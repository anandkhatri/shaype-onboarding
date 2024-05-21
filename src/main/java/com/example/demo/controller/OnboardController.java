package com.example.demo.controller;

import com.example.demo.model.CreateCustomer;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OnboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shaype.openapi.example.model.HayCustomer;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OnboardController {

    private final OnboardService onboardService;

    @PostMapping("/onboard")
    public HayCustomer onboardCustomer() {
        return onboardService.onboardCustomerToShaypePlatform(new CreateCustomer());
    }

//    @GetMapping("/customer")
//    public List<HayCustomer> getCustomer() {
//        return customerService.getAllCustomer();
//    }
}
