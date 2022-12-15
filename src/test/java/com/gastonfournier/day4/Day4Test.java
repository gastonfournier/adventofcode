package com.gastonfournier.day4;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class Day4Test extends DailyChallengeTest {
    @Override
    public Solution solution() {
        return new Day4();
    }

    @Override
    public List<String> getInput() {
        return Arrays.asList(
                "2-4,6-8",
                "2-3,4-5",
                "5-7,7-9",
                "2-8,3-7",
                "6-6,4-6",
                "2-6,4-8"
        );
    }

    @Override
    public Integer expected1() {
        return 2;
    }

    @Override
    public Integer expected2() {
        return 4;
    }
}