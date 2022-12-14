package com.gastonfournier.day4;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class Day4Test {
    List<String> testInput = Arrays.asList(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8"
    );

    @Test
    void testCase() {
        assertThat(new Day4().part1(testInput)).isEqualTo(2);
    }

    @Test
    void testCase2() {
        assertThat(new Day4().part2(testInput)).isEqualTo(4);
    }
}