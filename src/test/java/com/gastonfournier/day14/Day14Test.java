package com.gastonfournier.day14;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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