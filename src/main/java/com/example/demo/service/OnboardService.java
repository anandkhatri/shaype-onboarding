package com.example.demo.service;

import com.example.demo.model.CreateCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shaype.openapi.example.api.KycApiApi;
import shaype.openapi.example.model.CreateCaseExternalResponse;
import shaype.openapi.example.model.HayAccount;
import shaype.openapi.example.model.HayCustomer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OnboardService {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final KycApiApi kycApi;

    @Value("${shaype.skipKYC}")
    private Boolean skipKYC;

    public HayCustomer onboardCustomerToShaypePlatform(CreateCustomer createCustomer) {
        HayCustomer customer;
        if (skipKYC) {
            //Creating a case
            CreateCaseExternalResponse createCaseResponse = kycApi.createCase();
            //creating customer with caseId
            assert createCaseResponse.getScanCase() != null;
            customer = customerService.createCustomer(createCustomer, createCaseResponse.getScanCase().getId());
        } else {
            // Creating a customer
            customer = customerService.createCustomer(createCustomer, null);

            if (Objects.nonNull(customer)) {
                // Activate customer
                customerService.activateCustomer(customer.getCustomerHayId());
                // Creating account
                HayAccount account = accountService.createAccount(customer.getCustomerHayId());
                if (Objects.nonNull(account)) {
                    // Updating account risk level
                    accountService.updateAccountRiskLevel(account.getAccountHayId());
                }
            }
        }
        return customer;
    }
}
