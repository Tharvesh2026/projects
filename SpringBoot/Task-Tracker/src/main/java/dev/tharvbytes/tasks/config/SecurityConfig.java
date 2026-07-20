package dev.tharvbytes.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                    .authorizationRequestResolver(authorizationRequestResolver(clientRegistrationRepository))
                    .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                )
                .userInfoEndpoint(userInfo -> userInfo
                    .userAuthoritiesMapper(userAuthoritiesMapper())
                )
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
            );

        return http.build();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    private OAuth2AuthorizationRequestResolver authorizationRequestResolver(ClientRegistrationRepository repo) {
        DefaultOAuth2AuthorizationRequestResolver resolver = 
            new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
        return resolver;
    }

    /**
     * Map the authorities retrieved during OIDC login.
     * Extracts "role" and "permissions" custom claims issued by I.Core
     * and registers them as GrantedAuthorities in Spring Security.
     */
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                // Keep the default OIDC scope-based/standard authorities
                mappedAuthorities.add(authority);

                if (authority instanceof OidcUserAuthority oidcAuthority) {
                    OidcIdToken idToken = oidcAuthority.getIdToken();

                    // Extract custom "role" claim (e.g., "ADMIN" or "USER")
                    String role = idToken.getClaimAsString("role");
                    if (role != null) {
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                    }

                    // Extract custom "permissions" array (e.g., ["USER_READ", "TASK_DELETE"])
                    List<String> permissions = idToken.getClaimAsStringList("permissions");
                    if (permissions != null) {
                        for (String perm : permissions) {
                            mappedAuthorities.add(new SimpleGrantedAuthority(perm));
                        }
                    }
                }
            });

            return mappedAuthorities;
        };
    }
}
