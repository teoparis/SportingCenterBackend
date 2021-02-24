package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.repo.SubscriptionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserSubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    public UserSubscriptionController(SubscriptionRepository abbRepository) {
        this.subscriptionRepository = abbRepository;
    }

    @GetMapping("/subscriptions")
    public List<Subscription> getSubscriptions() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }
}
