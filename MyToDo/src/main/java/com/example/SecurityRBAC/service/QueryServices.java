package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface QueryServices {
    Role findRoleName(String rname)throws RuntimeException;
    AppUser findByUsername(String uname)throws UsernameNotFoundException;
}
