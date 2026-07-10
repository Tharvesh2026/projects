
package project.module.SpringSecurity.config;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.module.SpringSecurity.repository.UsersRepo;

@Service
@RequiredArgsConstructor
public class RBACUserDetailService implements UserDetailsService {


    private final UsersRepo repo;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByEmail(username)
                .or(() -> repo.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
    }
}
