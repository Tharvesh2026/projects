package com.example.SecurityRBAC.config;

import com.example.SecurityRBAC.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RBACUserDetailService implements UserDetailsService {


    private final UserRepo repo;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }
}
