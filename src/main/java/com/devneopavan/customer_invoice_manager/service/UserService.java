package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.AccountVerification;
import com.devneopavan.customer_invoice_manager.entity.ResetPasswordVerification;
import com.devneopavan.customer_invoice_manager.entity.TwoFactorVerification;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.entity.UserEvent;
import com.devneopavan.customer_invoice_manager.entity.UserRole;
import com.devneopavan.customer_invoice_manager.dto.UserDTO;
import com.devneopavan.customer_invoice_manager.repository.AccountVerificationRepository;
import com.devneopavan.customer_invoice_manager.repository.ResetPasswordVerificationRepository;
import com.devneopavan.customer_invoice_manager.repository.TwoFactorVerificationRepository;
import com.devneopavan.customer_invoice_manager.repository.UserEventRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRoleRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import com.devneopavan.customer_invoice_manager.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final AccountVerificationRepository accountVerificationRepository;
    private final TwoFactorVerificationRepository twoFactorVerificationRepository;
    private final UserEventRepository userEventRepository;
    private final ResetPasswordVerificationRepository resetPasswordVerificationRepository;

    public UserService(final UserRepository userRepository,
            final UserRoleRepository userRoleRepository,
            final AccountVerificationRepository accountVerificationRepository,
            final TwoFactorVerificationRepository twoFactorVerificationRepository,
            final UserEventRepository userEventRepository,
            final ResetPasswordVerificationRepository resetPasswordVerificationRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.accountVerificationRepository = accountVerificationRepository;
        this.twoFactorVerificationRepository = twoFactorVerificationRepository;
        this.userEventRepository = userEventRepository;
        this.resetPasswordVerificationRepository = resetPasswordVerificationRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setTitle(user.getTitle());
        userDTO.setBio(user.getBio());
        userDTO.setImageUrl(user.getImageUrl());
        userDTO.setEnabled(user.getEnabled());
        userDTO.setIsNotLocked(user.getIsNotLocked());
        userDTO.setIsUsingMfa(user.getIsUsingMfa());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setTitle(userDTO.getTitle());
        user.setBio(userDTO.getBio());
        user.setImageUrl(userDTO.getImageUrl());
        user.setEnabled(userDTO.getEnabled());
        user.setIsNotLocked(userDTO.getIsNotLocked());
        user.setIsUsingMfa(userDTO.getIsUsingMfa());
        user.setCreatedAt(userDTO.getCreatedAt());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UserRole userUserRole = userRoleRepository.findFirstByUser(user);
        if (userUserRole != null) {
            referencedWarning.setKey("user.userRole.user.referenced");
            referencedWarning.addParam(userUserRole.getId());
            return referencedWarning;
        }
        final AccountVerification userAccountVerification = accountVerificationRepository.findFirstByUser(user);
        if (userAccountVerification != null) {
            referencedWarning.setKey("user.accountVerification.user.referenced");
            referencedWarning.addParam(userAccountVerification.getId());
            return referencedWarning;
        }
        final TwoFactorVerification userTwoFactorVerification = twoFactorVerificationRepository.findFirstByUser(user);
        if (userTwoFactorVerification != null) {
            referencedWarning.setKey("user.twoFactorVerification.user.referenced");
            referencedWarning.addParam(userTwoFactorVerification.getId());
            return referencedWarning;
        }
        final UserEvent userUserEvent = userEventRepository.findFirstByUser(user);
        if (userUserEvent != null) {
            referencedWarning.setKey("user.userEvent.user.referenced");
            referencedWarning.addParam(userUserEvent.getId());
            return referencedWarning;
        }
        final ResetPasswordVerification userResetPasswordVerification = resetPasswordVerificationRepository.findFirstByUser(user);
        if (userResetPasswordVerification != null) {
            referencedWarning.setKey("user.resetPasswordVerification.user.referenced");
            referencedWarning.addParam(userResetPasswordVerification.getId());
            return referencedWarning;
        }
        return null;
    }

}
