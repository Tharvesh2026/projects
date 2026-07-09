package com.example.SecurityRBAC;

import com.example.SecurityRBAC.dto.request.CreateRoleRequest;
import com.example.SecurityRBAC.dto.request.CreateUserRequest;
import com.example.SecurityRBAC.service.RolesServices;
import com.example.SecurityRBAC.service.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SecurityRBACApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityRBACApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserServices userService,
			RolesServices roleService) {

		return args -> {

			List.of(
					new CreateRoleRequest("ROLE_ADMIN"),
					new CreateRoleRequest("ROLE_MANAGER"),
					new CreateRoleRequest("ROLE_MODERATOR"),
					new CreateRoleRequest("ROLE_EDITOR"),
					new CreateRoleRequest("ROLE_USER")
			).forEach(roleService::saveRole);

			List.of(
					new CreateUserRequest("Alice Johnson", "alice", "123456"),
					new CreateUserRequest("Bob Smith", "bob", "123456"),
					new CreateUserRequest("Charlie Brown", "charlie", "123456"),
					new CreateUserRequest("David Wilson", "david", "123456"),
					new CreateUserRequest("Emma Davis", "emma", "123456"),
					new CreateUserRequest("Frank Miller", "frank", "123456"),
					new CreateUserRequest("Grace Taylor", "grace", "123456"),
					new CreateUserRequest("Henry Anderson", "henry", "123456"),
					new CreateUserRequest("Isabella Thomas", "isabella", "123456"),
					new CreateUserRequest("Jack Moore", "jack", "123456"),
					new CreateUserRequest("Karen Martin", "karen", "123456"),
					new CreateUserRequest("Liam Jackson", "liam", "123456"),
					new CreateUserRequest("Mia White", "mia", "123456"),
					new CreateUserRequest("Noah Harris", "noah", "123456"),
					new CreateUserRequest("Olivia Clark", "olivia", "123456"),
					new CreateUserRequest("Peter Lewis", "peter", "123456"),
					new CreateUserRequest("Queen Walker", "queen", "123456"),
					new CreateUserRequest("Ryan Hall", "ryan", "123456"),
					new CreateUserRequest("Sophia Allen", "sophia", "123456"),
					new CreateUserRequest("Thomas Young", "thomas", "123456"),
					new CreateUserRequest("Uma King", "uma", "123456"),
					new CreateUserRequest("Victor Scott", "victor", "123456"),
					new CreateUserRequest("William Green", "william", "123456"),
					new CreateUserRequest("Xavier Baker", "xavier", "123456"),
					new CreateUserRequest("Zoe Adams", "zoe", "123456")
			).forEach(userService::saveUser);


			assign(userService, "alice", "ROLE_ADMIN");
			assign(userService, "alice", "ROLE_MANAGER");

			assign(userService, "bob", "ROLE_ADMIN");
			assign(userService, "bob", "ROLE_EDITOR");

			assign(userService, "charlie", "ROLE_MANAGER");
			assign(userService, "charlie", "ROLE_USER");

			assign(userService, "david", "ROLE_MANAGER");
			assign(userService, "david", "ROLE_MODERATOR");

			assign(userService, "emma", "ROLE_EDITOR");
			assign(userService, "frank", "ROLE_EDITOR");
			assign(userService, "grace", "ROLE_EDITOR");

			assign(userService, "henry", "ROLE_MODERATOR");
			assign(userService, "isabella", "ROLE_MODERATOR");
			assign(userService, "jack", "ROLE_MODERATOR");

			assign(userService, "karen", "ROLE_USER");
			assign(userService, "karen", "ROLE_EDITOR");

			assign(userService, "liam", "ROLE_USER");
			assign(userService, "liam", "ROLE_MANAGER");

			assign(userService, "mia", "ROLE_USER");
			assign(userService, "mia", "ROLE_MODERATOR");

			assign(userService, "noah", "ROLE_EDITOR");
			assign(userService, "noah", "ROLE_MODERATOR");

			assign(userService, "olivia", "ROLE_MANAGER");
			assign(userService, "olivia", "ROLE_EDITOR");

			List.of(
					"peter", "queen", "ryan", "sophia", "thomas",
					"uma", "victor", "william", "xavier", "zoe"
			).forEach(username -> assign(userService, username, "ROLE_USER"));

			assign(userService, "alice", "ROLE_EDITOR");
			assign(userService, "olivia", "ROLE_USER");
			assign(userService, "noah", "ROLE_USER");
		};
	}

	private static void assign(UserServices service, String username, String role) {
		service.assignRoleToUser(username, role);
	}
}