package com.gastonfournier.y2023;


import com.gastonfournier.utils.Reader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5Test extends DailyTest<Long, Long> {

    public static final String EXAMPLE = """
            seeds: 79 14 55 13
                                                                    
            seed-to-soil map:
            50 98 2
            52 50 48
                        
            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15
                        
            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4
                        
            water-to-light map:
            88 18 7
            18 25 70
                        
            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13
                        
            temperature-to-humidity map:
            0 69 1
            1 0 69
                        
            humidity-to-location map:
            60 56 37
            56 93 4
                                                                    
            """;

    public Day5Test() {
        super(new Day5(), List.of(
                EXAMPLE,
                Reader.readFileAsString("y2023/day5.input.txt"),
                EXAMPLE,
                Reader.readFileAsString("y2023/day5.input.txt")),
                List.of(35L, 51752125L, 46L, 12634632L));
    }

    @Test
    void testPart() {
        super.testPart2();
    }

    @Test
    void processLine() {
        Map<Range, Long> range = new Day5().processRangeLine("50 98 2");
        Map<Range, Long> expectedMap = Map.of(
                new Range(98, 99), 50L
        );
        assertThat(range).isEqualTo(expectedMap);
    }

    @Test
    void rangeMap() {
        Day5 solver = new Day5();
        Map<Range, Long> rangeMap = Map.of(
                // 50 98 2
                new Range(98, 99), 50L,
                // 52 50 48
                new Range(50, 97), 52L
        );
        assertThat(solver.map(98, rangeMap)).isEqualTo(50);
        assertThat(solver.map(99, rangeMap)).isEqualTo(51);
        assertThat(solver.map(10, rangeMap)).isEqualTo(10);
        assertThat(solver.map(53, rangeMap)).isEqualTo(55);
    }

    @Test
    void numberOfSeeds() {
        Day5 solver = new Day5();
        solver.processInput(Reader.readFileAsLines("y2023/day5.input.txt"), false);
        long min = Long.MAX_VALUE;

        List<Range> newSeedsRanges = new ArrayList<>();
        for (Range range: solver.seedsRange) {
            System.out.println("Range " + range.from() + " to " + range.to() + " size " + (range.to() - range.from()));
            // find minimum of this range not included in other ranges
            Map<Range, Long> layer = solver.mappings.get(0);
            for (Range otherRange: layer.keySet()) {
                Optional<Range> intersection = range.intersection(otherRange);
                intersection.ifPresent(r -> {
                    newSeedsRanges.add(r);
                    System.out.println("Intersection length " + r.length());
                });
                if (!otherRange.contains(range.from())) {
                    min = Math.min(min, range.from());
                } else {
                    long potentialNewMin = range.contains(otherRange.to())? otherRange.to() + 1 : range.to();
                    min = Math.min(min, potentialNewMin);
                }
            }
        }
        System.out.println("Min found without mapping " + min);
        System.out.println(solver.seedsRange.stream().map(Range::length).reduce(0L, Long::sum));
        System.out.println(newSeedsRanges.stream().map(Range::length).reduce(0L, Long::sum));
    }
}
