package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.ResetPasswordVerification;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.dto.ResetPasswordVerificationDTO;
import com.devneopavan.customer_invoice_manager.repository.ResetPasswordVerificationRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ResetPasswordVerificationService {

    private final ResetPasswordVerificationRepository resetPasswordVerificationRepository;
    private final UserRepository userRepository;

    public ResetPasswordVerificationService(
            final ResetPasswordVerificationRepository resetPasswordVerificationRepository,
            final UserRepository userRepository) {
        this.resetPasswordVerificationRepository = resetPasswordVerificationRepository;
        this.userRepository = userRepository;
    }

    public List<ResetPasswordVerificationDTO> findAll() {
        final List<ResetPasswordVerification> resetPasswordVerifications = resetPasswordVerificationRepository.findAll(Sort.by("id"));
        return resetPasswordVerifications.stream()
                .map(resetPasswordVerification -> mapToDTO(resetPasswordVerification, new ResetPasswordVerificationDTO()))
                .toList();
    }

    public ResetPasswordVerificationDTO get(final Long id) {
        return resetPasswordVerificationRepository.findById(id)
                .map(resetPasswordVerification -> mapToDTO(resetPasswordVerification, new ResetPasswordVerificationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ResetPasswordVerificationDTO resetPasswordVerificationDTO) {
        final ResetPasswordVerification resetPasswordVerification = new ResetPasswordVerification();
        mapToEntity(resetPasswordVerificationDTO, resetPasswordVerification);
        return resetPasswordVerificationRepository.save(resetPasswordVerification).getId();
    }

    public void update(final Long id,
            final ResetPasswordVerificationDTO resetPasswordVerificationDTO) {
        final ResetPasswordVerification resetPasswordVerification = resetPasswordVerificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(resetPasswordVerificationDTO, resetPasswordVerification);
        resetPasswordVerificationRepository.save(resetPasswordVerification);
    }

    public void delete(final Long id) {
        resetPasswordVerificationRepository.deleteById(id);
    }

    private ResetPasswordVerificationDTO mapToDTO(
            final ResetPasswordVerification resetPasswordVerification,
            final ResetPasswordVerificationDTO resetPasswordVerificationDTO) {
        resetPasswordVerificationDTO.setId(resetPasswordVerification.getId());
        resetPasswordVerificationDTO.setUrl(resetPasswordVerification.getUrl());
        resetPasswordVerificationDTO.setExpirationDate(resetPasswordVerification.getExpirationDate());
        resetPasswordVerificationDTO.setUser(resetPasswordVerification.getUser() == null ? null : resetPasswordVerification.getUser().getId());
        return resetPasswordVerificationDTO;
    }

    private ResetPasswordVerification mapToEntity(
            final ResetPasswordVerificationDTO resetPasswordVerificationDTO,
            final ResetPasswordVerification resetPasswordVerification) {
        resetPasswordVerification.setUrl(resetPasswordVerificationDTO.getUrl());
        resetPasswordVerification.setExpirationDate(resetPasswordVerificationDTO.getExpirationDate());
        final User user = resetPasswordVerificationDTO.getUser() == null ? null : userRepository.findById(resetPasswordVerificationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        resetPasswordVerification.setUser(user);
        return resetPasswordVerification;
    }

}
