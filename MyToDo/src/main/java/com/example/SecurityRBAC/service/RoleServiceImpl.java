package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.repo.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RoleServiceImpl implements RolesServices{
    private final RoleRepo repo;

    @Override
    public Role saveRole(Role role) {
        return repo.save(role);
    }

    @Override
    public Page<Role> getAllRole(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);

    }
}
