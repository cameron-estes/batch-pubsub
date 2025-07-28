package com.example.spring_batch_pubsub;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    
    private final JdbcTemplate jdbcTemplate;

    public MessageService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getAllMessages() {
        return jdbcTemplate.queryForList("SELECT * FROM messages");
    }

    public Map<String, Object> getMessageById(Long id) {
        List<Map<String, Object>> results = jdbcTemplate.queryForList(
            "SELECT * FROM messages WHERE id = ?", id);
        return results.isEmpty() ? null : results.get(0);
    }
    
    public void addMessage(String message) {
        jdbcTemplate.update("INSERT INTO messages (message) VALUES (?)", message);
    }
    
    public int getMessageCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM messages", Integer.class);
    }
    
    public void displayAllMessages() {
        System.out.println("\n💬 All messages in database:");
        List<Map<String, Object>> messages = getAllMessages();
        for (Map<String, Object> message : messages) {
            System.out.println("  - ID: " + message.get("ID") + " | Message: " + message.get("MESSAGE"));
        }
    }
}