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
            "Talk is cheap. Show me the code.",
            "Keep it simple.",
            "Less code, fewer bugs.",
            "Every bug is a lesson.",
            "Ship early. Improve often.",
            "Done is better than perfect.",
            "Measure twice, code once.",
            "Think before you commit.",
            "Readability counts.",
            "Automation saves time.",
            "Consistency beats cleverness.",
            "Refactoring is a daily habit.",
            "Small changes are easier to review.",
            "Write tests with confidence.",
            "Debugging is detective work.",
            "The compiler is your friend.",
            "Naming things is hard.",
            "Optimize only when necessary.",
            "Documentation is part of the product.",
            "Keep functions small.",
            "Code should tell a story.",
            "Quality is everyone's responsibility.",
            "Feedback drives improvement.",
            "Review code with kindness.",
            "Every feature has a cost.",
            "Build for maintainability.",
            "Performance starts with good design.",
            "Security is not optional.",
            "Version control remembers everything.",
            "Never stop learning.",
            "Curiosity fuels innovation.",
            "Practice beats theory.",
            "Great software evolves.",
            "Fix the root cause.",
            "Test what matters.",
            "Make failure recoverable.",
            "Start simple, then iterate.",
            "Elegant code is intentional.",
            "Predictability beats surprise.",
            "Focus on the user.",
            "A clean API is a gift.",
            "Fast feedback accelerates progress.",
            "Code with empathy.",
            "Every commit tells a story.",
            "Reduce complexity whenever possible.",
            "Software is never truly finished.",
            "One step at a time.",
            "Progress over perfection.",
            "Today's shortcut is tomorrow's technical debt.",
            "Simple designs scale better.",
            "Questions are cheaper than assumptions.",
            "Design for change.",
            "Good architecture enables growth.",
            "Solve the right problem.",
            "Keep dependencies under control.",
            "Reliable systems earn trust.",
            "Logs are your future self's best friend.",
            "Monitoring prevents surprises.",
            "Failures are opportunities to improve.",
            "Resilience is a feature.",
            "Think in systems.",
            "Code reviews improve everyone.",
            "The best optimization removes unnecessary work.",
            "Avoid premature optimization.",
            "Build small, release often.",
            "Keep your build green.",
            "Tests document behavior.",
            "Edge cases deserve attention.",
            "Every line of code has a maintenance cost.",
            "Clarity beats clever tricks.",
            "Readable code scales with teams.",
            "Strong foundations support great products.",
            "Good software solves real problems.",
            "Focus on value, not volume.",
            "Incremental progress compounds.",
            "Every release teaches something.",
            "Listen to your users.",
            "Simple interfaces create better experiences.",
            "Code confidently, test thoroughly.",
            "Great teams share knowledge.",
            "Collaboration beats competition.",
            "Reliable code builds confidence.",
            "Leave the codebase better than you found it.",
            "Design for humans first.",
            "Learning never goes out of style.",
            "Good habits produce great software.",
            "A passing test is better than a hopeful guess.",
            "Maintainability is a feature.",
            "Small wins create momentum.",
            "Technology changes, principles remain.",
            "The simplest solution is often the best.",
            "Success comes from continuous improvement.",
            "Every expert was once a beginner.",
            "Think clearly, code carefully.",
            "Write code your future self will appreciate."
    );

    private static final List<Map<String, String>> JOKES = List.of(
            Map.of("setup", "Why do programmers prefer dark mode?", "punchline", "Because light attracts bugs."),
            Map.of("setup", "Why do Java developers wear glasses?", "punchline", "Because they don't C#."),
            Map.of("setup", "How many programmers does it take to change a light bulb?", "punchline", "None, that's a hardware problem."),
            Map.of("setup", "Why did the developer go broke?", "punchline", "Because they used up all their cache."),
            Map.of("setup", "Why did the programmer quit his job?", "punchline", "Because he didn't get arrays."),
            Map.of("setup", "Why was the JavaScript developer sad?", "punchline", "Because they didn't Node how to Express themselves."),
            Map.of("setup", "Why do Python programmers need glasses?", "punchline", "Because they can't C."),
            Map.of("setup", "Why was the computer cold?", "punchline", "It left its Windows open."),
            Map.of("setup", "Why did the function return early?", "punchline", "It had nothing left to execute."),
            Map.of("setup", "Why don't programmers like nature?", "punchline", "It has too many bugs."),
            Map.of("setup", "Why did the database administrator break up?", "punchline", "There was no connection."),
            Map.of("setup", "Why did the developer stay calm?", "punchline", "They knew how to handle exceptions."),
            Map.of("setup", "What's a programmer's favorite hangout place?", "punchline", "The Foo Bar."),
            Map.of("setup", "Why was the Git repository so confident?", "punchline", "It had a solid commit history."),
            Map.of("setup", "Why did the programmer get kicked out of school?", "punchline", "Too many class conflicts."),
            Map.of("setup", "Why was the code always relaxed?", "punchline", "It never got under pressure."),
            Map.of("setup", "Why did the API go to therapy?", "punchline", "It had too many unresolved requests."),
            Map.of("setup", "Why did the server go on vacation?", "punchline", "It needed some downtime."),
            Map.of("setup", "Why did the programmer bring a ladder?", "punchline", "To reach the next level of abstraction."),
            Map.of("setup", "Why do developers love coffee?", "punchline", "Because Java keeps them running."),
            Map.of("setup", "Why did the object refuse to change?", "punchline", "It was immutable."),
            Map.of("setup", "Why did the thread stop talking?", "punchline", "It was blocked."),
            Map.of("setup", "Why was the algorithm so confident?", "punchline", "It always had a solution."),
            Map.of("setup", "Why did the programmer cross the road?", "punchline", "The chicken hadn't been deployed yet."),
            Map.of("setup", "Why did the bug apply for a job?", "punchline", "It wanted to be a feature."),
            Map.of("setup", "Why did the compiler get promoted?", "punchline", "It always produced results."),
            Map.of("setup", "Why was the keyboard always happy?", "punchline", "It had plenty of key opportunities."),
            Map.of("setup", "Why don't programmers trust stairs?", "punchline", "They're always up to something."),
            Map.of("setup", "Why was the developer always punctual?", "punchline", "They synchronized their clocks."),
            Map.of("setup", "Why did the null value fail the interview?", "punchline", "It had nothing to offer."),
            Map.of("setup", "Why did the binary file feel lonely?", "punchline", "It couldn't find its other bit."),
            Map.of("setup", "Why did the frontend developer smile?", "punchline", "Everything finally aligned."),
            Map.of("setup", "Why did the backend developer panic?", "punchline", "The endpoint disappeared."),
            Map.of("setup", "Why was the CSS file so dramatic?", "punchline", "It had too much style."),
            Map.of("setup", "Why did the programmer love recursion?", "punchline", "To understand recursion, they first had to understand recursion."),
            Map.of("setup", "Why did the cache feel important?", "punchline", "Everyone kept checking on it."),
            Map.of("setup", "Why did the microservice feel lonely?", "punchline", "It was always deployed separately."),
            Map.of("setup", "Why was the Docker container calm?", "punchline", "It was well contained."),
            Map.of("setup", "Why did the cloud application succeed?", "punchline", "It was always looking up."),
            Map.of("setup", "Why did the developer carry a pencil?", "punchline", "To sketch better architecture."),
            Map.of("setup", "Why did the programmer smile at the terminal?", "punchline", "It finally accepted the command."),
            Map.of("setup", "Why was the test suite happy?", "punchline", "Everything passed."),
            Map.of("setup", "Why did the code review take so long?", "punchline", "Everyone had comments."),
            Map.of("setup", "Why was the repository so clean?", "punchline", "It had no dirty commits."),
            Map.of("setup", "Why did the developer love weekends?", "punchline", "No production deployments."),
            Map.of("setup", "Why did the application crash?", "punchline", "It couldn't handle the pressure."),
            Map.of("setup", "Why was the bug so hard to catch?", "punchline", "It kept reproducing."),
            Map.of("setup", "Why did the engineer carry a rubber duck?", "punchline", "For debugging conversations."),
            Map.of("setup", "Why did the variable become famous?", "punchline", "It had global scope."),
            Map.of("setup", "Why was the codebase so friendly?", "punchline", "It welcomed every contribution.")
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
