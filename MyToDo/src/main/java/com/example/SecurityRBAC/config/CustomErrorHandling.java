package com.example.SecurityRBAC.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.webmvc.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
@Slf4j
public class CustomErrorHandling extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest,
            org.springframework.boot.web.error.ErrorAttributeOptions options) {

        Map<String, Object> attributes =
                super.getErrorAttributes(webRequest, options);

        Throwable error = getError(webRequest);

        log.error("""
                
                ========= APPLICATION ERROR =========
                Status    : {}
                Path      : {}
                Message   : {}
                Exception : {}
                =====================================
                """,
                attributes.get("status"),
                attributes.get("path"),
                attributes.get("message"),
                error
        );

        return attributes;
    }
}