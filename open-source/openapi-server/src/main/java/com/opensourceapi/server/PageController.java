package com.opensourceapi.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/docs")
    public String docs() {
        return "redirect:/swagger-ui/index.html";
    }
    @GetMapping("/db-login")
    public String dbLogin() {
        return "db-login";
    }
}
