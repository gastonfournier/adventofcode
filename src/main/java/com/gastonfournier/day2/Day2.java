package com.gastonfournier.day2;


import com.gastonfournier.utils.Reader;
import java.util.List;

enum RPS {
  Rock(1),
  Paper(2),
  Scissors(3);

  public final int points;

  RPS(int points) {
    this.points = points;
  }

  public Result against(RPS opponent) {
    if (this == opponent) {
      return Result.Draw;
    } else if ((this == Rock && opponent == Scissors)
        || (this == Scissors && opponent == Paper)
        || (this == Paper && opponent == Rock)) {
      return Result.Won;
    } else {
      return opponent.against(this).negated();
    }
  }
}

enum Result {
  Loose(0),
  Draw(3),
  Won(6);

  public final int points;

  Result(int points) {
    this.points = points;
  }

  public Result negated() {
    if (this == Won) {
      return Loose;
    } else if (this == Loose) {
      return Won;
    }
    return Draw;
  }
}

public class Day2 {
  public static void main(String[] args) {
    var lines = Reader.readFileAsLines("day2.input.txt");
    // lines = List.of("A Y", "B X", "C Z");

    firstStrategy(lines);
    secondStrategy(lines);
  }

  private static void secondStrategy(List<String> lines) {
    var sum = 0;
    for (String line : lines) {
      var parts = line.split(" ");
      sum += play2(toRPS(parts[0]), toExpectedResult(parts[1]));
    }
    System.out.println(sum);
  }

  private static void firstStrategy(List<String> lines) {
    var sum = 0;
    for (String line : lines) {
      var parts = line.split(" ");
      sum += play(toRPS(parts[0]), toRPS(parts[1]));
    }
    System.out.println(sum);
  }

  private static int play(RPS opponent, RPS myself) {
    return myself.against(opponent).points + myself.points;
  }

  private static int play2(RPS opponent, Result expected) {
    return expected.points + whichRPS(opponent, expected).points;
  }

  private static RPS whichRPS(RPS opponent, Result expected) {
    if (expected == Result.Draw) {
      return opponent;
    } else {
      for (RPS option : RPS.values()) {
        if (option.against(opponent) == expected) {
          return option;
        }
      }
    }
    throw new RuntimeException("Impossible choice");
  }

  private static RPS toRPS(String code) {
    switch (code) {
      case "A":
      case "X":
        return RPS.Rock;
      case "B":
      case "Y":
        return RPS.Paper;
      case "C":
      case "Z":
        return RPS.Scissors;
      default:
        throw new RuntimeException("Unknown code " + code);
    }
  }

  private static Result toExpectedResult(String code) {
    switch (code) {
      case "X":
        return Result.Loose;
      case "Y":
        return Result.Draw;
      case "Z":
        return Result.Won;
      default:
        throw new RuntimeException("Unknown code " + code);
    }
  }
}
