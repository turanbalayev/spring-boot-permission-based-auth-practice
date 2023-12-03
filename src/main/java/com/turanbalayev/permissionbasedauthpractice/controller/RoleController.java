package com.turanbalayev.permissionbasedauthpractice.controller;

import com.turanbalayev.permissionbasedauthpractice.dto.RoleDto;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        return new ResponseEntity<RoleDto>(roleService.saveRole(roleDto), HttpStatus.CREATED);
    }

    @GetMapping("/permissions/{roleName}")
    public ResponseEntity<List<String>> getPermissionsOfRole(@PathVariable String roleName){
        return new ResponseEntity<>(roleService.getPermissionOfRoleByRoleName(roleName),HttpStatus.OK);
    }

    @PostMapping("/{permissionName}/{roleName}")
    public ResponseEntity<String> addPermissionToARole(@PathVariable String permissionName, @PathVariable String roleName){
        roleService.addPermissionToARole(permissionName, roleName);
        return ResponseEntity.ok(permissionName + " has been added to a " + roleName + " successfully.");
    }

    @PutMapping("/update/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto, @PathVariable Long roleId){
        return new ResponseEntity<>(roleService.updateRole(roleDto,roleId),HttpStatus.OK);
    }


    @PutMapping("/remove/{roleId}/{permissionId}")
    public ResponseEntity<RoleDto> removePermissionFromARole(@PathVariable Long roleId, @PathVariable Long permissionId){
        return new ResponseEntity<>(roleService.removePermissionFromARole(roleId,permissionId),HttpStatus.OK);
    }


}
