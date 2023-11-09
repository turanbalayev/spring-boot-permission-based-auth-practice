package com.turanbalayev.permissionbasedauthpractice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "permission") @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions = new ArrayList<>();

    public Permission(String name) {
        this.name = name;
    }
}
