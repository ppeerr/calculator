package com.per.information.service;

import com.per.information.service.impl.BiOperand;

import java.util.concurrent.CompletableFuture;

public interface InputService {

    void readContent();

    CompletableFuture<BiOperand> getFirst() throws InterruptedException;

    CompletableFuture<BiOperand> getSecond() throws InterruptedException;

    void putToSecond(Integer argument) throws InterruptedException;

    Integer getResult() throws InterruptedException;

}
