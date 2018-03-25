package com.per.calculation;

import java.util.concurrent.CompletableFuture;

public interface CalculationService {

    CompletableFuture<Integer> calc() throws Exception;

}
