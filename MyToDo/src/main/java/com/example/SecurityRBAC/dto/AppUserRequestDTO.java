package com.example.SecurityRBAC.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequestDTO {

    private String name;

    private String username;

    @Size(min = 6)
    private String password;

    // Role IDs to assign
    private List<Long> roleIds;
}