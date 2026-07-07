package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;

public interface QueryServices {
    Role findRoleName(String rname);
    AppUser findByUsername(String uname);
}
