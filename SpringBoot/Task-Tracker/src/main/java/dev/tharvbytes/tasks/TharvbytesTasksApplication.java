package dev.tharvbytes.tasks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@SpringBootApplication
public class TharvbytesTasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(TharvbytesTasksApplication.class, args);
    }



    @Bean
CommandLineRunner test(ClientRegistrationRepository repo) {
    return args -> {
        ClientRegistration reg =
                ((InMemoryClientRegistrationRepository) repo)
                        .findByRegistrationId("icore");

        System.out.println("Client ID      : " + reg.getClientId());
        System.out.println("Client Secret  : " + reg.getClientSecret());
        System.out.println("Redirect URI   : " + reg.getRedirectUri());

        System.out.println("Authorization  : "
                + reg.getProviderDetails().getAuthorizationUri());

        System.out.println("Token          : "
                + reg.getProviderDetails().getTokenUri());

        System.out.println("UserInfo       : "
                + reg.getProviderDetails().getUserInfoEndpoint().getUri());

        System.out.println("JWK Set        : "
                + reg.getProviderDetails().getJwkSetUri());

        System.out.println("Issuer         : "
                + reg.getProviderDetails().getIssuerUri());
    };
}
    
}
