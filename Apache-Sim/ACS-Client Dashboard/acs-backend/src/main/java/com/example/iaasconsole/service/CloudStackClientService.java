package com.example.iaasconsole.service;

import com.example.iaasconsole.config.CloudStackProperties;
import com.example.iaasconsole.dto.ListDomainsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudStackClientService {

    private final RestClient cloudStackRestClient;
    private final CloudStackProperties properties;

    public ListDomainsResponse listDomains() {
        log.info("Calling CloudStack listDomains API...");
        
        return cloudStackRestClient.get()
                .uri(properties.getBaseUrl() + "?command=listDomains")
                .retrieve()
                .body(ListDomainsResponse.class);
    }

    public String deployVirtualMachine(String name, String templateId, String serviceOfferingId, String zoneId) {
        log.info("Calling CloudStack deployVirtualMachine API...");
        // CloudStack requires templateid, serviceofferingid, zoneid
        String uri = properties.getBaseUrl() + 
            "?command=deployVirtualMachine" +
            "&name=" + name +
            "&templateid=" + templateId +
            "&serviceofferingid=" + serviceOfferingId +
            "&zoneid=" + zoneId;

        return cloudStackRestClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }

    public String createDomain(String name) {
        log.info("Calling CloudStack createDomain API with name: {}", name);
        String uri = properties.getBaseUrl() + 
            "?command=createDomain" +
            "&name=" + name;

        return cloudStackRestClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
}
