package com.example.SecurityRBAC.dto;

import com.example.SecurityRBAC.dto.response.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {

    private Long id;
    private String name;
    private String username;
    private List<RoleDTO> roles;
}