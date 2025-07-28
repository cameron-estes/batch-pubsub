package com.example.spring_batch_pubsub;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SimpleBatchJob {

    @Bean
    public Job simpleJob(JobRepository jobRepository, Step simpleStep) {
        return new JobBuilder("simpleJob", jobRepository)
                .start(simpleStep)
                .build();
    }

    @Bean
    public Step simpleStep(JobRepository jobRepository, 
                          PlatformTransactionManager transactionManager,
                          ItemReader<String> reader,
                          ItemProcessor<String, String> processor,
                          ItemWriter<String> writer) {
        return new StepBuilder("simpleStep", jobRepository)
                .<String, String>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        List<String> items = Arrays.asList(
            "Hello", "World", "Spring", "Batch", "Learning", "Journey"
        );
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> {
            System.out.println("Processing: " + item);
            return item.toUpperCase();
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            System.out.println("Writing chunk:");
            for (String item : items) {
                System.out.println("  -> " + item);
            }
        };
    }
} 