package com.gastonfournier.utils;


import java.util.function.Supplier;

public class Timer {
  private final long start;
  private Long threshold = 0l;

  public Timer() {
    this.start = System.currentTimeMillis();
  }

  public void setThreshold(Long threshold) {
    this.threshold = threshold;
  }

  public static <T> T time(Supplier<T> supplier) {
    return time(supplier, null);
  }

  public static <T> T time(Supplier<T> supplier, String additionalInfo) {
    return time(supplier, 0, additionalInfo);
  }

  public static <T> T time(Supplier<T> supplier, long threshold) {
    return time(supplier, threshold, null);
  }

  public static <T> T time(Supplier<T> supplier, long threshold, String additionalInfo) {
    long start = System.currentTimeMillis();
    T result = supplier.get();
    long elapsed = System.currentTimeMillis() - start;
    if (elapsed >= threshold) {
      System.out.println(elapsed + "ms" + (additionalInfo == null ? "" : " - " + additionalInfo));
    }
    return result;
  }

  public void print(String text) {
    long elapsed = System.currentTimeMillis() - this.start;
    if (elapsed >= this.threshold) {
      System.out.println(elapsed + "ms - " + text);
    }
  }
}
