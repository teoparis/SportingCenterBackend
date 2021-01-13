package com.sportingCenterWebApp.subscriptionservice.repo;

import com.sportingCenterWebApp.subscriptionservice.model.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
