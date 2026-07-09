package com.example.SecurityRBAC.mapper;

import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.dto.request.CreateRoleRequest;
import com.example.SecurityRBAC.dto.response.RoleResponse;

public class RoleMapper {

    private RoleMapper() {
    }

    public static Role toEntity(CreateRoleRequest request) {

        return Role.builder()
                .name(request.getName())
                .build();
    }

    public static RoleResponse toResponse(Role role) {

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}