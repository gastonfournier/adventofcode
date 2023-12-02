package com.gastonfournier.day5;

import static org.assertj.core.api.Assertions.assertThat;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day5Test extends DailyChallengeTest {

  @Override
  public Solution solution() {
    return new Day5();
  }

  @Override
  public List<String> getInput() {
    return Arrays.stream(
            """
                    [D]
                [N] [C]
                [Z] [M] [P]
                 1   2   3

                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """
                .split("\n"))
        .toList();
  }

  @Override
  public String expected1() {
    return "CMZ";
  }

  @Override
  public String expected2() {
    return "MCD";
  }

  @Test
  void checkStackAssignation() {
    // based on the position of the letter
    assertThat(new Day5().stackNumber(9)).isEqualTo(3);
    assertThat(new Day5().stackNumber(13)).isEqualTo(4);
  }
}
