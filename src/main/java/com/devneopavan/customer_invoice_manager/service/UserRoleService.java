package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.Role;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.entity.UserRole;
import com.devneopavan.customer_invoice_manager.dto.UserRoleDTO;
import com.devneopavan.customer_invoice_manager.repository.RoleRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRoleRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(final UserRoleRepository userRoleRepository,
            final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserRoleDTO> findAll() {
        final List<UserRole> userRoles = userRoleRepository.findAll(Sort.by("id"));
        return userRoles.stream()
                .map(userRole -> mapToDTO(userRole, new UserRoleDTO()))
                .toList();
    }

    public UserRoleDTO get(final Long id) {
        return userRoleRepository.findById(id)
                .map(userRole -> mapToDTO(userRole, new UserRoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserRoleDTO userRoleDTO) {
        final UserRole userRole = new UserRole();
        mapToEntity(userRoleDTO, userRole);
        return userRoleRepository.save(userRole).getId();
    }

    public void update(final Long id, final UserRoleDTO userRoleDTO) {
        final UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userRoleDTO, userRole);
        userRoleRepository.save(userRole);
    }

    public void delete(final Long id) {
        userRoleRepository.deleteById(id);
    }

    private UserRoleDTO mapToDTO(final UserRole userRole, final UserRoleDTO userRoleDTO) {
        userRoleDTO.setId(userRole.getId());
        userRoleDTO.setUser(userRole.getUser() == null ? null : userRole.getUser().getId());
        userRoleDTO.setRole(userRole.getRole() == null ? null : userRole.getRole().getId());
        return userRoleDTO;
    }

    private UserRole mapToEntity(final UserRoleDTO userRoleDTO, final UserRole userRole) {
        final User user = userRoleDTO.getUser() == null ? null : userRepository.findById(userRoleDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        userRole.setUser(user);
        final Role role = userRoleDTO.getRole() == null ? null : roleRepository.findById(userRoleDTO.getRole())
                .orElseThrow(() -> new NotFoundException("role not found"));
        userRole.setRole(role);
        return userRole;
    }

}
