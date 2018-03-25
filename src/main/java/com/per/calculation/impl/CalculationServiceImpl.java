package com.per.calculation.impl;

import com.per.calculation.CalculationService;
import com.per.calculation.functions.Function;
import com.per.io.InputService;
import com.per.io.impl.BiOperand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
public class CalculationServiceImpl implements CalculationService {

    private volatile int type;
    private String functionBeanName;

    @Autowired
    private ApplicationContext applicationContext;

    public CalculationServiceImpl(String functionBeanName, int type) {
        this.functionBeanName = functionBeanName;
        this.type = type;
    }

    @Override
    @Async
    public CompletableFuture<Integer> calc() throws Exception {
        InputService inputService = (InputService) applicationContext.getBean("inputService");
        Function function = (Function) applicationContext.getBean(functionBeanName);

        inputService.readContent();

        while (true) {
            BiOperand operands;
            if ((type & 1) == 1) {
                operands = inputService.getFirst().get();
            } else {
                operands = inputService.getSecond().get();
            }
            if (operands == null) {
                break;
            }

            Integer answer = function.evaluate(operands.getA(), operands.getB());
            inputService.putToSecond(answer);

//            log.info("Left = {} Right = {} Result = {}; type = {}", operands.getA(), operands.getB(), answer, type);
        }

        return completedFuture(1);
    }

}
