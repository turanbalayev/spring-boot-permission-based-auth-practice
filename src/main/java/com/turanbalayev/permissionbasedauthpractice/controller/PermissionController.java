package com.turanbalayev.permissionbasedauthpractice.controller;

import com.turanbalayev.permissionbasedauthpractice.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    ResponseEntity<List<String>> getAllPermissions(){
        return new ResponseEntity<List<String>>(permissionService.getAllPermissions(), HttpStatus.ACCEPTED);
    }


}
