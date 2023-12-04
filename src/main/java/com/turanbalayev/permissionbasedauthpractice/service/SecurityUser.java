package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.entity.User;
import com.turanbalayev.permissionbasedauthpractice.repository.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {
    private final User user;
    private final UserRoleRepository userRoleRepository;
    private final RoleService roleService;

    public SecurityUser(User user, UserRoleRepository userRoleRepository, RoleService roleService) {
        this.user = user;
        this.userRoleRepository = userRoleRepository;
        this.roleService = roleService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> permissions = new ArrayList<>();

        List<Role> rolesOfUserByUserId = userRoleRepository.getRolesOfUserByUserId(user.getId());

        rolesOfUserByUserId.forEach(role -> {
            List<String> permissionOfRoleByRoleName = roleService.getPermissionOfRoleByRoleName(role.getName());
            permissions.addAll(permissionOfRoleByRoleName);
        });

        List<SimpleGrantedAuthority> authorities = permissions.stream().map(p -> new SimpleGrantedAuthority(p)).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
