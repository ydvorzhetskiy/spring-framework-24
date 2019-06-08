package ru.otus.spring.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.Arrays;

@SpringBootApplication
@IntegrationComponentScan
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

        ctx.getBean(UpperMessaagingGateway.class).upperStrings(
                Arrays.asList("foo", "bar")
        ).forEach(System.out::println);

        ctx.close();
    }

    @Bean
    public IntegrationFlow upper() {
        return f -> f
                .log()
                .split()
                .log()
                .<String, String>transform(String::toUpperCase)
                .log()
                .aggregate();
    }
}
