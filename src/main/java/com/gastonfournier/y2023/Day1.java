package com.gastonfournier.y2023;


import com.gastonfournier.utils.Reader;
import com.gastonfournier.utils.Solution;
import java.util.List;
import java.util.regex.Pattern;

public class Day1 implements Solution<Integer, Long> {
  public static void main(String[] args) {
    String input =
        """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """;
    var lines = List.of(input.split("\n"));
    int sum = calculateSum(lines);
    // System.out.println(sum);
    Day1 day1 = new Day1();
    day1.part1(Reader.readFileAsLines("y2023/day1.input.txt"));

    /*day1.part2(List.of("""
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen""".split("\n")));*/
    day1.part2(Reader.readFileAsLines("y2023/day1.input.txt"));
  }

  private static int calculateSum(List<String> lines) {
    int sum = 0;
    for (var line : lines) {
      var chars = line.toCharArray();
      int i = 0;
      Integer first = null;
      Integer second = null;
      while (i < chars.length && first == null) {
        var c = chars[i++];
        if (Character.isDigit(c)) {
          first = Character.getNumericValue(c) * 10;
        }
      }
      i--;
      var j = chars.length - 1;
      while (j >= i && second == null) {
        var c = chars[j--];
        if (Character.isDigit(c)) {
          second = Character.getNumericValue(c);
        }
      }

      sum += first + second;
    }
    return sum;
  }

  @Override
  public Integer part1(List<String> lines) {
    int sum = calculateSum(lines);
    System.out.println(sum);
    return sum;
  }

  @Override
  public Long part2(List<String> lines) {
    Long sum = calculateSum2(lines);
    System.out.println(sum);
    return sum;
  }

  private static Long calculateSum2(List<String> lines) {
    Long sum = 0l;
    Pattern pattern = Pattern.compile("([0-9]|one|two|three|four|five|six|seven|eight|nine)");
    for (var line : lines) {
      var matcher = pattern.matcher(line);
      if (matcher.find()) {
        String m1 = matcher.group(0);

        String m2 = null;
        int start = line.length() - 1;
        while (m2 == null) {
          matcher = pattern.matcher(line.substring(start));
          if (matcher.find()) {
            m2 = matcher.group(0);
          }
          start--;
        }

        long number = toInt(m1) * 10L + toInt(m2);
        if (number > 99) {
          throw new RuntimeException("Number is too big");
        }
        sum += number;
        // System.out.println(line + " -> +" + number + "=" + sum);
      } else {
        throw new RuntimeException("No match found");
      }
    }
    return sum;
  }

  private static int toInt(String m1) {
    return switch (m1) {
      case "one" -> 1;
      case "two" -> 2;
      case "three" -> 3;
      case "four" -> 4;
      case "five" -> 5;
      case "six" -> 6;
      case "seven" -> 7;
      case "eight" -> 8;
      case "nine" -> 9;
      default -> Integer.parseInt(m1);
    };
  }
}
