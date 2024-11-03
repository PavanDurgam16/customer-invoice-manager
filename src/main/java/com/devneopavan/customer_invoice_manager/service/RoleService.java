package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.Role;
import com.devneopavan.customer_invoice_manager.entity.UserRole;
import com.devneopavan.customer_invoice_manager.dto.RoleDTO;
import com.devneopavan.customer_invoice_manager.repository.RoleRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRoleRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import com.devneopavan.customer_invoice_manager.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public RoleService(final RoleRepository roleRepository,
            final UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Long id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Long id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setPermission(role.getPermission());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setName(roleDTO.getName());
        role.setPermission(roleDTO.getPermission());
        return role;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UserRole roleUserRole = userRoleRepository.findFirstByRole(role);
        if (roleUserRole != null) {
            referencedWarning.setKey("role.userRole.role.referenced");
            referencedWarning.addParam(roleUserRole.getId());
            return referencedWarning;
        }
        return null;
    }

}
