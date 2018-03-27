package com.per.calculation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import static java.lang.System.exit;

@SpringBootApplication
@EnableAsync
@Slf4j
public class CoordinationApp implements CommandLineRunner {

    private static final String INFORMATION_ENDPOINT = "http://localhost:9095/api/information";
    private static final String CALCULATION_ENDPOINT = "http://localhost:9093/api/calculation";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CoordinationApp.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForLocation(INFORMATION_ENDPOINT + "/read", null);
        log.info("Reading was called");

        restTemplate.postForLocation(
                CALCULATION_ENDPOINT + "/calc/{calculationType}/{functionName}",
                null,
                1,
                "subtraction"
        );
        restTemplate.postForLocation(
                CALCULATION_ENDPOINT + "/calc/{calculationType}/{functionName}",
                null,
                3,
                "subtraction"
        );
        restTemplate.postForLocation(
                CALCULATION_ENDPOINT + "/calc/{calculationType}/{functionName}",
                null,
                5,
                "subtraction"
        );

        restTemplate.postForLocation(
                CALCULATION_ENDPOINT + "/calc/{calculationType}/{functionName}",
                null,
                2,
                "addition"
        );
        restTemplate.postForLocation(
                CALCULATION_ENDPOINT + "/calc/{calculationType}/{functionName}",
                null,
                4,
                "addition"
        );
        restTemplate.postForLocation(
                CALCULATION_ENDPOINT + "/calc/{calculationType}/{functionName}",
                null,
                6,
                "addition"
        );

        Integer result = null;
        while (result == null) {
            result = restTemplate.getForObject(INFORMATION_ENDPOINT + "/result", Integer.class);
            Thread.sleep(1000);
        }

        log.info("Result = {}", result);

        exit(0);
    }



}
