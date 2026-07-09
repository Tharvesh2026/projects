package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.dto.request.CreateRoleRequest;
import com.example.SecurityRBAC.dto.response.RoleResponse;
import org.springframework.data.domain.Page;

public interface RolesServices {

    RoleResponse saveRole(CreateRoleRequest request);

    Page<RoleResponse> getAllRole(int page, int size);
}
