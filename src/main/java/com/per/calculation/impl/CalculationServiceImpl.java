package com.per.calculation.impl;

import com.per.calculation.CalculationService;
import com.per.calculation.functions.Addition;
import com.per.calculation.functions.Subtraction;
import com.per.io.InputService;
import com.per.io.impl.BiOperand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalculationServiceImpl implements CalculationService {

//    @Autowired
//    private Addition additionFunction;
//
//    @Autowired
//    private Multiplication multiplicationFunction;
//
//    @Autowired
//    private Subtraction subtractionFunction;
//
//    @Autowired
//    private InputService inputService;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public int calc() {
        InputService inputService = (InputService) applicationContext.getBean("inputService");
        List<BiOperand> content = inputService.getContent();

        Subtraction subtractionFunction = (Subtraction) applicationContext.getBean("subtraction");
        Addition additionFunction = (Addition) applicationContext.getBean("addition");

        List<Integer> results = new ArrayList<>();
        for (BiOperand biOperand : content) {
            results.add((Integer) subtractionFunction.evaluate(biOperand.getA(), biOperand.getB()));
        }

        Integer result2 = 0;
        for (int i = 0; i < content.size(); i++) {
            result2 = (Integer) additionFunction.evaluate(result2, results.get(i));
        }

        return result2;
    }

}
