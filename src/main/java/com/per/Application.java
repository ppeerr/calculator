package com.per;

import com.per.calculation.CalculationService;
import com.per.calculation.impl.CalculationServiceImpl;
import com.per.io.InputService;
import com.per.io.impl.InputServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.exit;

@SpringBootApplication
@EnableAsync
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<CompletableFuture<Integer>> responses = new ArrayList<>();

        responses.add(((CalculationService) applicationContext.getBean("calculationSubtractionService1")).calc());
        responses.add(((CalculationService) applicationContext.getBean("calculationSubtractionService2")).calc());
        responses.add(((CalculationService) applicationContext.getBean("calculationAdditionService1")).calc());
        responses.add(((CalculationService) applicationContext.getBean("calculationAdditionService2")).calc());

        for (CompletableFuture<Integer> response : responses) {
            response.get();
        }

        Integer result = ((InputService) applicationContext.getBean("inputService")).getResult().get();
        log.info("Result = {}", result);

        exit(0);
    }

    @Bean
    public InputService inputService(@Value("${filePath}") String filePath) throws FileNotFoundException {
        return new InputServiceImpl(filePath);
    }

    @Bean
    public CalculationService calculationSubtractionService1() {
        return new CalculationServiceImpl("subtraction", 1);
    }

    @Bean
    public CalculationService calculationSubtractionService2() {
        return new CalculationServiceImpl("subtraction", 3);
    }

    @Bean
    public CalculationService calculationAdditionService1() {
        return new CalculationServiceImpl("addition", 2);
    }

    @Bean
    public CalculationService calculationAdditionService2() {
        return new CalculationServiceImpl("addition", 4);
    }



}
