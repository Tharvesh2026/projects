package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import org.springframework.data.domain.Page;

public interface UserServices {
    AppUser saveUser(AppUser user);

    void assignRoleToUser(String uname, String rname);
    AppUser getUser(String uname);

    Page<AppUser> getAllUser(int page, int size);

}
