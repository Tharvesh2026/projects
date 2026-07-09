package com.example.SecurityRBAC.mapper;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.dto.request.CreateUserRequest;
import com.example.SecurityRBAC.dto.response.RoleResponse;
import com.example.SecurityRBAC.dto.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(AppUser user) {

        if (user == null)
            return null;

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .roles(user.getRoles()
                        .stream()
                        .map(UserMapper::toRoleResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public static RoleResponse toRoleResponse(Role role) {

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static AppUser toEntity(CreateUserRequest request) {

        if (request == null) {
            return null;
        }

        AppUser user = new AppUser();

        user.setName(request.getName());
        user.setUsername(request.getUsername());

        return user;
    }

    public static List<UserResponse> toResponseList(List<AppUser> users) {

        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}