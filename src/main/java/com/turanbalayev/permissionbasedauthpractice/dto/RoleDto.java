package com.turanbalayev.permissionbasedauthpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data @NoArgsConstructor @AllArgsConstructor
public class RoleDto {
    private String name;
    private List<String> permissions;
}
