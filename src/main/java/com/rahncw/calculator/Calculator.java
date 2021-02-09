package com.rahncw.calculator;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

/**
 * this is a javadoc.
 */
@Service
public class Calculator {

  @Cacheable("sum")
  int sum(int a, int b) {
    // sleep three seconds for testing purposes
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return a + b;
  }

  @Cacheable("diff")
  int diff(int a, int b) {
    // sleep three seconds for testing purposes
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return a - b;
  }


}
