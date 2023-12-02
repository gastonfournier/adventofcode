package com.gastonfournier.day14;

import static org.junit.jupiter.api.Assertions.*;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;

class Day14Test extends DailyChallengeTest {

  @Override
  public Solution solution() {
    return new Day14();
  }

  @Override
  public String getStringInput() {
    return """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
        """;
  }

  @Override
  public Integer expected1() {
    return 24;
  }

  @Override
  public Integer expected2() {
    return 93;
  }
}
