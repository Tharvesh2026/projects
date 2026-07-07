package com.example.SecurityRBAC;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.service.RolesServices;
import com.example.SecurityRBAC.service.UserServiceImpl;
import com.example.SecurityRBAC.service.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityRBACApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityRBACApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserServices userService, RolesServices roleService){
		return args ->
		{
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_MANAGER"));
			roleService.saveRole(new Role(null, "ROLE_MODERATOR"));
			roleService.saveRole(new Role(null, "ROLE_EDITOR"));
			roleService.saveRole(new Role(null, "ROLE_USER"));


			userService.saveUser(new AppUser(null, "Alice Johnson", "alice", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Bob Smith", "bob", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Charlie Brown", "charlie", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "David Wilson", "david", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Emma Davis", "emma", "1234", new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Frank Miller", "frank", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Grace Taylor", "grace", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Henry Anderson", "henry", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Isabella Thomas", "isabella", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Jack Moore", "jack", "1234", new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Karen Martin", "karen", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Liam Jackson", "liam", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Mia White", "mia", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Noah Harris", "noah", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Olivia Clark", "olivia", "1234", new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Peter Lewis", "peter", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Queen Walker", "queen", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Ryan Hall", "ryan", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Sophia Allen", "sophia", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Thomas Young", "thomas", "1234", new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Uma King", "uma", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Victor Scott", "victor", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "William Green", "william", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Xavier Baker", "xavier", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Zoe Adams", "zoe", "1234", new ArrayList<>()));

			// Admins
			userService.assignRoleToUser("alice", "ROLE_ADMIN");
			userService.assignRoleToUser("alice", "ROLE_MANAGER");

			userService.assignRoleToUser("bob", "ROLE_ADMIN");
			userService.assignRoleToUser("bob", "ROLE_EDITOR");

// Managers
			userService.assignRoleToUser("charlie", "ROLE_MANAGER");
			userService.assignRoleToUser("charlie", "ROLE_USER");

			userService.assignRoleToUser("david", "ROLE_MANAGER");
			userService.assignRoleToUser("david", "ROLE_MODERATOR");

// Editors
			userService.assignRoleToUser("emma", "ROLE_EDITOR");
			userService.assignRoleToUser("frank", "ROLE_EDITOR");
			userService.assignRoleToUser("grace", "ROLE_EDITOR");

// Moderators
			userService.assignRoleToUser("henry", "ROLE_MODERATOR");
			userService.assignRoleToUser("isabella", "ROLE_MODERATOR");
			userService.assignRoleToUser("jack", "ROLE_MODERATOR");

// Mixed roles
			userService.assignRoleToUser("karen", "ROLE_USER");
			userService.assignRoleToUser("karen", "ROLE_EDITOR");

			userService.assignRoleToUser("liam", "ROLE_USER");
			userService.assignRoleToUser("liam", "ROLE_MANAGER");

			userService.assignRoleToUser("mia", "ROLE_USER");
			userService.assignRoleToUser("mia", "ROLE_MODERATOR");

			userService.assignRoleToUser("noah", "ROLE_EDITOR");
			userService.assignRoleToUser("noah", "ROLE_MODERATOR");

			userService.assignRoleToUser("olivia", "ROLE_MANAGER");
			userService.assignRoleToUser("olivia", "ROLE_EDITOR");

// Remaining users get ROLE_USER
			userService.assignRoleToUser("peter", "ROLE_USER");
			userService.assignRoleToUser("queen", "ROLE_USER");
			userService.assignRoleToUser("ryan", "ROLE_USER");
			userService.assignRoleToUser("sophia", "ROLE_USER");
			userService.assignRoleToUser("thomas", "ROLE_USER");
			userService.assignRoleToUser("uma", "ROLE_USER");
			userService.assignRoleToUser("victor", "ROLE_USER");
			userService.assignRoleToUser("william", "ROLE_USER");
			userService.assignRoleToUser("xavier", "ROLE_USER");
			userService.assignRoleToUser("zoe", "ROLE_USER");

// Users with three roles
			userService.assignRoleToUser("alice", "ROLE_EDITOR");
			userService.assignRoleToUser("olivia", "ROLE_USER");
			userService.assignRoleToUser("noah", "ROLE_USER");

		};
	}
}
