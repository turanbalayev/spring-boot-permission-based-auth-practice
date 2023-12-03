package com.turanbalayev.permissionbasedauthpractice.service;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    Permission savePermission(String permissionName);
    Permission getPermissionByName(String permissionName);

    List<String> getAllPermissions();


}
