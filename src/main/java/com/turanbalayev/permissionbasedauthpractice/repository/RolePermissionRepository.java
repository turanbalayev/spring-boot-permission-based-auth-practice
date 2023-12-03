package com.turanbalayev.permissionbasedauthpractice.repository;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.RolePermission;
import com.turanbalayev.permissionbasedauthpractice.entity.composite.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    @Query(value = "SELECT permission.name FROM role_permission " +
            "JOIN role ON role_permission.role_id = role.id " +
            "JOIN permission ON role_permission.permission_id = permission.id " +
            "WHERE role.name = ?1",nativeQuery = true)
    List<Permission> getPermissionsOfRoleByRoleName(String roleName);

    @Modifying
    @Query(value = "DELETE FROM role_permission WHERE role_id = ?1 and permission_id = ?2", nativeQuery = true)
    void deletePermissionFromARole(Long roleId, Long permissionId);

    @Modifying
    @Query(value = "DELETE FROM role_permission WHERE role_id = ?1", nativeQuery = true)
    void deleteAllPermissionsOfARole(Long roleId);
}
