package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projects.icore.CoursePortal.dto.CourseRequest;
import projects.icore.CoursePortal.dto.EnrollmentRequest;
import projects.icore.CoursePortal.services.CourseService;
import projects.icore.CoursePortal.services.EnrollmentService;
import projects.icore.CoursePortal.services.StudentService;

@Controller
@RequiredArgsConstructor
public class WebCourseViewController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        return courses(model, authentication);
    }

    @GetMapping("/courses")
    public String courses(Model model, Authentication authentication) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("currentUri", "/courses");
        addAuthDetails(model, authentication);
        return "courses";
    }

    @GetMapping("/my-courses")
    public String myCourses(@RequestParam(required = false, defaultValue = "101") Integer rollNo, Model model, Authentication authentication) {
        model.addAttribute("enrollments", enrollmentService.getCoursesByStudent(rollNo));
        model.addAttribute("studentRollNo", rollNo);
        model.addAttribute("currentUri", "/my-courses");
        addAuthDetails(model, authentication);
        return "my-courses";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("currentUri", "/admin/dashboard");
        addAuthDetails(model, authentication);
        return "admin-dashboard";
    }

    @GetMapping("/sysadmin/dashboard")
    public String sysAdminDashboard(Model model, Authentication authentication) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        model.addAttribute("students", studentService.getAll());
        model.addAttribute("currentUri", "/sysadmin/dashboard");
        addAuthDetails(model, authentication);
        return "sysadmin-dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        model.addAttribute("currentUri", "/profile");
        addAuthDetails(model, authentication);
        return "profile";
    }

    @GetMapping("/settings")
    public String settings(Model model, Authentication authentication) {
        model.addAttribute("currentUri", "/settings");
        addAuthDetails(model, authentication);
        return "settings";
    }

    @PostMapping("/admin/verify")
    public String verifyEnrollmentForm(@RequestParam Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.verifyEnrollment(id, status);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment #" + id + " updated to " + status);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to verify enrollment: " + ex.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/sysadmin/create-course")
    public String createCourseForm(@RequestParam String code, @RequestParam String name, @RequestParam(required = false, defaultValue = "Course Module") String description, @RequestParam(required = false, defaultValue = "4") int credits, RedirectAttributes redirectAttributes) {
        try {
            courseService.createCourse(new CourseRequest(code, name, description, credits));
            redirectAttributes.addFlashAttribute("successMessage", "Course " + code + " created successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create course: " + ex.getMessage());
        }
        return "redirect:/sysadmin/dashboard";
    }

    @PostMapping("/web/enroll")
    public String enrollForm(@RequestParam Integer rollNo, @RequestParam String courseCode, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.registerCourse(new EnrollmentRequest(rollNo, courseCode));
            redirectAttributes.addFlashAttribute("successMessage", "Successfully enrolled roll #" + rollNo + " in " + courseCode + "!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Enrollment error: " + ex.getMessage());
        }
        return "redirect:/my-courses?rollNo=" + rollNo;
    }

    private void addAuthDetails(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            model.addAttribute("userSub", oAuth2User.getAttribute("sub"));
            model.addAttribute("userName", oAuth2User.getAttribute("name"));
            model.addAttribute("userEmail", oAuth2User.getAttribute("email"));
            model.addAttribute("userRole", oAuth2User.getAttribute("role"));
            model.addAttribute("userPermissions", oAuth2User.getAttribute("permissions"));
            model.addAttribute("userAttributes", oAuth2User.getAttributes());
        }
    }
}
