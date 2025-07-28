package com.example.spring_batch_pubsub;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchJobLauncher implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("simpleJob")
    private Job simpleJob;

    @Autowired
    @Qualifier("databaseJob")
    private Job databaseJob;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Starting Spring Batch Jobs...");
        
        // Run simple job
        System.out.println("📝 Running Simple Job...");
        JobParameters simpleJobParams = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(simpleJob, simpleJobParams);
        
        // Run database job
        System.out.println("💾 Running Database Job...");
        JobParameters databaseJobParams = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis() + 1)
                .toJobParameters();
        jobLauncher.run(databaseJob, databaseJobParams);
        
        System.out.println("✅ All Spring Batch Jobs completed!");
    }
} 