package com.example.iaasconsole.service;

import com.example.iaasconsole.domain.Domain;
import com.example.iaasconsole.dto.DomainDTO;
import com.example.iaasconsole.dto.ListDomainsResponse;
import com.example.iaasconsole.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainSyncService {

    private final CloudStackClientService cloudStackClientService;
    private final DomainRepository domainRepository;

    @Transactional
    public void syncDomains() {
        log.info("Starting domain synchronization...");
        
        ListDomainsResponse response = cloudStackClientService.listDomains();
        
        if (response != null && response.getResponse() != null && response.getResponse().getDomains() != null) {
            for (DomainDTO dto : response.getResponse().getDomains()) {
                Domain domain = domainRepository.findByUuid(dto.getId())
                        .orElse(new Domain());
                
                domain.setUuid(dto.getId());
                domain.setName(dto.getName());
                domain.setPath(dto.getPath());
                
                domainRepository.save(domain);
                log.info("Synced domain: {} (UUID: {})", domain.getName(), domain.getUuid());
            }
        }
        
        log.info("Domain synchronization complete.");
    }
}
