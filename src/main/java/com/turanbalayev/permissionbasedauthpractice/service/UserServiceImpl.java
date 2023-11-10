package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.entity.User;
import com.turanbalayev.permissionbasedauthpractice.entity.UserRole;
import com.turanbalayev.permissionbasedauthpractice.entity.composite.UserRoleId;
import com.turanbalayev.permissionbasedauthpractice.repository.RoleRepository;
import com.turanbalayev.permissionbasedauthpractice.repository.UserRepository;
import com.turanbalayev.permissionbasedauthpractice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    @Override
    public void addRoleToUser(String roleName, String email) {
            Role role = roleRepository.findByName(roleName).orElseThrow(
                    () -> new RuntimeException("Role not found")
            );

            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("User nor found")
            );

            userRoleRepository.save(new UserRole(
                    new UserRoleId(user.getId(),role.getId()),user,role
            ));

            log.info("Role: {} added to a user: {}",role.getName(),user.getFirstName());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getRolesOfUserByUserId(Long userId) {

        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Resource not found"));

        return userRoleRepository.getRolesOfUserByUserId(userId);
    }
}
