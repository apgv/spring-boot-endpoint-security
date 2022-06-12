package no.elme.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api")
public class OneController {
    @GetMapping(path = "secured")
    @PreAuthorize("secured")
    public void securedEndpoint() {
        //ignore
    }

    @GetMapping(path = "endpoint-open-to-all")
    @NoAuthorizationNeeded
    public void getEndpointOpenToAll() {
        //ignore
    }

    @RequestMapping(path = "catch-all-methods")
    public void allMethodsEndpointMissingSecurity() {
        //ignore
    }

    @GetMapping(path = "public")
    public void getEndpointPublicMethodMissingSecurity() {
        //ignore
    }

    @GetMapping(path = "protected")
    protected void getEndpointProtectedMethodMissingSecurity() {
        //ignore
    }

    @GetMapping(path = "package-private")
    void getEndpointPackagePrivateMethodMissingSecurity() {
        //ignore
    }

    @GetMapping(path = "private")
    private void getEndpointPrivateMethodMissingSecurity() {
        //ignore
    }

    @PostMapping
    public void postEndpointMissingSecurity() {
        //ignore
    }

    @PutMapping
    public void putEndpointMissingSecurity() {
        //ignore
    }

    @DeleteMapping
    public void deleteEndpointMissingSecurity() {
        //ignore
    }

    @PatchMapping
    public void patchEndpointMissingSecurity() {
        //ignore
    }
}
