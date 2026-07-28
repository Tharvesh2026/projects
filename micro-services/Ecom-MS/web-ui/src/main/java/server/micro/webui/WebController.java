package server.micro.webui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(Model model) {
        // Mocking data for demonstration of UI/UX
        List<Product> products = Arrays.asList(
            new Product(1L, "Minimalist Black Tee", "A simple, clean black t-shirt made from organic cotton. Fits any style.", 29.99),
            new Product(2L, "Grey Wool Scarf", "Keep warm with this soft, lightweight grey wool scarf. Essential for winter.", 45.00),
            new Product(3L, "White Ceramic Mug", "Start your morning right with this perfectly sized, heavy-bottom white mug.", 18.50)
        );
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @PostMapping("/order")
    public String processOrder() {
        // Mocking order processing
        return "redirect:/products";
    }

    // Inner class for mock data structure
    public static class Product {
        private Long id;
        private String name;
        private String description;
        private Double price;

        public Product(Long id, String name, String description, Double price) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public Double getPrice() { return price; }
    }
}
