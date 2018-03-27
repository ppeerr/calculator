package com.per.calculation.rest;

import com.per.calculation.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/calculation", produces = "application/json")
public class CalculationController {

    @Autowired
    private CalculationService calculationService;

    @RequestMapping(value = "/calc/{calculationType}/{functionName}", method = RequestMethod.POST)
    public ResponseEntity calc(
            @PathVariable int calculationType,
            @PathVariable String functionName
    ) {
        calculationService.calc(calculationType, functionName);

        return new ResponseEntity(HttpStatus.OK);
    }

}
