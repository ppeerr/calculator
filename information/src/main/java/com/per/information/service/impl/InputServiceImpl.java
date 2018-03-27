package com.per.information.service.impl;

import com.per.information.service.InputService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;

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
    private volatile boolean secondEnded = false;

    private BlockingQueue<BiOperand> first;
    private BlockingQueue<Integer> second;

    public InputServiceImpl(String filePath) throws IOException {
        Reader reader = new FileReader(new ClassPathResource("inputMid.txt").getFile());
        bufferedReader = new BufferedReader(reader);

        first = new LinkedBlockingQueue<>();
        second = new LinkedBlockingQueue<>();
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
            log.info("The first queue is empty");
            return completedFuture(null);
        }


        BiOperand value = first.take();
//        log.info("The first ({},{}) value was taken", value.getA(), value.getB());
        return completedFuture(value);
    }

    @Override
    @Async
    public CompletableFuture<BiOperand> getSecond() throws InterruptedException {
        if (firstEnded && second.size() == 1) {
            secondEnded = true;
            log.info("The second queue is empty");
            return completedFuture(null);
        }

        BiOperand value = new BiOperand(second.take(), second.take());
//        log.info("The second ({},{}) value was taken", value.getA(), value.getB());
        return completedFuture(value);
    }

    @Override
    @Async
    public void putToSecond(Integer argument) throws InterruptedException {
        second.put(argument);
    }

    @Override
    public Integer getResult() throws InterruptedException {
        if (!secondEnded) {
            return null;
        }

        return second.take();
    }

    private String readLine() throws IOException {
        return bufferedReader.readLine();
    }

}
