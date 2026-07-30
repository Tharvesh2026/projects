package com.example.iaasconsole.security;

import com.example.iaasconsole.config.CloudStackProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CloudStackAuthInterceptor implements ClientHttpRequestInterceptor {

    private final CloudStackProperties properties;
    private final CloudStackSignatureService signatureService;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI originalUri = request.getURI();
        
        // Only sign requests to the CloudStack API URL
        if (!originalUri.toString().startsWith(properties.getBaseUrl())) {
            return execution.execute(request, body);
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(originalUri);
        uriBuilder.queryParam("apikey", properties.getApiKey());
        uriBuilder.queryParam("response", "json");

        URI uriWithParams = uriBuilder.build().toUri();
        String query = uriWithParams.getQuery();

        String signature = signatureService.generateSignature(query, properties.getSecretKey());
        
        // Append signature to URI
        String encodedSignature = URLEncoder.encode(signature, StandardCharsets.UTF_8.name());
        URI finalUri = UriComponentsBuilder.fromUri(uriWithParams)
                .queryParam("signature", encodedSignature)
                .build(true).toUri(); // true = encoded

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return finalUri;
            }
        };

        return execution.execute(requestWrapper, body);
    }
}
