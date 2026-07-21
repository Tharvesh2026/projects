package projects.icore.CoursePortal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;

    public SecurityConfig(HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository) {
        this.authorizationRequestRepository = authorizationRequestRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabled for REST API convenience; enable if using stateful forms
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Enable H2 Console
            .authorizeHttpRequests(auth -> auth
                // Guest & Other Roles -> Can view courses
                .requestMatchers(HttpMethod.GET, "/api/v1/courses/**", "/courses/**", "/", "/css/**", "/js/**", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Users -> Can register/enroll in courses
                .requestMatchers(HttpMethod.POST, "/api/v1/enrollments/**", "/enrollments/**").hasAnyRole("USER", "ADMIN", "SYS_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/enrollments/student/**").hasAnyRole("USER", "ADMIN", "SYS_ADMIN")

                // Admin -> Can able to verify all enrollments
                .requestMatchers("/api/v1/enrollments/verify/**", "/api/v1/enrollments/course/**").hasAnyRole("ADMIN", "SYS_ADMIN")

                // SYS Admin -> Can manage all (Create, Edit, Delete courses; Manage students)
                .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasRole("SYS_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasRole("SYS_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasRole("SYS_ADMIN")
                .requestMatchers("/api/v1/students/**").hasRole("SYS_ADMIN")

                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                    .authorizationRequestRepository(authorizationRequestRepository)
                )
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(oauth2UserService())
                )
                .defaultSuccessUrl("/courses", true)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());

            // Extract role group from OIDC UserInfo claim 'role'
            Object roleObj = oAuth2User.getAttribute("role");
            if (roleObj != null) {
                String roleName = roleObj.toString().toUpperCase().trim();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
            }

            String nameAttributeKey = userRequest.getClientRegistration()
                    .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
            if (nameAttributeKey == null || nameAttributeKey.isBlank()) {
                nameAttributeKey = "sub";
            }

            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), nameAttributeKey);
        };
    }
}
