package com.gastonfournier.y2023;


import com.gastonfournier.utils.Reader;
import com.gastonfournier.y2023.day7.CardLabel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gastonfournier.y2023.day7.CardLabel.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Day7Test extends DailyTest<Long, Long> {
    public Day7Test() {
        super(new Day7(), List.of(
              """
              32T3K 765
              T55J5 684
              KK677 28
              KTJJT 220
              QQQJA 483
              """,
              Reader.readFileAsString("y2023/day7.input.txt"),
              """
              32T3K 765
              T55J5 684
              KK677 28
              KTJJT 220
              QQQJA 483
              """,
              Reader.readFileAsString("y2023/day7.input.txt")
        ), List.of(6440L, 247815719L, 5905L, 1L));
        // 248752994 too high
        // 249366045 too high
        // 249711015 too high
        // 249360214 not right
        // 248749606 not right

    }


    @Test
    void testPart() {
        super.testPart2();
    }

    @Test
    void testCompare() {
        Integer a = 14;
        Integer b = 13;
        assertThat(a.compareTo(b)).isEqualTo(CardLabel.A.compareTo(K));

        List<Integer> list = new ArrayList<>(List.of(1, 3, 7, 5));
        Collections.sort(list);
        System.out.println(list);

        List<CardLabel> list2 = new ArrayList<>(List.of(A, TWO, THREE, K));
        Collections.sort(list2);
        System.out.println(list2);
    }
}
