package edu.tsystems.javaschool.logapp.api.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccessDeniedController {

    private static final Logger LOG = Logger.getLogger(AccessDeniedController.class);

    @RequestMapping(value = "/accessDenied")
    public String handleAccessDenied(){
        LOG.error("Non-autheticated access happened");
        return "accessDenied";
    }
}
