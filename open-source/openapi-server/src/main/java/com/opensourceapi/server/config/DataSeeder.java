package com.opensourceapi.server.config;

import com.opensourceapi.server.entity.*;
import com.opensourceapi.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final RecipeRepository recipeRepository;
    private final QuoteRepository quoteRepository;
    private final StudentRepository studentRepository;
    private final TransactionRepository transactionRepository;
    private final ReceiptRepository receiptRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // already seeded
        }

        User demo = userRepository.save(User.builder()
                .username("demo")
                .email("demo@example.com")
                .password(passwordEncoder.encode("password123"))
                .role("USER")
                .build());

        postRepository.save(Post.builder()
                .title("Welcome to the Open Source Practice API")
                .content("This is a seeded post. Use /api/v1/auth/register or log in as demo/password123 " +
                        "to try authenticated endpoints like creating todos, posts, and comments.")
                .authorId(demo.getId())
                .build());

        productRepository.save(Product.builder()
                .name("Wireless Mouse")
                .description("A smooth, ergonomic wireless mouse. Sample data for practicing e-commerce UIs.")
                .price(new BigDecimal("19.99"))
                .category("Electronics")
                .stock(120)
                .imageUrl("https://picsum.photos/seed/mouse/400/300")
                .build());

        productRepository.save(Product.builder()
                .name("Mechanical Keyboard")
                .description("Clicky, tactile, and satisfying. Sample data for practicing e-commerce UIs.")
                .price(new BigDecimal("59.99"))
                .category("Electronics")
                .stock(45)
                .imageUrl("https://picsum.photos/seed/keyboard/400/300")
                .build());

        productRepository.save(Product.builder()
                .name("Coffee Mug")
                .description("Ceramic mug, 350ml. Sample data for practicing e-commerce UIs.")
                .price(new BigDecimal("9.50"))
                .category("Home")
                .stock(200)
                .imageUrl("https://picsum.photos/seed/mug/400/300")
                .build());

        // Seeding Recipes
        recipeRepository.save(Recipe.builder()
                .title("Spaghetti Bolognese")
                .description("A classic Italian pasta dish.")
                .ingredients(List.of("Spaghetti", "Ground Beef", "Tomato Sauce", "Onion", "Garlic"))
                .instructions("1. Boil water. 2. Cook pasta. 3. Brown beef. 4. Add sauce. 5. Combine.")
                .prepTimeMinutes(15)
                .cookTimeMinutes(30)
                .servings(4)
                .difficulty("Medium")
                .imageUrl("https://picsum.photos/seed/recipe1/400/300")
                .build());

        // Seeding Quotes
        quoteRepository.save(Quote.builder()
                .text("The only way to do great work is to love what you do.")
                .author("Steve Jobs")
                .category("Inspirational")
                .build());

        // Seeding Students
        studentRepository.save(Student.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice.j@example.com")
                .enrollmentDate(LocalDate.now().minusYears(1))
                .major("Computer Science")
                .gpa(3.8)
                .build());

        // Seeding Transactions
        transactionRepository.save(Transaction.builder()
                .userId(demo.getId().toString())
                .amount(new BigDecimal("150.00"))
                .type("CREDIT")
                .status("COMPLETED")
                .description("Salary deposit")
                .timestamp(LocalDateTime.now().minusDays(2))
                .build());

        // Seeding Receipts
        receiptRepository.save(Receipt.builder()
                .merchantName("Walmart")
                .totalAmount(new BigDecimal("85.50"))
                .taxAmount(new BigDecimal("6.50"))
                .date(LocalDate.now().minusDays(1))
                .category("Groceries")
                .imageUrl("https://picsum.photos/seed/receipt1/400/300")
                .notes("Weekly grocery shopping")
                .build());
    }
}
