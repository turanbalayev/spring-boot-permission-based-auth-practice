package com.turanbalayev.permissionbasedauthpractice;

import com.turanbalayev.permissionbasedauthpractice.entity.Permission;
import com.turanbalayev.permissionbasedauthpractice.entity.Role;
import com.turanbalayev.permissionbasedauthpractice.service.PermissionService;
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
	CommandLineRunner commandLineRunner (PermissionService permissionService) {
		return args -> {

			Permission userRead = permissionService.savePermission("book:read");
			Permission userWrite = permissionService.savePermission("book:write");

			log.info(userRead.getName());
			log.info(userWrite.getName());





		};
	}

}
