package project.module.SpringSecurity.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @org.springframework.beans.factory.annotation.Autowired
    private project.module.SpringSecurity.repository.UsersRepo usersRepo;

    @org.springframework.beans.factory.annotation.Autowired
    private project.module.SpringSecurity.repository.RoleRepo roleRepo;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        populateAuthAttributes(model, authentication);
        return "index";
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        populateAuthAttributes(model, authentication);
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        populateAuthAttributes(model, authentication);
        return "profile";
    }

    @GetMapping("/users")
    public String users(Model model, Authentication authentication) {
        populateAuthAttributes(model, authentication);
        model.addAttribute("users", usersRepo.findAll());
        return "users";
    }

    @GetMapping("/roles")
    public String roles(Model model, Authentication authentication) {
        populateAuthAttributes(model, authentication);
        model.addAttribute("allRoles", roleRepo.findAll());
        return "roles";
    }

    @GetMapping("/settings")
    public String settings(Model model, Authentication authentication) {
        populateAuthAttributes(model, authentication);
        return "settings";
    }

    private void populateAuthAttributes(Model model, Authentication authentication) {
        if (authentication == null) {
            model.addAttribute("username", "Guest");
            model.addAttribute("email", "N/A");
            model.addAttribute("provider", "NONE");
            model.addAttribute("roles", "NONE");
            return;
        }

        String displayName = "User";
        String email = "N/A";
        String provider = "LOCAL";
        String rolesStr = "";

        if (authentication.getPrincipal() instanceof project.module.SpringSecurity.entity.CustomOAuth2User oauthUser) {
            project.module.SpringSecurity.entity.Users dbUser = oauthUser.getDbUser();
            displayName = dbUser.getName();
            email = dbUser.getEmail();
            provider = dbUser.getProvider() != null ? dbUser.getProvider() : "OAUTH2";
            rolesStr = oauthUser.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(java.util.stream.Collectors.joining(", "));
        } else if (authentication.getPrincipal() instanceof project.module.SpringSecurity.entity.Users localUser) {
            displayName = localUser.getName();
            email = localUser.getEmail();
            provider = localUser.getProvider() != null ? localUser.getProvider() : "LOCAL";
            rolesStr = localUser.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(java.util.stream.Collectors.joining(", "));
        } else {
            displayName = authentication.getName();
            rolesStr = authentication.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(java.util.stream.Collectors.joining(", "));
        }

        model.addAttribute("username", displayName);
        model.addAttribute("email", email);
        model.addAttribute("provider", provider);
        model.addAttribute("roles", rolesStr);
    }
}

