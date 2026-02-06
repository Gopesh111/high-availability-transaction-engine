package com.gopesh.transactionengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // Enables concurrent processing
public class TransactionEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionEngineApplication.class, args);
    }
}
