package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.dto.RoleDto;
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
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;


    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        roleRepository.findByName(roleDto.getName()).ifPresent(role -> {
            throw new RuntimeException("The role with the given role name is exist in the database");
        });

        roleDto.getPermissions().forEach(p -> {
            permissionRepository.findByName(p).orElseThrow(()-> {
                    throw new RuntimeException("The permission with the given name is not existed");
            });
        });


        Role role = roleRepository.save(new Role(roleDto.getName()));

        roleDto.getPermissions().forEach(p -> {
            this.addPermissionToARole(p,roleDto.getName());
        });

        List<String> permissions = rolePermissionRepository.getPermissionsOfRoleByRoleName(role.getName()).stream().map(p -> p.getName()).collect(Collectors.toList());
        return new RoleDto(role.getName(),permissions);


    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("The role not found in the database"));
    }

    @Override
    public void addPermissionToARole(String permissionName, String roleName) {
        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found to add a permission"));


        rolePermissionRepository.save(new RolePermission
                (new RolePermissionId(role.getId(),permission.getId()),role,permission));

        log.info("Permission: {} added to a role: {}",permission.getName(),role.getName());

    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<String> getPermissionOfRoleByRoleName(String roleName) {
        roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));

        return rolePermissionRepository.getPermissionsOfRoleByRoleName(roleName)
                .stream().map(r -> r.getName()).collect(Collectors.toList());
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto, Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() ->
                new RuntimeException("Role with the given id is not exist in the database."));

        role.setName(roleDto.getName());
        rolePermissionRepository.deleteAllPermissionsOfARole(role.getId());
        roleRepository.save(role);

        if(!roleDto.getPermissions().isEmpty()){
            roleDto.getPermissions().forEach(permission -> {
                permissionRepository.findByName(permission).orElseThrow(() ->
                        new RuntimeException("Permission with the given name is not exist in the database."));
                this.addPermissionToARole(permission,role.getName());
            });
        }

        RoleDto responseRoleDto = new RoleDto();
        responseRoleDto.setName(role.getName());
        List<String> newPermissionList = this.getPermissionOfRoleByRoleName(role.getName());
        responseRoleDto.setPermissions(newPermissionList);
        return responseRoleDto;

    }

    @Override
    public RoleDto removePermissionFromARole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() ->
                new RuntimeException("Role with the given id is not exist in the database."));

        permissionRepository.findById(permissionId).orElseThrow(() ->
                new RuntimeException("Permission with the given id is not exist in the database"));

        rolePermissionRepository.deletePermissionFromARole(roleId,permissionId);

        List<String> permissionsOfARole = this.getPermissionOfRoleByRoleName(role.getName());

        RoleDto roleDto = new RoleDto();

        roleDto.setName(role.getName());
        roleDto.setPermissions(permissionsOfARole);

        return roleDto;

    }
}
