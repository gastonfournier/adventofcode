package com.gastonfournier.utils;


import java.util.HashMap;
import java.util.Map;

public class SparseMatrix<T> {
  private final Map<Integer, Map<Integer, T>> matrix = new HashMap<>();
  private int minX = Integer.MAX_VALUE;
  private int minY = Integer.MAX_VALUE;
  private int maxX = Integer.MIN_VALUE;
  private int maxY = Integer.MIN_VALUE;

  private T emptyValue; // for printing mostly

  public SparseMatrix(T emptyValue) {
    this.emptyValue = emptyValue;
  }

  public void set(int x, int y, T c) {
    this.minX = Math.min(this.minX, x);
    this.minY = Math.min(this.minY, y);
    this.maxX = Math.max(this.maxX, x);
    this.maxY = Math.max(this.maxY, y);
    this.matrix.putIfAbsent(y, new HashMap<>());
    this.matrix.get(y).put(x, c);
  }

  public void print() {
    for (int i = minY; i < maxY; i++) {
      if (i == minY) {
        for (int j = minX; j < maxX; j++) {
          System.out.print(j % 5 == 0 ? j : " ");
          int compensateDigits = 10;
          while (j % 5 == 0 && j > compensateDigits) {
            j++; // skip 1 digit
            compensateDigits = compensateDigits * 10;
          }
        }
        System.out.print("\n");
      }
      for (int j = minX; j < maxX; j++) {
        System.out.print(this.at(j, i));
      }
      System.out.print("\t" + i + "\n");
    }
  }

  public T at(Integer x, Integer y) {
    return matrix.getOrDefault(y, new HashMap<>()).getOrDefault(x, emptyValue);
  }
}
