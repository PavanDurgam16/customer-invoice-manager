package com.devneopavan.customer_invoice_manager.repository;

import com.devneopavan.customer_invoice_manager.entity.AccountVerification;
import com.devneopavan.customer_invoice_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountVerificationRepository extends JpaRepository<AccountVerification, Long> {

    AccountVerification findFirstByUser(User user);

}
