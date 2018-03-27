package com.per.calculation.service;

import com.per.calculation.service.impl.BiOperand;

public interface InformationServiceClient {

    BiOperand getFirst();

    BiOperand getSecond();

    void putToSecond(Integer value);

}
