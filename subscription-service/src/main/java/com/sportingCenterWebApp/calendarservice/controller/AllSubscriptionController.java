package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.repo.SubscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/all")
public class AllSubscriptionController {
    //standard constructors
    private final SubscriptionRepository subscriptionRepository;

    public AllSubscriptionController(SubscriptionRepository abbRepository) {
        this.subscriptionRepository = abbRepository;
    }

    @RequestMapping(value = "subscriptions/getSubfromid/{subId}", method = RequestMethod.GET)
    public Subscription getSubscriptionById(@PathVariable("subId") Long id) {
        Optional<Subscription> subOp = subscriptionRepository.findById(id);
        Subscription subscription = subOp.get();
        return subscription;
    }

}
