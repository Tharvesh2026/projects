package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.dto.request.CreateRoleRequest;
import com.example.SecurityRBAC.dto.response.RoleResponse;
import com.example.SecurityRBAC.mapper.RoleMapper;
import com.example.SecurityRBAC.repo.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RolesServices {

    private final RoleRepo repo;

    @Override
    public RoleResponse saveRole(CreateRoleRequest request) {

        log.info("Creating role '{}'", request.getName());

        Role role = RoleMapper.toEntity(request);

        Role savedRole = repo.save(role);

        log.info("Role '{}' created successfully with id={}",
                savedRole.getName(),
                savedRole.getId());

        return RoleMapper.toResponse(savedRole);
    }

    @Override
    public Page<RoleResponse> getAllRole(int page, int size) {

        log.info("Fetching roles. page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        return repo.findAll(pageable)
                .map(RoleMapper::toResponse);
    }
}