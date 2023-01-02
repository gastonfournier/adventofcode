package com.gastonfournier.day15;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;

import static org.junit.jupiter.api.Assertions.*;

class Day15Test extends DailyChallengeTest {

    @Override
    public Solution solution() {
        return new Day15(10, 20);
    }

    @Override
    public String getStringInput() {
        return """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
        """;
    }

    @Override
    public Integer expected1() {
        return 26;
    }

    @Override
    public Long expected2() {
        return 56000011L;
    }
}