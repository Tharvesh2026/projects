package com.example.iaasconsole.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListDomainsResponse {
    
    @JsonProperty("listdomainsresponse")
    private DomainsResponseWrapper response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DomainsResponseWrapper {
        @JsonProperty("count")
        private Integer count;
        
        @JsonProperty("domain")
        private List<DomainDTO> domains;
    }
}
