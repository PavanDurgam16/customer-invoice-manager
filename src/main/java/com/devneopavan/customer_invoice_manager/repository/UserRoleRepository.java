package com.devneopavan.customer_invoice_manager.repository;

import com.devneopavan.customer_invoice_manager.entity.Role;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findFirstByUser(User user);

    UserRole findFirstByRole(Role role);

}
