package com.example.iaasconsole.repository;

import com.example.iaasconsole.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUuid(String uuid);
}
