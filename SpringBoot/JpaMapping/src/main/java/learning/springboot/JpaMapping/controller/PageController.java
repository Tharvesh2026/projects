package learning.springboot.JpaMapping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/list")
    public String list() {
        return "list";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/filter")
    public String filter() {
        return "filter";
    }

}
