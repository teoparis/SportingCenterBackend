package com.sportingCenterWebApp.subscriptionservice.controller;

import com.sportingCenterWebApp.subscriptionservice.model.Subscription;
import com.sportingCenterWebApp.subscriptionservice.repo.SubscriptionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class SubscriptionController {
    //standard constructors
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository abbRepository) {
        this.subscriptionRepository = abbRepository;
    }

    @GetMapping("/subscriptions")
    public List<Subscription> getActivities() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }

    @PostMapping("/subscription")
    void addActivity(@RequestBody Subscription abb){
        subscriptionRepository.save(abb);
    }

    @PostMapping("/modifysub")
    void modifyActivity(@RequestBody Subscription newabb,@RequestBody Subscription oldabb){
        subscriptionRepository.delete(oldabb);
        subscriptionRepository.save(newabb);
    }

    @PostMapping("/subscription/delete")
    void deleteActivity(@RequestBody Subscription abb) {
        subscriptionRepository.delete(abb);
    }
}

