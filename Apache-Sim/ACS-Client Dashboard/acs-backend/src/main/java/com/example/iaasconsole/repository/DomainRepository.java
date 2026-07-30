package com.example.iaasconsole.repository;

import com.example.iaasconsole.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByUuid(String uuid);
}
