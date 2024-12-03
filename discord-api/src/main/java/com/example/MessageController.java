package com.example;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

    // Store messages in memory using a Map
    private final Map<String, List<String>> messageStore = new ConcurrentHashMap<>();

    @PostMapping
    public String saveMessage(@RequestParam String username, @RequestParam String content) {
        System.out.println("Username: " + username + " | Message: " + content);
        messageStore.computeIfAbsent(username, k -> new ArrayList<>()).add(content);
        System.out.println(messageStore);
        return "Message saved!";
    }

    @GetMapping("/{word}")
    public Map<String, Long> getWordUsage(@PathVariable String word) {
        Map<String, Long> wordCounts = new HashMap<>();
        messageStore.forEach((username, messages) -> {
            long count = messages.stream()
                    // Split the message into words and check for exact matches
                    .flatMap(message -> Arrays.stream(message.split("\\s+"))) // Split by spaces
                    .filter(w -> w.equalsIgnoreCase(word)) // Match whole words
                    .count();
            if (count > 0) {
                wordCounts.put(username, count);
            }
        });
        return wordCounts;
    }

}
