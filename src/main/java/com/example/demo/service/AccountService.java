package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shaype.openapi.example.api.AccountsApiApi;
import shaype.openapi.example.api.CustomersApiApi;
import shaype.openapi.example.model.ChangeHayAccountRiskLevelRequestBody;
import shaype.openapi.example.model.CreateHayAccountRequest;
import shaype.openapi.example.model.HayAccount;

import java.util.Collections;
import java.util.UUID;

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
        return customersApi.createHayAccount(customerId, createAccountRequest);
    }

    public void updateAccountRiskLevel(UUID accountHayId) {
        ChangeHayAccountRiskLevelRequestBody riskLevelRequest = new ChangeHayAccountRiskLevelRequestBody()
                .level(ChangeHayAccountRiskLevelRequestBody.LevelEnum.LOW)
                .reason("Reason of Risk Level");
        accountsApi.changeAccountRiskLevel(accountHayId, riskLevelRequest);
        //TODO: Perform any post action
    }
}
