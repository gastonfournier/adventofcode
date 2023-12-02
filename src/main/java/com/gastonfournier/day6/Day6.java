package com.gastonfournier.day6;


import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import java.util.*;

public class Day6 implements Solution<List<Integer>, List<Integer>> {

  public static void main(String[] args) {
    DailyChallenge challenge = new DailyChallenge(new Day6(), "day6.input.txt");
    challenge.run();
  }

  @Override
  public List<Integer> part1(List<String> input) {
    List<Integer> results = new ArrayList<>();
    for (String line : input) {
      results.add(findFirstXNonRepeatingCharacters(line, 4));
    }
    return results;
  }

  private int findFirstXNonRepeatingCharacters(String line, int size) {
    int from = 0;
    int to = size;
    while (to < line.length()) {
      if (findFirstRepeatedChar(line.substring(from, to)) == null) {
        return to;
      } else {
        from++;
        to++;
      }
    }
    throw new RuntimeException(
        "Not found a sequence of " + size + " non-repeating-chars in " + line);
  }

  private Character findFirstRepeatedChar(String substring) {
    Set<Character> occurrences = new HashSet<>();
    for (int i = 0; i < substring.length(); i++) {
      char c = substring.charAt(i);
      if (occurrences.contains(c)) {
        return c;
      } else {
        occurrences.add(c);
      }
    }
    return null;
  }

  @Override
  public List<Integer> part2(List<String> input) {
    List<Integer> results = new ArrayList<>();
    for (String line : input) {
      results.add(findFirstXNonRepeatingCharacters(line, 14));
    }
    return results;
  }
}
