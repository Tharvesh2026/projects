package dev.tharvbytes.tasks.controller;

import dev.tharvbytes.tasks.model.Task;
import dev.tharvbytes.tasks.service.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String dashboard(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        if (oidcUser == null) {
            return "redirect:/oauth2/authorization/icore";
        }

        String email = oidcUser.getEmail();
        String role = oidcUser.getClaimAsString("role");
        List<String> permissions = oidcUser.getClaimAsStringList("permissions");

        model.addAttribute("email", email);
        model.addAttribute("role", role != null ? role : "N/A");
        model.addAttribute("permissions", permissions != null ? permissions : List.of());
        model.addAttribute("tasks", taskService.getTasksByOwner(email));

        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam String title) {
        if (title != null && !title.trim().isEmpty() && oidcUser != null) {
            taskService.addTask(title.trim(), false, oidcUser.getEmail());
        }
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/complete")
    public String completeTask(@AuthenticationPrincipal OidcUser oidcUser, @PathVariable Long id) {
        if (oidcUser != null) {
            taskService.completeTask(id, oidcUser.getEmail());
        }
        return "redirect:/";
    }

    @PostMapping("/tasks/{id}/delete")
    @PreAuthorize("hasAuthority('TASK_DELETE')")
    public String deleteTask(@AuthenticationPrincipal OidcUser oidcUser, @PathVariable Long id) {
        if (oidcUser != null) {
            taskService.deleteTask(id, oidcUser.getEmail());
        }
        return "redirect:/";
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String handleAccessDeniedException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Access Denied: You do not have the required 'TASK_DELETE' permission to delete tasks.");
        return "redirect:/";
    }
}
