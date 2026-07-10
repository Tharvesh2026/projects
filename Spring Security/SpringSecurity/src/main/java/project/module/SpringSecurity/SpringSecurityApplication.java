package project.module.SpringSecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;
import project.module.SpringSecurity.entity.Users;
import project.module.SpringSecurity.repository.UsersRepo;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			project.module.SpringSecurity.repository.UsersRepo usersRepo,
			project.module.SpringSecurity.repository.RoleRepo roleRepo,
			PasswordEncoder passwordEncoder) {
		return args -> {
			// Seed roles
			project.module.SpringSecurity.entity.Role roleViewer = new project.module.SpringSecurity.entity.Role();
			roleViewer.setName("ROLE_VIEWER");
			roleViewer = roleRepo.save(roleViewer);

			project.module.SpringSecurity.entity.Role roleAdmin = new project.module.SpringSecurity.entity.Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleAdmin = roleRepo.save(roleAdmin);

			project.module.SpringSecurity.entity.Role roleDomainAdmin = new project.module.SpringSecurity.entity.Role();
			roleDomainAdmin.setName("ROLE_DOMAIN_ADMIN");
			roleDomainAdmin = roleRepo.save(roleDomainAdmin);

			project.module.SpringSecurity.entity.Role roleSysAdmin = new project.module.SpringSecurity.entity.Role();
			roleSysAdmin.setName("ROLE_SYS_ADMIN");
			roleSysAdmin = roleRepo.save(roleSysAdmin);

			// Seed Users
			// 1. Viewer
			Users userViewer = new Users();
			userViewer.setName("Bob Viewer");
			userViewer.setEmail("viewer@gmail.com");
			userViewer.setUsername("viewer");
			userViewer.setPassword(passwordEncoder.encode("viewer123"));
			userViewer.setProvider("LOCAL");
			userViewer.setRoles(new java.util.ArrayList<>(java.util.List.of(roleViewer)));
			usersRepo.save(userViewer);

			// 2. Admin (Manager based access)
			Users userAdmin = new Users();
			userAdmin.setName("John Admin");
			userAdmin.setEmail("admin@gmail.com");
			userAdmin.setUsername("admin");
			userAdmin.setPassword(passwordEncoder.encode("admin123"));
			userAdmin.setProvider("LOCAL");
			userAdmin.setRoles(new java.util.ArrayList<>(java.util.List.of(roleAdmin)));
			usersRepo.save(userAdmin);

			// 3. Domain Admin
			Users userDomain = new Users();
			userDomain.setName("Alice Domain");
			userDomain.setEmail("domain@gmail.com");
			userDomain.setUsername("domain");
			userDomain.setPassword(passwordEncoder.encode("domain123"));
			userDomain.setProvider("LOCAL");
			userDomain.setRoles(new java.util.ArrayList<>(java.util.List.of(roleDomainAdmin)));
			usersRepo.save(userDomain);

			// 4. SYS Admin
			Users userSys = new Users();
			userSys.setName("Super SysAdmin");
			userSys.setEmail("sysadmin@gmail.com");
			userSys.setUsername("sysadmin");
			userSys.setPassword(passwordEncoder.encode("sys123"));
			userSys.setProvider("LOCAL");
			userSys.setRoles(new java.util.ArrayList<>(java.util.List.of(roleSysAdmin)));
			usersRepo.save(userSys);

			System.out.println("Sample roles and users seeded successfully!");
		};
	}

}