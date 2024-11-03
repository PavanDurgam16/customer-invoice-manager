package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.TwoFactorVerification;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.dto.TwoFactorVerificationDTO;
import com.devneopavan.customer_invoice_manager.repository.TwoFactorVerificationRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TwoFactorVerificationService {

    private final TwoFactorVerificationRepository twoFactorVerificationRepository;
    private final UserRepository userRepository;

    public TwoFactorVerificationService(
            final TwoFactorVerificationRepository twoFactorVerificationRepository,
            final UserRepository userRepository) {
        this.twoFactorVerificationRepository = twoFactorVerificationRepository;
        this.userRepository = userRepository;
    }

    public List<TwoFactorVerificationDTO> findAll() {
        final List<TwoFactorVerification> twoFactorVerifications = twoFactorVerificationRepository.findAll(Sort.by("id"));
        return twoFactorVerifications.stream()
                .map(twoFactorVerification -> mapToDTO(twoFactorVerification, new TwoFactorVerificationDTO()))
                .toList();
    }

    public TwoFactorVerificationDTO get(final Long id) {
        return twoFactorVerificationRepository.findById(id)
                .map(twoFactorVerification -> mapToDTO(twoFactorVerification, new TwoFactorVerificationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TwoFactorVerificationDTO twoFactorVerificationDTO) {
        final TwoFactorVerification twoFactorVerification = new TwoFactorVerification();
        mapToEntity(twoFactorVerificationDTO, twoFactorVerification);
        return twoFactorVerificationRepository.save(twoFactorVerification).getId();
    }

    public void update(final Long id, final TwoFactorVerificationDTO twoFactorVerificationDTO) {
        final TwoFactorVerification twoFactorVerification = twoFactorVerificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(twoFactorVerificationDTO, twoFactorVerification);
        twoFactorVerificationRepository.save(twoFactorVerification);
    }

    public void delete(final Long id) {
        twoFactorVerificationRepository.deleteById(id);
    }

    private TwoFactorVerificationDTO mapToDTO(final TwoFactorVerification twoFactorVerification,
            final TwoFactorVerificationDTO twoFactorVerificationDTO) {
        twoFactorVerificationDTO.setId(twoFactorVerification.getId());
        twoFactorVerificationDTO.setCode(twoFactorVerification.getCode());
        twoFactorVerificationDTO.setExpirationData(twoFactorVerification.getExpirationData());
        twoFactorVerificationDTO.setUser(twoFactorVerification.getUser() == null ? null : twoFactorVerification.getUser().getId());
        return twoFactorVerificationDTO;
    }

    private TwoFactorVerification mapToEntity(
            final TwoFactorVerificationDTO twoFactorVerificationDTO,
            final TwoFactorVerification twoFactorVerification) {
        twoFactorVerification.setCode(twoFactorVerificationDTO.getCode());
        twoFactorVerification.setExpirationData(twoFactorVerificationDTO.getExpirationData());
        final User user = twoFactorVerificationDTO.getUser() == null ? null : userRepository.findById(twoFactorVerificationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        twoFactorVerification.setUser(user);
        return twoFactorVerification;
    }

}
