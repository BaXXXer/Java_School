package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.exception.BusinessLogicException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myOrder")
public class MyOrderController {

    @GetMapping("")
    public String list(Model model) throws BusinessLogicException {

        return "myOrder/myAccount";
    }





}
