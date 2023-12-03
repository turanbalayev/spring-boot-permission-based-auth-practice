package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{

    private final PermissionRepository permissionRepository;

    @Override
    public Permission savePermission(String permissionName) {
        permissionRepository.findByName(permissionName).ifPresent(permission -> {
            throw new RuntimeException("Permission with the given name is present on the database");
        });

        return permissionRepository.save(new Permission(permissionName));
    }

    @Override
    public Permission getPermissionByName(String permissionName) {
        return permissionRepository.findByName(permissionName).orElseThrow(() ->
                new RuntimeException("Permission not found with the given name"));
    }

    @Override
    public List<String> getAllPermissions() {
        return permissionRepository.findAll().stream().map(p -> p.getName()).collect(Collectors.toList());
    }
}
