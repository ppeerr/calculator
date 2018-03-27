package com.per.calculation

import com.per.functions.Addition
import com.per.functions.Multiplication
import com.per.functions.Subtraction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CalculationAppConfiguration {

    @Bean
    Addition addition() {
        new Addition()
    }

    @Bean
    Multiplication multiplication() {
        new Multiplication()
    }

    @Bean
    Subtraction subtraction() {
        new Subtraction()
    }

}
