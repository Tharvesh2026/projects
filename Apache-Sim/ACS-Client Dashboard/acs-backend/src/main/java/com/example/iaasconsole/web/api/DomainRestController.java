package com.example.iaasconsole.web.api;

import com.example.iaasconsole.domain.Domain;
import com.example.iaasconsole.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
@RequiredArgsConstructor
public class DomainRestController {

    private final DomainRepository domainRepository;

    @GetMapping
    public ResponseEntity<List<Domain>> getAllDomains() {
        return ResponseEntity.ok(domainRepository.findAll());
    }
}
