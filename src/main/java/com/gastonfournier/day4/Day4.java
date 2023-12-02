package com.gastonfournier.day4;

import static com.gastonfournier.utils.Reader.readFileAsLines;

import com.gastonfournier.utils.Solution;
import java.util.List;

public class Day4 implements Solution<Integer, Integer> {
  public static void main(String[] args) {
    List<String> input = readFileAsLines("day4.input.txt");
    System.out.println(new Day4().part1(input));
    System.out.println(new Day4().part2(input));
  }

  @Override
  public Integer part1(List<String> input) {
    int count = 0;
    for (String pairCsv : input) {
      String[] elves = pairCsv.split(",");
      Range elf1 = this.decodeRange(elves[0]);
      Range elf2 = this.decodeRange(elves[1]);
      if (elf1.includes(elf2) || elf2.includes(elf1)) {
        count++;
      }
    }
    return count;
  }

  private Range decodeRange(String elf) {
    String[] parts = elf.split("-");
    return new Range(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
  }

  @Override
  public Integer part2(List<String> input) {
    int count = 0;
    for (String pairCsv : input) {
      String[] elves = pairCsv.split(",");
      Range elf1 = this.decodeRange(elves[0]);
      Range elf2 = this.decodeRange(elves[1]);
      if (elf1.overlaps(elf2) || elf2.overlaps(elf1)) {
        count++;
      }
    }
    return count;
  }
}
