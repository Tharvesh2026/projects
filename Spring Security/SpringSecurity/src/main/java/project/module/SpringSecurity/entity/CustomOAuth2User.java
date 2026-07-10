package project.module.SpringSecurity.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oauth2User;
    private final Users dbUser;

    public CustomOAuth2User(OAuth2User oauth2User, Users dbUser) {
        this.oauth2User = oauth2User;
        this.dbUser = dbUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return dbUser.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getName();
    }

    public Users getDbUser() {
        return dbUser;
    }
    
    public String getEmail() {
        return dbUser.getEmail();
    }
}
