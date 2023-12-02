package com.gastonfournier.day9;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Point;
import com.gastonfournier.utils.Solution;
import org.junit.jupiter.api.Test;

class Day9Test extends DailyChallengeTest {

  @Override
  public Solution solution() {
    return new Day9();
  }

  @Override
  public String getStringInput() {
    return """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2""";
  }

  @Override
  public Integer expected1() {
    return 13;
  }

  @Override
  public Integer expected2() {
    return 1;
  }

  @Test
  void moveTowardsTest() {
    Day9 day9 = new Day9();
    Point tail = new Point(4, 4);
    assertThat(day9.moveTowards(tail, new Point(4, 6))).isEqualTo(new Point(4, 5));
    assertThat(day9.moveTowards(tail, new Point(4, 2))).isEqualTo(new Point(4, 3));
    assertThat(day9.moveTowards(tail, new Point(6, 4))).isEqualTo(new Point(5, 4));
    assertThat(day9.moveTowards(tail, new Point(2, 4))).isEqualTo(new Point(3, 4));
    assertThat(day9.moveTowards(tail, new Point(5, 6))).isEqualTo(new Point(5, 5));
  }
}
