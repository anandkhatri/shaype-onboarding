package com.example.demo.controller;

import com.example.demo.model.CreateCustomer;
import com.example.demo.service.OnboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shaype.openapi.example.model.HayCustomer;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OnboardController {

    private final OnboardService onboardService;

    @PostMapping("/onboard")
    public HayCustomer onboardCustomer() {
        log.info("Request to onboard the customer on Shaype platform.");
        return onboardService.onboardCustomerToShaypePlatform(new CreateCustomer());
    }

//    @GetMapping("/customer")
//    public List<HayCustomer> getCustomer() {
//        return customerService.getAllCustomer();
//    }
}
