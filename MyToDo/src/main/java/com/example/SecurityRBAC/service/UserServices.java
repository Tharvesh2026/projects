package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.dto.request.CreateUserRequest;
import com.example.SecurityRBAC.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserServices {

    UserResponse saveUser(CreateUserRequest request);

    void assignRoleToUser(String username, String roleName);

    UserResponse getUser(String username);

    Page<UserResponse> getAllUser(int page, int size);
}