package com.example.demo.service;

import com.example.demo.exception.ShaypeApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import shaype.openapi.example.api.AccountsApiApi;
import shaype.openapi.example.api.CustomersApiApi;
import shaype.openapi.example.api.GroupsApiApi;
import shaype.openapi.example.model.*;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountService {

    private final CustomersApiApi customersApi;
    private final AccountsApiApi accountsApi;
    private final GroupsApiApi groupApi;

    public HayAccount createAccountForCustomer(UUID customerId, CreateHayAccountRequest createAccountRequest) {
        //TODO: Perform your business logic before Account creation for customer
        HayAccount account;
        try {
            account = customersApi.createHayAccount(customerId, createAccountRequest);
        } catch (RestClientException e) {
            log.error("Error occurred while creating account for customer {} :- {}", customerId, e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Account {} created for customer {}", account.getAccountHayId(), customerId);
        return account;
    }

    public HayJointAccount createAccountForGroup(UUID groupId, CreateHayAccountForGroupRequestBody createAccountRequest) {
        //TODO: Perform your business logic before Account creation for group
        HayJointAccount account;
        try {
            account = groupApi.createHayAccountForGroup(groupId, createAccountRequest);
        } catch (RestClientException e) {
            log.error("Error occurred while creating account for group {} :- {}", groupId, e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Account {} created for group {}", account.getHayAccount().getAccountHayId(), account.getName());
        return account;
    }

    public void updateAccountRiskLevel(UUID accountHayId) {
        ChangeHayAccountRiskLevelRequestBody riskLevelRequest = new ChangeHayAccountRiskLevelRequestBody()
                .level(ChangeHayAccountRiskLevelRequestBody.LevelEnum.LOW)
                .reason("Reason of Risk Level");
        try {
            accountsApi.changeAccountRiskLevel(accountHayId, riskLevelRequest);
        } catch (RestClientException e) {
            log.error("Error occurred while updating account risk level to LOW:- {}", e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Account {} risk level updated to LOW", accountHayId);
        //TODO: Perform any post action
    }
}
