package com.turanbalayev.permissionbasedauthpractice.repository;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.RolePermission;
import com.turanbalayev.permissionbasedauthpractice.entity.composite.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    @Query(value = "SELECT permission.name FROM role_permission " +
            "JOIN role ON role_permission.role_id = role.id " +
            "JOIN permission ON role_permission.permission_id = permission.id " +
            "WHERE role.name = ?1",nativeQuery = true)
    List<Permission> getPermissionsOfRoleByRoleName(String roleName);
}
