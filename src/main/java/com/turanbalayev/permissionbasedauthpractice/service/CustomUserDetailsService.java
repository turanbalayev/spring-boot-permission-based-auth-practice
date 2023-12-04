package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.User;
import com.turanbalayev.permissionbasedauthpractice.repository.UserRepository;
import com.turanbalayev.permissionbasedauthpractice.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleService roleService;


    public CustomUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with the given email not found in the database."));

        return new SecurityUser(user,userRoleRepository,roleService);


    }
}
