package com.vmware.retail.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private final String customerIdAttribId;
    private final String customerId;

    public IndexController(
            @Value("${retail.customer.attribute.name:customerId}")
            String customerIdAttribId,
            @Value("${retail.customer.id}")
            String customerId) {
        this.customerIdAttribId = customerIdAttribId;
        this.customerId = customerId;
    }

    @RequestMapping("/")
    public String homePage(HttpServletRequest request, Model model)
    {
        model.addAttribute(customerIdAttribId,customerId);
        return "index";
    }
}
