package com.example.iaasconsole.web.mvc;

import com.example.iaasconsole.repository.DomainRepository;
import com.example.iaasconsole.service.DomainSyncService;
import com.example.iaasconsole.service.VirtualMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DomainRepository domainRepository;
    private final DomainSyncService domainSyncService;
    private final VirtualMachineService virtualMachineService;
    private final com.example.iaasconsole.service.CloudStackClientService cloudStackClientService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal Object principal, Model model) {
        populateUser(principal, model);
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("domains", domainRepository.findAll());
        return "dashboard";
    }

    @PostMapping("/dashboard/sync")
    public String syncDomains() {
        domainSyncService.syncDomains();
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/domains/create")
    public String createDomain(String companyName) {
        if (companyName != null && !companyName.trim().isEmpty()) {
            // Convert to lowercase as per requirements (e.g. "Viens Solution" -> "viens solution")
            String normalizedDomainName = companyName.trim().toLowerCase();
            try {
                cloudStackClientService.createDomain(normalizedDomainName);
                // Sync domains immediately to fetch the newly created domain
                domainSyncService.syncDomains();
            } catch (Exception e) {
                // In a real app we'd add flash attributes for error messages
                e.printStackTrace();
            }
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/instances")
    public String instances(@AuthenticationPrincipal Object principal, Model model) {
        populateUser(principal, model);
        model.addAttribute("activePage", "instances");
        return "instances";
    }

    @PostMapping("/instances/deploy")
    public String deployInstance(String name, String templateId, String serviceOfferingId) {
        virtualMachineService.deployVirtualMachine(name, templateId, serviceOfferingId);
        return "redirect:/instances";
    }

    @GetMapping("/networks")
    public String networks(@AuthenticationPrincipal Object principal, Model model) {
        populateUser(principal, model);
        model.addAttribute("activePage", "networks");
        return "networks";
    }

    private void populateUser(Object principal, Model model) {
        if (principal instanceof OAuth2User oauth2User) {
            String login = oauth2User.getAttribute("login") != null ? oauth2User.getAttribute("login") : oauth2User.getAttribute("sub");
            model.addAttribute("username", login);
            model.addAttribute("avatarUrl", oauth2User.getAttribute("avatar_url"));
        } else if (principal instanceof UserDetails userDetails) {
            model.addAttribute("username", userDetails.getUsername());
        } else {
            model.addAttribute("username", "Guest");
        }
    }
}
