package com.gastonfournier.day6;


import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;
import java.util.List;
import org.assertj.core.util.Lists;

class Day6Test extends DailyChallengeTest {

  @Override
  public Solution solution() {
    return new Day6();
  }

  @Override
  public List<String> getInput() {
    return Lists.list(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
        "bvwbjplbgvbhsrlpgdmjqwftvncz",
        "nppdvjthqldpwncqszvftbrmjlhg",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw");
  }

  @Override
  public List<Integer> expected1() {
    return Lists.list(7, 5, 6, 10, 11);
  }

  @Override
  public List<Integer> expected2() {
    return Lists.list(19, 23, 23, 29, 26);
  }
}
