package com.sportingCenterWebApp.calendarservice.controller;

import com.sportingCenterWebApp.calendarservice.model.Subscription;
import com.sportingCenterWebApp.calendarservice.repo.SubscriptionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    void addSubscription(@RequestBody Subscription abb){
        subscriptionRepository.save(abb);
    }

    @PostMapping("/modifysub")
    void modifySubscription(@RequestBody Subscription newabb,@RequestBody Subscription oldabb){
        subscriptionRepository.save(newabb);
    }

    @RequestMapping(value = "subscriptions/getSubfromid/{id}", method = RequestMethod.GET)
    public Optional<Subscription> getSubscriptionById(@PathVariable("id") Long id) {
        return (Optional<Subscription>) subscriptionRepository.findById(id);
    }

    @RequestMapping(value = "subscriptions/getnamefromid/{id}", method = RequestMethod.GET)
    public String getNameSubscriptionById(@PathVariable("id") Long id) {
        Optional<Subscription> optionalSubscription= subscriptionRepository.findById(id);
        Subscription subscription = optionalSubscription.get();
        if(subscription != null)
            return subscription.getName();
        return "Abbonamento non trovato";
    }


    @PostMapping("/subscription/delete")
    void deleteActivity(@RequestBody Subscription abb) {
        subscriptionRepository.delete(abb);
    }
}

