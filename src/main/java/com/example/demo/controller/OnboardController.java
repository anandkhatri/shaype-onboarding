package com.example.demo.controller;

import com.example.demo.model.CreateCustomer;
import com.example.demo.model.Group;
import com.example.demo.service.GroupService;
import com.example.demo.service.OnboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shaype.openapi.example.model.AddCustomersToGroupRequestBody;
import shaype.openapi.example.model.HayCustomer;
import shaype.openapi.example.model.HayGroup;
import shaype.openapi.example.model.HayJointAccount;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OnboardController {

    private final OnboardService onboardService;
    private final GroupService groupService;

    @PostMapping("/group")
    public HayGroup createGroup(@RequestBody Group group) {
        log.info("Request to create a group on Shaype platform.");
        return groupService.createGroup(group);
    }

    @PostMapping("/onboard")
    public HayCustomer onboardCustomer(@RequestBody CreateCustomer createCustomer) {
        log.info("Request to onboard the customer.");
        return onboardService.onboardCustomerToShaypePlatform(createCustomer);
    }

    @PostMapping("/group/{groupId}/customers")
    public HayJointAccount addCustomerToGroup(@PathVariable UUID groupId, @RequestBody AddCustomersToGroupRequestBody addCustomersToGroupRequestBody) {
        log.info("Request to add customer to group.");
        return groupService.addCustomerToGroup(groupId, addCustomersToGroupRequestBody);
    }

//    @GetMapping("/customer")
//    public List<HayCustomer> getCustomer() {
//        return customerService.getAllCustomer();
//    }
}
