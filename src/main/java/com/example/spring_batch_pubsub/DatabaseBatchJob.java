package com.example.spring_batch_pubsub;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatabaseBatchJob {

    @Bean
    public Job databaseJob(JobRepository jobRepository, Step databaseStep) {
        return new JobBuilder("databaseJob", jobRepository)
                .start(databaseStep)
                .build();
    }

    @Bean
    public Step databaseStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           ItemReader<Message> reader,
                           ItemProcessor<Message, Message> processor,
                           ItemWriter<Message> writer) {
        return new StepBuilder("databaseStep", jobRepository)
                .<Message, Message>chunk(2, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Message> reader(DataSource dataSource) {
        JdbcCursorItemReader<Message> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id, message FROM messages");
        reader.setRowMapper(new DataClassRowMapper<>(Message.class));
        return reader;
    }

    @Bean
    public ItemProcessor<Message, Message> processor() {
        return message -> {
            System.out.println("Processing message: " + message.message());
            // Transform the message (add timestamp)
            String transformedMessage = message.message() + " [Processed at: " + System.currentTimeMillis() + "]";
            return new Message(message.id(), transformedMessage);
        };
    }

    @Bean
    public JdbcBatchItemWriter<Message> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Message> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("UPDATE messages SET message = ? WHERE id = ?");
        writer.setItemPreparedStatementSetter((item, ps) -> {
            ps.setString(1, item.message());
            ps.setLong(2, item.id());
        });
        return writer;
    }

    // Record class to represent message data
    public record Message(Long id, String message) {}
} 