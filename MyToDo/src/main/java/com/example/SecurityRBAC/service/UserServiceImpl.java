package com.example.SecurityRBAC.service;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.repo.UserRepo;
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

public class UserServiceImpl implements UserServices {
    private final UserRepo repo;
    private final QueryServiceImpl queryService;

    @Override
    public AppUser saveUser(AppUser user) {
        return repo.save(user);
    }


    @Override
    public void assignRoleToUser(String uname, String rname) {
        AppUser user = queryService.findByUsername(uname);
        Role role = queryService.findRoleName(rname);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String uname) {
        return repo.findByUsername(uname).orElseThrow();
    }

    @Override
    public Page<AppUser> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

}
