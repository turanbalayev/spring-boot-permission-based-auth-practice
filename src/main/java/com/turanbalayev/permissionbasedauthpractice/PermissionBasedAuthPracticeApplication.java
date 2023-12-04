package com.turanbalayev.permissionbasedauthpractice;

import com.turanbalayev.permissionbasedauthpractice.dto.RoleDto;
import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.entity.User;
import com.turanbalayev.permissionbasedauthpractice.repository.UserRoleRepository;
import com.turanbalayev.permissionbasedauthpractice.service.PermissionService;
import com.turanbalayev.permissionbasedauthpractice.service.RoleService;
import com.turanbalayev.permissionbasedauthpractice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class PermissionBasedAuthPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionBasedAuthPracticeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PermissionService permissionService,
                                        RoleService roleService,
                                        UserService userService,
                                        PasswordEncoder passwordEncoder) {
        return args -> {

            // Create permissions
            Permission bookRead = permissionService.savePermission("book:read");
            Permission bookWrite = permissionService.savePermission("book:write");
            Permission productRead = permissionService.savePermission("product:read");
            Permission productWrite = permissionService.savePermission("product:write");
            Permission roleRead = permissionService.savePermission("role:read");
            Permission roleWrite = permissionService.savePermission("role:write");

            log.info("These permissions added: {} {} {} {} {} {}",
                    bookRead.getName(),
                    bookWrite.getName(),
                    productRead.getName(),
                    productWrite.getName(),
                    roleRead.getName(),
                    roleWrite.getName());


            // Create Roles with permissions
            List<String> userPermissions = new ArrayList<>();
            userPermissions.add("role:read");

            List<String> adminPermissions = new ArrayList<>();
            adminPermissions.add("role:write");

            RoleDto role_user = roleService.saveRole(new RoleDto("ROLE_USER", userPermissions));
            RoleDto role_admin = roleService.saveRole(new RoleDto("ROLE_ADMIN", adminPermissions));


            // Create users
            User turan = userService.saveUser(new User("Turan", "Balayev", "turan_balayev@gmail.com",
                    passwordEncoder.encode("12345")));
            User john = userService.saveUser(new User("John", "Doe", "john_doe@gmail.com",
                    passwordEncoder.encode("12345")));


            // Add roles to users
            userService.addRoleToUser(role_user.getName(), turan.getEmail());
            userService.addRoleToUser(role_admin.getName(), john.getEmail());


        };
    }

    /*

    @Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();

	}*/

}
