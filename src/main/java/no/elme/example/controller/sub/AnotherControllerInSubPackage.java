package no.elme.example.controller.sub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "another")
public class AnotherControllerInSubPackage {

    @GetMapping
    public void getEndpointMissingSecurity() {
        //ignore
    }
}
