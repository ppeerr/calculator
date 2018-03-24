package com.per;

import com.per.calculation.CalculationService;
import com.per.io.InputService;
import com.per.io.impl.InputServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.lang.System.exit;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private CalculationService calculationService;

    @Autowired
    public Application(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        int res = calculationService.calc();

        System.out.println(res);
        exit(0);
    }

    @Bean
    public InputService inputService(@Value("${filePath}") String filePath) {
        return new InputServiceImpl(filePath);
    }

}
