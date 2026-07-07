package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.Role;
import org.springframework.data.domain.Page;

public interface RolesServices {
    Role saveRole(Role role);
    Page<Role> getAllRole(int page, int size);
}
