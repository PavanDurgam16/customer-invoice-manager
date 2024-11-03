package com.devneopavan.customer_invoice_manager.repository;

import com.devneopavan.customer_invoice_manager.entity.TwoFactorVerification;
import com.devneopavan.customer_invoice_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TwoFactorVerificationRepository extends JpaRepository<TwoFactorVerification, Long> {

    TwoFactorVerification findFirstByUser(User user);

}
