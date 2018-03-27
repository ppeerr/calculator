package com.per.calculation.service.impl;

import com.per.calculation.service.CalculationService;
import com.per.calculation.service.InformationServiceClient;
import com.per.functions.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CalculationServiceImpl implements CalculationService {

    private static final Logger log = LoggerFactory.getLogger(CalculationServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private InformationServiceClient informationServiceClient;


    @Override
    @Async
    public void calc(int type, String functionName) {
        Function function = (Function) applicationContext.getBean(functionName);

        while (true) {
            BiOperand operands;
            if ((type & 1) == 1) {
                operands = informationServiceClient.getFirst();
            } else {
                operands = informationServiceClient.getSecond();
            }
            if (operands == null) {
                break;
            }

            Integer answer = function.evaluate(operands.getA(), operands.getB());
            informationServiceClient.putToSecond(answer);

//            log.info("Left = {} Right = {} Result = {}; type = {}", operands.getA(), operands.getB(), answer, type);
        }

    }

}
