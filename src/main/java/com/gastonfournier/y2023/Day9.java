package com.gastonfournier.y2023;

import java.util.Arrays;
import java.util.List;

public class Day9 implements com.gastonfournier.utils.Solution<Long, Long> {
    @Override
    public Long part1(List<String> input) {
        long sum = 0L;
        for (String line: input) {
            Long[] serie = Arrays.stream(line.split(" ")).map(Long::parseLong).toArray(Long[]::new);
            Long result = solve(serie);
            sum += result;
        }
        return sum;
    }

    private Long solve(Long[] serie) {
        if (Arrays.stream(serie).allMatch(x -> x == 0)) {
            return 0L;
        }
        return serie[serie.length - 1] + solve(buildNextSerie(serie));
    }

    private static Long[] buildNextSerie(Long[] serie) {
        Long[] newSerie = new Long[serie.length - 1];
        for (int i = 0; i < serie.length - 1; i++) {
            newSerie[i] = serie[i + 1] - serie[i];
        }
        return newSerie;
    }

    @Override
    public Long part2(List<String> input) {
        long sum = 0L;
        for (String line: input) {
            Long[] serie = Arrays.stream(line.split(" ")).map(Long::parseLong).toArray(Long[]::new);
            Long result = solvePrev(serie);
            sum += result;
        }
        return sum;
    }

    private Long solvePrev(Long[] serie) {
        if (Arrays.stream(serie).allMatch(x -> x == 0)) {
            return 0L;
        }
        return serie[0] - solvePrev(buildNextSerie(serie));
    }
}
