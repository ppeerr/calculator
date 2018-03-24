package com.per

import com.per.calculation.functions.Addition
import com.per.calculation.functions.Multiplication
import com.per.calculation.functions.Subtraction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

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
