package com.gastonfournier;

import static org.assertj.core.api.Assertions.assertThat;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public abstract class DailyChallengeTest<T, U> {

  private DailyChallenge challenge;

  public abstract Solution solution();

  public List<String> getInput() {
    return Arrays.asList(getStringInput().split("\n"));
  }

  public String getStringInput() {
    throw new RuntimeException("Either extend and override getInput or getInputStream");
  }

  public abstract T expected1();

  public abstract U expected2();

  @BeforeEach
  void setup() {
    this.challenge = new DailyChallenge(solution(), getInput());
  }

  @Test
  @Disabled
  void testPart1() {
    Object response = challenge.part1();
    System.out.println("Got response " + response);
    assertThat(response).isEqualTo(expected1());
  }

  @Test
  @Disabled
  void testPart2() {
    Object response = challenge.part2();
    System.out.println("Got response " + response);
    assertThat(response).isEqualTo(expected2());
  }
}
