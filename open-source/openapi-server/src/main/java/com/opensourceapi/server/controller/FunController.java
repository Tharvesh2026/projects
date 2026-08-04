package com.opensourceapi.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/fun")
@Tag(name = "Fun", description = "Zero-setup public endpoints (no auth, no body) — good first calls for absolute beginners")
public class FunController {

    private static final List<String> QUOTES = List.of(
            "Simplicity is the soul of efficiency.",
            "First, solve the problem. Then, write the code.",
            "Code is like humor. When you have to explain it, it's bad.",
            "Make it work, make it right, make it fast.",
            "The best error message is the one that never shows up.",
            "Programs must be written for people to read.",
            "Any fool can write code that a computer can understand.",
            "Talk is cheap. Show me the code."
    );

    private static final List<Map<String, String>> JOKES = List.of(
            Map.of("setup", "Why do programmers prefer dark mode?", "punchline", "Because light attracts bugs."),
            Map.of("setup", "Why do Java developers wear glasses?", "punchline", "Because they don't C#."),
            Map.of("setup", "How many programmers does it take to change a light bulb?", "punchline", "None, that's a hardware problem."),
            Map.of("setup", "Why did the developer go broke?", "punchline", "Because they used up all their cache.")
    );

    @GetMapping("/quotes/random")
    @Operation(summary = "Get a random programming quote")
    public Map<String, String> randomQuote() {
        String quote = QUOTES.get(ThreadLocalRandom.current().nextInt(QUOTES.size()));
        return Map.of("quote", quote);
    }

    @GetMapping("/quotes")
    @Operation(summary = "List all quotes")
    public List<String> allQuotes() {
        return QUOTES;
    }

    @GetMapping("/jokes/random")
    @Operation(summary = "Get a random programming joke")
    public Map<String, String> randomJoke() {
        return JOKES.get(ThreadLocalRandom.current().nextInt(JOKES.size()));
    }

    @GetMapping("/jokes")
    @Operation(summary = "List all jokes")
    public List<Map<String, String>> allJokes() {
        return JOKES;
    }
}
