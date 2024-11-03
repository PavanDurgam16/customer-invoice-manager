package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.AccountVerification;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.dto.AccountVerificationDTO;
import com.devneopavan.customer_invoice_manager.repository.AccountVerificationRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AccountVerificationService {

    private final AccountVerificationRepository accountVerificationRepository;
    private final UserRepository userRepository;

    public AccountVerificationService(
            final AccountVerificationRepository accountVerificationRepository,
            final UserRepository userRepository) {
        this.accountVerificationRepository = accountVerificationRepository;
        this.userRepository = userRepository;
    }

    public List<AccountVerificationDTO> findAll() {
        final List<AccountVerification> accountVerifications = accountVerificationRepository.findAll(Sort.by("id"));
        return accountVerifications.stream()
                .map(accountVerification -> mapToDTO(accountVerification, new AccountVerificationDTO()))
                .toList();
    }

    public AccountVerificationDTO get(final Long id) {
        return accountVerificationRepository.findById(id)
                .map(accountVerification -> mapToDTO(accountVerification, new AccountVerificationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AccountVerificationDTO accountVerificationDTO) {
        final AccountVerification accountVerification = new AccountVerification();
        mapToEntity(accountVerificationDTO, accountVerification);
        return accountVerificationRepository.save(accountVerification).getId();
    }

    public void update(final Long id, final AccountVerificationDTO accountVerificationDTO) {
        final AccountVerification accountVerification = accountVerificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(accountVerificationDTO, accountVerification);
        accountVerificationRepository.save(accountVerification);
    }

    public void delete(final Long id) {
        accountVerificationRepository.deleteById(id);
    }

    private AccountVerificationDTO mapToDTO(final AccountVerification accountVerification,
            final AccountVerificationDTO accountVerificationDTO) {
        accountVerificationDTO.setId(accountVerification.getId());
        accountVerificationDTO.setUrl(accountVerification.getUrl());
        accountVerificationDTO.setUser(accountVerification.getUser() == null ? null : accountVerification.getUser().getId());
        return accountVerificationDTO;
    }

    private AccountVerification mapToEntity(final AccountVerificationDTO accountVerificationDTO,
            final AccountVerification accountVerification) {
        accountVerification.setUrl(accountVerificationDTO.getUrl());
        final User user = accountVerificationDTO.getUser() == null ? null : userRepository.findById(accountVerificationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        accountVerification.setUser(user);
        return accountVerification;
    }

}
