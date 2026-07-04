package learning.springboot.JpaMapping.controller;

import learning.springboot.JpaMapping.model.ApiResponse;
import learning.springboot.JpaMapping.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/admin")
@RequiredArgsConstructor
@CrossOrigin("*")

public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/flush")
    public ResponseEntity<ApiResponse<String>> flushDatabase(
            @RequestParam String password,
            @RequestParam String entity
    ) {
        String result = adminService.flushDatabase(password, entity);

        return ResponseEntity.ok(
                new ApiResponse<>("Database flush completed", result)
        );
    }
}