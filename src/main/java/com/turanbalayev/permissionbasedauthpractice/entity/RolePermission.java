package com.turanbalayev.permissionbasedauthpractice.entity;

import com.turanbalayev.permissionbasedauthpractice.entity.composite.RolePermissionId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "role_permission") @Getter @Setter @NoArgsConstructor
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;


    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id",foreignKey = @ForeignKey(name = "role_permission_role_id_fk"))
    private Role role;


    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id",foreignKey = @ForeignKey(name = "role_permission_permission_id_fk"))
    private Permission permission;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDate createdAt;

    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }
}
