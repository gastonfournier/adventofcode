package com.gastonfournier.y2023;


import com.gastonfournier.utils.Reader;

import java.util.List;

public class Day9Test extends DailyTest<Long, Long> {
    public Day9Test() {
        super(new Day9(), List.of(
            """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
            """,
                Reader.readFileAsString("y2023/day9.input.txt"),
            """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
            """,
                Reader.readFileAsString("y2023/day9.input.txt"),
            """
            10 13 16 21 30 45
            """
        ), List.of(114L, 1725987467L, 2L, 971L, 5L));
    }

}
