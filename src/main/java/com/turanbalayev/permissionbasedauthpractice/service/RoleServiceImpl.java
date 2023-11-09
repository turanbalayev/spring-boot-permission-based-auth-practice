package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.entity.RolePermission;
import com.turanbalayev.permissionbasedauthpractice.entity.composite.RolePermissionId;
import com.turanbalayev.permissionbasedauthpractice.repository.PermissionRepository;
import com.turanbalayev.permissionbasedauthpractice.repository.RolePermissionRepository;
import com.turanbalayev.permissionbasedauthpractice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;


    @Override
    public Role saveRole(String roleName) {
        roleRepository.findByName(roleName).ifPresent(role -> {
            throw new RuntimeException("The role with the given role name is exist in the database");
        });

        return roleRepository.save(new Role(roleName));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("The role not found in the database"));
    }

    @Override
    public void addPermissionToARole(String permissionName, String roleName) {
        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));


        rolePermissionRepository.save(new RolePermission
                (new RolePermissionId(role.getId(),permission.getId()),role,permission));

        log.info("Permission: {} added to a role: {}",permission.getName(),role.getName());

    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
