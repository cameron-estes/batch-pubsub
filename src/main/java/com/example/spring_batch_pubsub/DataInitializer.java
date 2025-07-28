package com.example.spring_batch_pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private MessageService messageService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("✅ Database initialized with sample data from schema.sql!");
        
        try {
            System.out.println("📊 Total messages in database: " + messageService.getMessageCount());
            
            messageService.displayAllMessages();
            
            messageService.addMessage("This message was added via Java code!");

            messageService.displayAllMessages();
        } catch (Exception e) {
            System.err.println("Error accessing database: " + e.getMessage());
            System.err.println("This might be because the database schema hasn't been initialized yet.");
        }
    }
} 