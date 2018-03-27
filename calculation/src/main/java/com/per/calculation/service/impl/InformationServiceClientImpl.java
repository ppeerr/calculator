package com.per.calculation.service.impl;

import com.per.calculation.service.InformationServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InformationServiceClientImpl implements InformationServiceClient {

    private static final String INFORMATION_ENDPOINT = "http://localhost:9095/api/information";

    @Override
    public BiOperand getFirst() {
        RestTemplate restTemplate = new RestTemplate();
        BiOperand operand = restTemplate.getForObject(INFORMATION_ENDPOINT + "/first", BiOperand.class);
        return operand;
    }

    @Override
    public BiOperand getSecond() {
        RestTemplate restTemplate = new RestTemplate();
        BiOperand operand = restTemplate.getForObject(INFORMATION_ENDPOINT + "/second", BiOperand.class);
        return operand;
    }

    @Override
    public void putToSecond(Integer value) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(INFORMATION_ENDPOINT + "/second/{argument}", null, value);
    }
}
