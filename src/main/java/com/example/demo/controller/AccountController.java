package com.example.demo.controller;

import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shaype.openapi.example.model.CreateHayAccountForGroupRequestBody;
import shaype.openapi.example.model.CreateHayAccountRequest;
import shaype.openapi.example.model.HayAccount;
import shaype.openapi.example.model.HayJointAccount;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/account/customer/{customerId}")
    public HayAccount createAccountForCustomer(@PathVariable UUID customerId, @RequestBody CreateHayAccountRequest createAccountRequest) {
        log.info("Creating account for a customer with risk level LOW for customer: {}", customerId);
        HayAccount account = accountService.createAccountForCustomer(customerId, createAccountRequest);
        accountService.updateAccountRiskLevel(account.getAccountHayId());
        return account;
    }

    @PostMapping("/account/group/{customerId}")
    public HayJointAccount createAccountForGroup(@PathVariable UUID customerId, @RequestBody CreateHayAccountForGroupRequestBody createAccountRequest) {
        log.info("Creating account for a group with risk level LOW for customer: {}", customerId);
        HayJointAccount account = accountService.createAccountForGroup(customerId, createAccountRequest);
        accountService.updateAccountRiskLevel(account.getHayAccount().getAccountHayId());
        return account;
    }
}
