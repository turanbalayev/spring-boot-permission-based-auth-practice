package com.turanbalayev.permissionbasedauthpractice.repository;

import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.entity.UserRole;
import com.turanbalayev.permissionbasedauthpractice.entity.composite.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query(value = "SELECT role.name FROM user_role " +
            "JOIN user ON user_role.user_id = user.id " +
            "JOIN role ON role.id = user_role.role_id " +
            "WHERE user.id = ?1",nativeQuery = true)
    List<Role> getRolesOfUserByUserId(Long userId);
}
