package com.rahncw.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CalculatorTest {

  @Test
  public void testSum() {
    Calculator calculator = new Calculator();
    Assertions.assertEquals(calculator.sum(5, 3), 8);
  }

  public static void main(String[] args) {

  }
}
