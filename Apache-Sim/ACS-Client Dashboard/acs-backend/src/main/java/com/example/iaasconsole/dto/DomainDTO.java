package com.example.iaasconsole.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainDTO {
    
    @JsonProperty("id")
    private String id; // CloudStack UUID

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("path")
    private String path;
    
    @JsonProperty("parentdomainid")
    private String parentDomainId;
}
