package com.devneopavan.customer_invoice_manager.repository;

import com.devneopavan.customer_invoice_manager.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {
}
