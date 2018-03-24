package com.per.io.impl;

import com.per.io.InputService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//TODO why not component?
public class InputServiceImpl implements InputService {

    private String filePath;
    private String content;

    public InputServiceImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<BiOperand> getContent() {
        try {
            readAll();
        } catch (Exception e) {
            // todo log?
            throw new RuntimeException("Error while file reading", e);
        }

        return Arrays.stream(content.split("\n"))
                .map(s -> new BiOperand(
                        Integer.valueOf(s.split("[\\s+]")[0]),
                        Integer.valueOf(s.split("[\\s+]")[1]))
                )
                .collect(Collectors.toList());
    }


    private void readAll() throws IOException {
        content = new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
