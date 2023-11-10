package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role saveRole(String roleName);

    Role getRoleByName(String roleName);

    void addPermissionToARole(String permissionName, String roleName);

    List<Role> getAllRoles();

    List<Permission> getPermissionOfRoleByRoleName(String roleName);
}
