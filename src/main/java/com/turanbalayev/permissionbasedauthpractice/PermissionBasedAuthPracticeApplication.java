package com.turanbalayev.permissionbasedauthpractice;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.service.PermissionService;
import com.turanbalayev.permissionbasedauthpractice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class PermissionBasedAuthPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PermissionBasedAuthPracticeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (PermissionService permissionService, RoleService roleService) {
		return args -> {

			Permission bookRead = permissionService.savePermission("book:read");
			Permission bookWrite = permissionService.savePermission("book:write");

			log.info(bookRead.getName());
			log.info(bookWrite.getName());

			Role userRole = roleService.saveRole("ROLE_USER");

			roleService.addPermissionToARole(bookRead.getName(),userRole.getName());
			roleService.addPermissionToARole(bookWrite.getName(),userRole.getName());




		};
	}

}
