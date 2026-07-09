package com.example.SecurityRBAC.dto.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    private String name;

    private String username;

    private String password;

    private List<Long> roleIds;
}