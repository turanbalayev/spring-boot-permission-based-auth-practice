package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    User getUserByEmail(String email);

    void addRoleToUser(String roleName,String email);

    List<User> getAllUsers();

    List<Role> getRolesOfUserByUserId(Long userId);
}
