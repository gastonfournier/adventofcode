package com.gastonfournier.day1;


import com.gastonfournier.utils.Reader;
import java.util.List;
import java.util.PriorityQueue;

public class Day1 {
  public static void main(String[] args) {
    var lines = Reader.readFileAsLines("day1.input.txt");
    partOne(lines);
    Day1.partTwo(lines, 1); // alternative way
    Day1.partTwo(lines, 3);
  }

  private static void partTwo(List<String> lines, Integer top) {
    var minHeap = new PriorityQueue<Integer>(top + 1);

    var currentSum = 0;
    for (String line : lines) {
      if (line.trim().equals("")) {
        minHeap.add(currentSum);
        if (minHeap.size() > top) {
          minHeap.remove();
        }
        currentSum = 0;
      } else {
        currentSum += Integer.parseInt(line);
      }
    }
    System.out.println(minHeap.stream().reduce(0, Math::addExact));
  }

  private static void partOne(List<String> lines) {
    var max = 0;
    var currentSum = 0;
    for (String line : lines) {
      if (line.trim().equals("")) {
        if (currentSum > max) {
          max = currentSum;
        }
        currentSum = 0;
      } else {
        currentSum += Integer.parseInt(line);
      }
    }
    System.out.println(max);
  }
}
