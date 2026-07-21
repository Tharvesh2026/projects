package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projects.icore.CoursePortal.services.CourseService;

@Controller
@RequiredArgsConstructor
public class WebCourseViewController {

    private final CourseService courseService;

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("courses", courseService.getAllCourses());
        addAuthDetails(model, authentication);
        return "courses";
    }

    @GetMapping("/courses")
    public String courses(Model model, Authentication authentication) {
        model.addAttribute("courses", courseService.getAllCourses());
        addAuthDetails(model, authentication);
        return "courses";
    }

    private void addAuthDetails(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            model.addAttribute("userName", oAuth2User.getAttribute("name"));
            model.addAttribute("userEmail", oAuth2User.getAttribute("email"));
            model.addAttribute("userRole", oAuth2User.getAttribute("role"));
        }
    }
}
