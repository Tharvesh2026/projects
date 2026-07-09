package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.dto.request.CreateUserRequest;
import com.example.SecurityRBAC.dto.response.UserResponse;
import com.example.SecurityRBAC.mapper.UserMapper;
import com.example.SecurityRBAC.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserServices {

    private final UserRepo repo;
    private final QueryServiceImpl queryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse saveUser(CreateUserRequest request) {

        log.info("Creating user '{}'", request.getUsername());

        AppUser user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role defaultRole = queryService.findRoleName("ROLE_USER");

        user.getRoles().add(defaultRole);

        AppUser saved = repo.save(user);

        log.info("User '{}' created successfully", saved.getUsername());

        return UserMapper.toResponse(saved);
    }

    @Override
    public void assignRoleToUser(String username, String roleName) {

        log.info("Assigning role '{}' to user '{}'", roleName, username);

        AppUser user = queryService.findByUsername(username);

        Role role = queryService.findRoleName(roleName);

        user.getRoles().add(role);

        log.info("Role '{}' assigned to user '{}'", roleName, username);
    }

    @Override
    public UserResponse getUser(String username) {

        log.info("Fetching user '{}'", username);

        AppUser user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUser(int page, int size) {

        log.info("Fetching users. page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        return repo.findAll(pageable)
                .map(UserMapper::toResponse);
    }
}