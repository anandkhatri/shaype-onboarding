package com.example.demo.service;

import com.example.demo.exception.ShaypeApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import shaype.openapi.example.api.AccountsApiApi;
import shaype.openapi.example.api.CustomersApiApi;
import shaype.openapi.example.model.ChangeHayAccountRiskLevelRequestBody;
import shaype.openapi.example.model.CreateHayAccountRequest;
import shaype.openapi.example.model.HayAccount;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountService {

    private final CustomersApiApi customersApi;
    private final AccountsApiApi accountsApi;

    public HayAccount createAccount(UUID customerId) {
        //TODO: Perform your business logic before Account creation
        CreateHayAccountRequest createAccountRequest = new CreateHayAccountRequest()
                .customData(Collections.singletonMap("AccountName", "Saving Account")) // define custom attributes for account
                .idempotencyKey(UUID.randomUUID());
        HayAccount account;
        try {
            account = customersApi.createHayAccount(customerId, createAccountRequest);
        } catch (RestClientResponseException e) {
            log.error("Error occurred while creating account for customer {} :- {}", customerId, e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Account {} created for customer {}", account.getAccountHayId(), customerId);
        return account;
    }

    public void updateAccountRiskLevel(UUID accountHayId) {
        ChangeHayAccountRiskLevelRequestBody riskLevelRequest = new ChangeHayAccountRiskLevelRequestBody()
                .level(ChangeHayAccountRiskLevelRequestBody.LevelEnum.LOW)
                .reason("Reason of Risk Level");
        try {
            accountsApi.changeAccountRiskLevel(accountHayId, riskLevelRequest);
        } catch (RestClientResponseException e) {
            log.error("Error occurred while updating account risk level to LOW:- {}", e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Account {} risk level updated to LOW", accountHayId);
        //TODO: Perform any post action
    }
}
