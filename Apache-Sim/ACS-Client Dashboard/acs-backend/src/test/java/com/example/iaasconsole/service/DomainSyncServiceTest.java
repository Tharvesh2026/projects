package com.example.iaasconsole.service;

import com.example.iaasconsole.domain.Domain;
import com.example.iaasconsole.dto.DomainDTO;
import com.example.iaasconsole.dto.ListDomainsResponse;
import com.example.iaasconsole.repository.DomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DomainSyncServiceTest {

    @Mock
    private CloudStackClientService cloudStackClientService;

    @Mock
    private DomainRepository domainRepository;

    @InjectMocks
    private DomainSyncService domainSyncService;

    @Test
    void testSyncDomains_DuplicateNames_ResolvedByUuid() {
        // Arrange
        DomainDTO d1 = new DomainDTO();
        d1.setId("d4e5f6");
        d1.setName("acme-corp");
        d1.setPath("/acme-corp");

        DomainDTO d2 = new DomainDTO();
        d2.setId("g7h8i9");
        d2.setName("acme-corp");
        d2.setPath("/acme-corp2");

        ListDomainsResponse.DomainsResponseWrapper wrapper = new ListDomainsResponse.DomainsResponseWrapper();
        wrapper.setDomains(List.of(d1, d2));
        ListDomainsResponse response = new ListDomainsResponse();
        response.setResponse(wrapper);

        when(cloudStackClientService.listDomains()).thenReturn(response);
        
        // Mock existing DB state - say the first one exists
        Domain existingDomain = new Domain(2L, "d4e5f6", "acme-corp", "/acme-corp");
        when(domainRepository.findByUuid("d4e5f6")).thenReturn(Optional.of(existingDomain));
        when(domainRepository.findByUuid("g7h8i9")).thenReturn(Optional.empty());

        // Act
        domainSyncService.syncDomains();

        // Assert
        verify(domainRepository, times(1)).findByUuid("d4e5f6");
        verify(domainRepository, times(1)).findByUuid("g7h8i9");
        
        // It should save both domains independently (one update, one insert)
        verify(domainRepository, times(2)).save(any(Domain.class));
    }
}
