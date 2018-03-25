package com.per.io.impl;

import com.per.io.InputService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
public class InputServiceImpl implements InputService {

    private BufferedReader bufferedReader;
    private boolean readCalled = false;
    private boolean readEnded = false;

    private boolean firstEnded = false;
    private boolean secondEnded = false;

    private BlockingQueue<BiOperand> first;
    private BlockingQueue<Integer> second;

    public InputServiceImpl(String filePath) throws FileNotFoundException {
        Reader reader = new FileReader(filePath);
        bufferedReader = new BufferedReader(reader);

        first = new LinkedBlockingQueue<>();
        second = new LinkedBlockingQueue<>();
    }

    @PostConstruct
    public void init() {
//        log.debug("Input service has been created");
    }

    @PreDestroy
    public void cleanUp() {
//        log.debug("Input service will be destroyed");
    }

    @Override
    @Async
    public void readContent() {
        if (!readCalled) {
            readCalled = true;

            String currentLine;
            try {
                while ((currentLine = readLine()) != null) {
                    String[] arguments = currentLine.split("[\\s+]");
                    BiOperand current = new BiOperand(
                            Integer.valueOf(arguments[0]),
                            Integer.valueOf(arguments[1])
                    );

                    first.put(current);
//                    log.info("Arguments {} has been added", current);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error while file reading", e);
            }

            readEnded = true;
        }
    }

    @Override
    @Async
    public CompletableFuture<BiOperand> getFirst() throws InterruptedException {
        if (readEnded && first.isEmpty()) {
            firstEnded = true;
            return completedFuture(null);
        }

        return completedFuture(first.take());
    }

    @Override
    @Async
    public CompletableFuture<BiOperand> getSecond() throws InterruptedException {
        if (firstEnded && second.size() == 1) {
            secondEnded = true;
            return completedFuture(null);
        }

        return completedFuture(new BiOperand(second.take(), second.take()));
    }

    @Override
    @Async
    public void putToSecond(Integer argument) throws InterruptedException {
        second.put(argument);
    }

    @Override
    public CompletableFuture<Integer> getResult() throws InterruptedException {
        return completedFuture(second.take());
    }

    private String readLine() throws IOException {
        return bufferedReader.readLine();
    }

}
