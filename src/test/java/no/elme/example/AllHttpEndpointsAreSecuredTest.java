package no.elme.example;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;
import no.elme.example.controller.NoAuthorizationRequired;
import no.elme.example.controller.OneController;
import no.elme.example.controller.sub.AnotherControllerInSubPackage;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AllHttpEndpointsAreSecuredTest {

    private static final String SCAN_PACKAGES_INCLUDING_SUB_PACKAGES = "no.elme.example";

    @Test
    void verifyAllEndpointsAreSecured() {
        try (var scanResult = new ClassGraph()
                .acceptPackages(SCAN_PACKAGES_INCLUDING_SUB_PACKAGES)
                .enableAllInfo()
                .scan()) {
            var controllerClasses = scanResult.getClassesWithAnnotation(Controller.class);
            assertThat(controllerClasses)
                    .extracting(ClassInfo::getSimpleName)
                    .contains(
                            OneController.class.getSimpleName(),
                            AnotherControllerInSubPackage.class.getSimpleName()
                    );

            var endpointsMissingProtection = new ArrayList<String>();

            controllerClasses.forEach(classInfo -> {
                Set<String> collect = classInfo.getDeclaredMethodInfo().stream()
                        .filter(methodInfo -> methodInfo.hasAnnotation(RequestMapping.class))
                        .filter(methodInfo -> !methodInfo.hasAnnotation(NoAuthorizationRequired.class))
                        .filter(methodInfo -> !methodInfo.hasAnnotation(PreAuthorize.class))
                        .map(this::classNameAndMethodName)
                        .collect(Collectors.toSet());
                endpointsMissingProtection.addAll(collect);
            });

            assertThat(endpointsMissingProtection).containsOnly(
                    classNameAndMethodName(OneController.class, "allMethodsEndpointMissingSecurity"),
                    classNameAndMethodName(OneController.class, "getEndpointPublicMethodMissingSecurity"),
                    classNameAndMethodName(OneController.class, "getEndpointProtectedMethodMissingSecurity"),
                    classNameAndMethodName(OneController.class, "getEndpointPackagePrivateMethodMissingSecurity"),
                    classNameAndMethodName(OneController.class, "getEndpointPrivateMethodMissingSecurity"),
                    classNameAndMethodName(OneController.class, "postEndpointMissingSecurity"),
                    classNameAndMethodName(OneController.class, "putEndpointMissingSecurity"),
                    classNameAndMethodName(OneController.class, "deleteEndpointMissingSecurity"),
                    classNameAndMethodName(OneController.class, "patchEndpointMissingSecurity"),
                    classNameAndMethodName(AnotherControllerInSubPackage.class, "getEndpointMissingSecurity")
            );
        }
    }

    private String classNameAndMethodName(final MethodInfo methodInfo) {
        return classNameAndMethodName(methodInfo.loadClassAndGetMethod().getDeclaringClass(), methodInfo.getName());
    }

    private String classNameAndMethodName(final Class<?> clazz, final String methodName) {
        return String.join("::", clazz.getName(), methodName);
    }
}
