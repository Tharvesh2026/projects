package project.module.SpringSecurity.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.module.SpringSecurity.entity.Role;
import project.module.SpringSecurity.entity.Users;
import project.module.SpringSecurity.repository.RoleRepo;
import project.module.SpringSecurity.repository.UsersRepo;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepo userRepo;
    private final RoleRepo roleRepo;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauthUser = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId(); // "google" or "github"

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String providerId = oauthUser.getName(); // "sub" for google, "id" for github

        // GitHub sometimes email null ah kudukum (private email setting)
        if (email == null) {
            email = providerId + "@" + provider + ".oauth"; // fallback placeholder
        }

        if (name == null) {
            name = oauthUser.getAttribute("login"); // GitHub username fallback
        }

        Users user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            user = new Users();
            user.setEmail(email);
            user.setName(name);
            user.setUsername(email);
            user.setProvider(provider.toUpperCase());
            user.setProviderId(providerId);

            Role defaultRole = roleRepo.findByName("ROLE_VIEWER")
                    .orElseThrow(() -> new RuntimeException("ROLE_VIEWER not found in DB"));
            user.setRoles(new ArrayList<>(List.of(defaultRole)));

            user = userRepo.save(user);
        }

        return new project.module.SpringSecurity.entity.CustomOAuth2User(oauthUser, user);
    }
}