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
        System.out.println("📊 Total messages in database: " + messageService.getMessageCount());
        
        // Display all messages
        messageService.displayAllMessages();
        
        // Add one more message to demonstrate the service
        messageService.addMessage("This message was added via Java code!");
        System.out.println("\n➕ Added one more message via Java code");
        messageService.displayAllMessages();
    }
} 