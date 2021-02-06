package com.rahncw.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    @Autowired
    private Calculator calculator;

    @RequestMapping("/sum")
    String sum(@RequestParam("a") int a,
               @RequestParam("b") int b) {
        return String.valueOf((calculator.sum(a, b)));
    }

    @RequestMapping("/diff")
    String diff(@RequestParam("a") int a,
        @RequestParam("b") int b) {
        return String.valueOf((calculator.diff(a, b)));
    }
}
