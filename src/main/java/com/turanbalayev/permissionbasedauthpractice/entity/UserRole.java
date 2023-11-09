package com.turanbalayev.permissionbasedauthpractice.entity;

import com.turanbalayev.permissionbasedauthpractice.entity.composite.UserRoleId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "user_role") @Getter @Setter @NoArgsConstructor
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_role_user_id_fk"))
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "user_role_role_id_fk"))
    private Role role;


    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
