package com.sportingCenterWebApp.calendarservice.repo;

import com.sportingCenterWebApp.calendarservice.model.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
