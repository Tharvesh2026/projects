package server.micro.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    // Mock in-memory database for users
    private final Map<String, User> users = new ConcurrentHashMap<>();
    
    // Status tracking for payment form success/fail
    private String paymentStatus = null;
    private String paymentMessage = null;

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(Model model) {
        try {
            // Fetch real products from product service
            String url = "http://localhost:8081/api/product?page=0&size=20";
            ProductPageResponse response = restTemplate.getForObject(url, ProductPageResponse.class);
            if (response != null && response.getContent() != null) {
                model.addAttribute("products", response.getContent());
            } else {
                model.addAttribute("products", new ArrayList<>());
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch products: " + e.getMessage());
            model.addAttribute("products", new ArrayList<>());
        }
        return "products";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("status", paymentStatus);
        model.addAttribute("message", paymentMessage);
        // clear status after showing
        paymentStatus = null;
        paymentMessage = null;
        return "checkout";
    }

    @PostMapping("/order")
    public String processOrder(
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String cardNumber,
            @RequestParam(required = false) String expiry,
            @RequestParam(required = false) String cvc,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String upiId) {

        boolean isSuccess = false;
        
        if ("CARD".equals(paymentMethod)) {
            if ("1234-1234-1234-1234".equals(cardNumber) && "05/2027".equals(expiry) && "113".equals(cvc) && "Tharvesh Muhaideen A".equals(name)) {
                isSuccess = true;
            }
        } else if ("UPI".equals(paymentMethod)) {
            if ("9780564123@arth".equals(upiId)) {
                isSuccess = true;
            }
        } else if ("COD".equals(paymentMethod)) {
            isSuccess = true; // Always success for COD in mock
        }

        if (isSuccess) {
            paymentStatus = "success";
            paymentMessage = "Payment successful! Your order has been placed.";
            return "redirect:/checkout";
        } else {
            paymentStatus = "error";
            paymentMessage = "Payment failed! Invalid details entered.";
            return "redirect:/checkout";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        
        User user = new User(name, email, password, "Pending", otp);
        users.put(email, user);

        // Send OTP email
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("otp", otp);
            String htmlBody = templateEngine.process("email/otp-template", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Your OTP for Minimal Store");
            helper.setText(htmlBody, true);

            // Print to console to simulate sending without real SMTP setup
            System.out.println("========== MOCK EMAIL SENT ==========");
            System.out.println("To: " + email);
            System.out.println("OTP: " + otp);
            System.out.println("=====================================");

            // Actually try sending (requires SMTP to be configured or mock like Mailtrap)
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }

        return "redirect:/otp-verify?email=" + email;
    }

    @GetMapping("/otp-verify")
    public String otpVerifyPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "otp-verify";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        User user = users.get(email);
        if (user != null && user.getOtp().equals(otp)) {
            user.setStatus("Active");
            // clear OTP
            user.setOtp(null);
            model.addAttribute("message", "Account successfully activated! You can now login.");
            return "redirect:/products";
        }
        
        model.addAttribute("error", "Invalid OTP or email.");
        model.addAttribute("email", email);
        return "otp-verify";
    }

    // Inner classes for mock data structure
    public static class Product {
        private Long id;
        private String name;
        private String description;
        private Double price;

        public Product() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }

    public static class ProductPageResponse {
        private List<Product> content;
        public List<Product> getContent() { return content; }
        public void setContent(List<Product> content) { this.content = content; }
    }

    public static class User {
        private String name;
        private String email;
        private String password;
        private String status;
        private String otp;

        public User(String name, String email, String password, String status, String otp) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.status = status;
            this.otp = otp;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }
}
