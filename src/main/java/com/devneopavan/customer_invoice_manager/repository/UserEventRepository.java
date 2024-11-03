package com.devneopavan.customer_invoice_manager.repository;

import com.devneopavan.customer_invoice_manager.entity.Event;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    UserEvent findFirstByUser(User user);

    UserEvent findFirstByEvent(Event event);

}
