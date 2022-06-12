# spring-boot-endpoint-security
Example of how to ensure with a [unit test](src/test/java/no/elme/example/AllHttpEndpointsAreSecuredTest.java) 
that every web endpoint in a Spring Boot web application is secured with Spring Security using annotations.
Libraries used:
* Spring Boot
  * Spring Security
* [ClassGraph library](https://github.com/classgraph/classgraph)
* JUnit 5
* AssertJ

The `@PreAuthorize` annotation from Spring Security is used in the examples to secure an endpoint.
The custom `@NoAuthorizationNeeded` annotation is used in the examples to tell an endpoint is not secured on purpose.