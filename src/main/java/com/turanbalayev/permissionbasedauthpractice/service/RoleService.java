package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.dto.RoleDto;
import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleDto saveRole(RoleDto roleDto);

    Role getRoleByName(String roleName);

    void addPermissionToARole(String permissionName, String roleName);

    List<Role> getAllRoles();

    List<String> getPermissionOfRoleByRoleName(String roleName);

    RoleDto updateRole(RoleDto roleDto, Long roleId);

    RoleDto removePermissionFromARole(Long roleId, Long permissionId);
}
