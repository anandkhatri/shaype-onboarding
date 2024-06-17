package com.example.demo.service;

import com.example.demo.exception.ShaypeApiException;
import com.example.demo.model.Group;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientException;
import shaype.openapi.example.api.GroupsApiApi;
import shaype.openapi.example.model.AddCustomersToGroupRequestBody;
import shaype.openapi.example.model.HayGroup;
import shaype.openapi.example.model.HayJointAccount;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupService {

    private final GroupsApiApi groupApi;

    public HayGroup createGroup(Group group) {
        HayGroup hayGroup = null;
        try {
            hayGroup = groupApi.createHayGroup(group);
        } catch (RestClientException e) {
            log.error("Error occurred while creating group: {}", e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Group created successfully, groupId: {}", hayGroup.getGroupHayId());
        return hayGroup;
    }

    public HayJointAccount addCustomerToGroup(UUID groupId, AddCustomersToGroupRequestBody addCustomersToGroupRequestBody) {
        HayJointAccount groupDetails;
        try {
            groupDetails = groupApi.addCustomersToGroup(groupId, addCustomersToGroupRequestBody);
        } catch (RestClientException e) {
            log.error("Error occurred while adding customer to group: {}", e.getMessage());
            throw new ShaypeApiException(e);
        }
        log.info("Customers {} have been added to the Group {} successfully.", addCustomersToGroupRequestBody.getCustomerHayIds(), groupId);
        return groupDetails;
    }

}
