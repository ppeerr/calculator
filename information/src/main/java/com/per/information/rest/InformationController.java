package com.per.information.rest;

import com.per.information.service.InputService;
import com.per.information.service.impl.BiOperand;
import com.per.information.service.impl.InputServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/information", produces = "application/json")
public class InformationController {

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public ResponseEntity startRead() {
        InputService inputService = (InputService) applicationContext.getBean("inputService");
        inputService.readContent();

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public ResponseEntity<BiOperand> getFirst() throws InterruptedException, ExecutionException {
        InputService inputService = (InputService) applicationContext.getBean("inputService");
        return new ResponseEntity<>(inputService.getFirst().get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/second", method = RequestMethod.GET)
    public ResponseEntity<BiOperand> getSecond() throws InterruptedException, ExecutionException {
        InputService inputService = (InputService) applicationContext.getBean("inputService");
        return new ResponseEntity<>(inputService.getSecond().get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/second/{argument}", method = RequestMethod.PUT)
    public ResponseEntity putToSecond(@PathVariable Integer argument) throws InterruptedException {
        InputService inputService = (InputService) applicationContext.getBean("inputService");
        inputService.putToSecond(argument);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ResponseEntity<Integer> getResult() throws InterruptedException {
        InputService inputService = (InputService) applicationContext.getBean("inputService");
        return new ResponseEntity<>(inputService.getResult(), HttpStatus.OK);
    }


    @Bean
    public InputService inputService() throws IOException {
        return new InputServiceImpl("input.txt");
    }
}
