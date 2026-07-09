package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.repo.RoleRepo;
import com.example.SecurityRBAC.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class QueryServiceImpl implements QueryServices {

    private final RoleRepo r_repo;
    private final UserRepo u_repo;

    @Override
    public Role findRoleName(String name) {

        return r_repo.findByName(name)
                .orElseThrow(() ->
                        new RuntimeException("Role not found: " + name)
                );
    }

    @Override
    public AppUser findByUsername(String uname) {

        return u_repo.findByUsername(uname)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + uname
                        )
                );
    }
}