package project.module.SpringSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SecuredApiController {

    @GetMapping("/viewer/data")
    public Map<String, Object> getViewerData() {
        return Map.of(
                "status", "success",
                "roleRequired", "ROLE_VIEWER",
                "message", "Welcome! You have VIEWER (View-Only) access. You can safely inspect logs, charts, and public dashboard details."
        );
    }

    @GetMapping("/admin/data")
    public Map<String, Object> getAdminData() {
        return Map.of(
                "status", "success",
                "roleRequired", "ROLE_ADMIN",
                "message", "Welcome! You have ADMIN (Manager based) access. You have rights to perform moderate configuration and oversee system metrics."
        );
    }

    @GetMapping("/domain-admin/data")
    public Map<String, Object> getDomainAdminData() {
        return Map.of(
                "status", "success",
                "roleRequired", "ROLE_DOMAIN_ADMIN",
                "message", "Welcome! You have DOMAIN_ADMIN (2nd Level Admin) access. You can manage domain configurations, allocate resources, and handle group security policies."
        );
    }

    @GetMapping("/sys-admin/data")
    public Map<String, Object> getSysAdminData() {
        return Map.of(
                "status", "success",
                "roleRequired", "ROLE_SYS_ADMIN",
                "message", "Access Granted! Welcome, Super Admin (SYS_ADMIN). You hold root level credentials and have complete control over all aspects of the application."
        );
    }
}
