package project.module.SpringSecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security){
        // Disable the CSRF Validation Filters
        security.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated()) //Authorize All API
                //Login Form Default Disabled Frontend .formLogin(Customizer.withDefaults());
                //API Client (Postman)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return security.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1= User
                .withDefaultPasswordEncoder()
                .username("user1")
                .password("1234")
                .roles("USER")
                .build();
        UserDetails user2= User
                .withDefaultPasswordEncoder()
                .username("user2")
                .password("1234")
                .roles("USER")
                .build();
        UserDetails user3= User
                .withDefaultPasswordEncoder()
                .username("user3")
                .password("1234")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1,user2, user3);
    }
}
