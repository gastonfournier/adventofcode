package com.gastonfournier.y2023;


import com.gastonfournier.utils.Reader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day6Test extends DailyTest<Integer, Long> {
    public Day6Test() {
        super(new Day6(), List.of(
              """
              Time:      7  15   30
              Distance:  9  40  200
              """,
              """
              Time:        40     70     98     79
              Distance:   215   1051   2147   1005
              """,
              """
              Time:      7  15   30
              Distance:  9  40  200
              """,
              """
              Time:        40     70     98     79
              Distance:   215   1051   2147   1005
              """
        ), List.of(288, 1084752, 71503L, 28228952L));

    }


    @Test
    void testPart() {
        super.testPart2();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 6",
            "2, 10",
            "3, 12",
            "4, 12",
            "5, 10",
            "6, 6",
            "7, 0",
    })
    void testDistance(int pressTime, int expected) {
        assertThat(new Day6().distance(pressTime, 7)).isEqualTo(expected);
    }

    @Test
    void waysToWin() {
        assertThat(new Day6().waysToWin(7, 9)).isEqualTo(4);
        assertThat(new Day6().waysToWin(15, 40)).isEqualTo(8);
        assertThat(new Day6().waysToWin(30, 200)).isEqualTo(9);
    }
}
