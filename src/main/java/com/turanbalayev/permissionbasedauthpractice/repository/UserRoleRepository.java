package com.turanbalayev.permissionbasedauthpractice.repository;

import com.turanbalayev.permissionbasedauthpractice.entity.UserRole;
import com.turanbalayev.permissionbasedauthpractice.entity.composite.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
