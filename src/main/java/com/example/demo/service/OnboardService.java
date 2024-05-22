package com.example.demo.service;

import com.example.demo.exception.ShaypeApiException;
import com.example.demo.model.CreateCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import shaype.openapi.example.api.KycApiApi;
import shaype.openapi.example.model.CreateCaseExternalResponse;
import shaype.openapi.example.model.HayAccount;
import shaype.openapi.example.model.HayCustomer;

@Slf4j
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
            log.info("Shaype KYC is disabled, creating a customer on platform.");
            // Creating a customer
            customer = customerService.createCustomer(createCustomer, null);
            // Activate customer
            customerService.activateCustomer(customer.getCustomerHayId());
            // Creating account
            HayAccount account = accountService.createAccount(customer.getCustomerHayId());
            // Updating account risk level
            accountService.updateAccountRiskLevel(account.getAccountHayId());
        } else {
            log.info("Shaype KYC is enabled, creating a case to initiate KYC process.");
            //Creating a case
            CreateCaseExternalResponse caseResponse = createCase();
            //creating customer with caseId
            customer = customerService.createCustomer(createCustomer, caseResponse.getScanCase().getId());
            log.info("Wait for ONBOARDING event");
        }
        return customer;
    }

    private CreateCaseExternalResponse createCase(){
        try {
             return kycApi.createCase();
        }catch (RestClientResponseException e){
            log.error("Error occurred while creating case:- {}", e.getMessage());
            throw new ShaypeApiException(e);
        }
    }
}
