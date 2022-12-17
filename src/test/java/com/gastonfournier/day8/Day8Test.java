package com.gastonfournier.day8;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.day7.Day7;
import com.gastonfournier.utils.Solution;

class Day8Test extends DailyChallengeTest {

    @Override
    public Solution solution() {
        return new Day8();
    }

    @Override
    public String getStringInput() {
        return """
        30373
        25512
        65332
        33549
        35390
        """;
    }

    @Override
    public Integer expected1() {
        return 21;
    }

    @Override
    public Integer expected2() {
        return 8;
    }
}