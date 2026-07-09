package com.example.SecurityRBAC;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.service.RolesServices;
import com.example.SecurityRBAC.service.UserServiceImpl;
import com.example.SecurityRBAC.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityRBACApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

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

			userService.saveUser(new AppUser(null, "Alice Johnson", "alice", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Bob Smith", "bob", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Charlie Brown", "charlie", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "David Wilson", "david", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Emma Davis", "emma", passwordEncoder.encode("123456"), new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Frank Miller", "frank", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Grace Taylor", "grace", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Henry Anderson", "henry", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Isabella Thomas", "isabella", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Jack Moore", "jack", passwordEncoder.encode("123456"), new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Karen Martin", "karen", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Liam Jackson", "liam", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Mia White", "mia", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Noah Harris", "noah", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Olivia Clark", "olivia", passwordEncoder.encode("123456"), new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Peter Lewis", "peter", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Queen Walker", "queen", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Ryan Hall", "ryan", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Sophia Allen", "sophia", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Thomas Young", "thomas", passwordEncoder.encode("123456"), new ArrayList<>()));

			userService.saveUser(new AppUser(null, "Uma King", "uma", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Victor Scott", "victor", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "William Green", "william", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Xavier Baker", "xavier", passwordEncoder.encode("123456"), new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Zoe Adams", "zoe", passwordEncoder.encode("123456"), new ArrayList<>()));

			userService.assignRoleToUser("alice", "ROLE_ADMIN");
			userService.assignRoleToUser("alice", "ROLE_MANAGER");

			userService.assignRoleToUser("bob", "ROLE_ADMIN");
			userService.assignRoleToUser("bob", "ROLE_EDITOR");

			userService.assignRoleToUser("charlie", "ROLE_MANAGER");
			userService.assignRoleToUser("charlie", "ROLE_USER");

			userService.assignRoleToUser("david", "ROLE_MANAGER");
			userService.assignRoleToUser("david", "ROLE_MODERATOR");

			userService.assignRoleToUser("emma", "ROLE_EDITOR");
			userService.assignRoleToUser("frank", "ROLE_EDITOR");
			userService.assignRoleToUser("grace", "ROLE_EDITOR");

			userService.assignRoleToUser("henry", "ROLE_MODERATOR");
			userService.assignRoleToUser("isabella", "ROLE_MODERATOR");
			userService.assignRoleToUser("jack", "ROLE_MODERATOR");

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

			userService.assignRoleToUser("alice", "ROLE_EDITOR");
			userService.assignRoleToUser("olivia", "ROLE_USER");
			userService.assignRoleToUser("noah", "ROLE_USER");

		};
	}
}
