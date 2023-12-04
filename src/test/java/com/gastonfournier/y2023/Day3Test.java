package com.gastonfournier.y2023;


import com.gastonfournier.utils.Reader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3Test extends DailyTest<Integer, Integer> {
    public Day3Test() {
        super(new Day3(), List.of(
            """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """,
                Reader.readFileAsString("y2023/day3.input.txt"),
                """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """,
                Reader.readFileAsString("y2023/day3.input.txt")
        ), List.of(4361, 525911, 467835, 73256867));
        // 73256867 is too low
        // 94184896 is too high

    }

    @ParameterizedTest
    @CsvSource(value = {
            "2.3,.*.,...",
            "2..,3*.,...",
            "2..,.*3,...",
            "2..,.*.,3..",
            "2..,.*.,.3.",
            "2..,.*.,..3",

            "2..,*..,3..",
            "2..,*..,.3.",

            ".2.,3*.,...",
            ".2.,.*3,...",
            ".2.,.*.,3..",
            ".2.,.*.,.3.",
            ".2.,.*.,..3",
            ".2.,.*.,3..",

            "..2,3*.,...",
            "..2,.*3,...",
            "..2,.*.,3..",
            "..2,.*.,.3.",
            "..2,.*.,..3",
            "...,.*3,2..",
            "...,.*.,2.3",
    })
    void findGear(String row1, String row2, String row3) {
        Day3 solver = new Day3();
        List<String> input = List.of(row1, row2, row3);
        assertThat(solver.part2(input)).isEqualTo(6);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2.10,.*..,....",
            "2...,*10.,....",
            "2...,.*10,....",
            "2...,.*..,10..",
            "2...,.*..,.10.",
            "2...,.*..,..10",

            ".2..,*10.,....",
            ".2..,.*10,....",
            ".2..,.*..,10..",
            ".2..,.*..,.10.",
            ".2..,.*..,..10",
            ".2..,..*.,10..",
            ".2..,..*.,.10.",
            ".2..,..*.,..10",

            "..2.,.*10,....",
            "..2.,.*..,10..",
            "..2.,.*..,.10.",
            "..2.,.*..,..10",
    })
    void findGear2(String row1, String row2, String row3) {
        Day3 solver = new Day3();
        assertThat(row1.length()).isEqualTo(4);
        assertThat(row2.length()).isEqualTo(4);
        assertThat(row3.length()).isEqualTo(4);
        List<String> input = List.of(row1, row2, row3);
        assertThat(solver.part2(input)).isEqualTo(20);
    }

    @Test
    void testPart2Example() {
        super.testPart2Example();
    }
}
