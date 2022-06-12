package no.elme.example.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Inherited
public @interface NoAuthorizationNeeded {
}
