package com.example.iaasconsole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

@SpringBootTest
public class SimulatorApiTest {

    @Autowired
    private RestClient cloudStackRestClient;

    @Test
    public void fetchData() {
        System.out.println("ZONES:");
        System.out.println(cloudStackRestClient.get().uri("/?command=listZones").retrieve().body(String.class));
        
        System.out.println("TEMPLATES:");
        System.out.println(cloudStackRestClient.get().uri("/?command=listTemplates&templatefilter=featured").retrieve().body(String.class));
        
        System.out.println("OFFERINGS:");
        System.out.println(cloudStackRestClient.get().uri("/?command=listServiceOfferings").retrieve().body(String.class));

        System.out.println("NETWORKS:");
        System.out.println(cloudStackRestClient.get().uri("/?command=listNetworks").retrieve().body(String.class));
    }
}
