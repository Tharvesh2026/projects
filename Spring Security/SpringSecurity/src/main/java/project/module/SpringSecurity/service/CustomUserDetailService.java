package project.module.SpringSecurity.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.module.SpringSecurity.entity.Users;
import project.module.SpringSecurity.repository.UserDetailRepo;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserDetailRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.getByName(username);
        if(user==null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return null;
    }
}
