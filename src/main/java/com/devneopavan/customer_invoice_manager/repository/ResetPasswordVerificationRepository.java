package com.devneopavan.customer_invoice_manager.repository;

import com.devneopavan.customer_invoice_manager.entity.ResetPasswordVerification;
import com.devneopavan.customer_invoice_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResetPasswordVerificationRepository extends JpaRepository<ResetPasswordVerification, Long> {

    ResetPasswordVerification findFirstByUser(User user);

}
