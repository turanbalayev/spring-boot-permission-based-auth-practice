package com.turanbalayev.permissionbasedauthpractice.repository;

import com.turanbalayev.permissionbasedauthpractice.entity.RolePermission;
import com.turanbalayev.permissionbasedauthpractice.entity.composite.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
}
