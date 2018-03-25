package com.per.io;

import com.per.io.impl.BiOperand;

import java.util.concurrent.CompletableFuture;

public interface InputService {

    void readContent();

    CompletableFuture<BiOperand> getFirst() throws InterruptedException;

    CompletableFuture<BiOperand> getSecond() throws InterruptedException;

    void putToSecond(Integer argument) throws InterruptedException;

    CompletableFuture<Integer> getResult() throws InterruptedException;

}
