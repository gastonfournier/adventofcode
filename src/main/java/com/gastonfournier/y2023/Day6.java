package com.gastonfournier.y2023;

import java.util.Arrays;
import java.util.List;

public class Day6 implements com.gastonfournier.utils.Solution<Integer, Long> {
    @Override
    public Integer part1(List<String> input) {
        Integer[] times = processLine(input.get(0));
        Integer[] bestScores = processLine(input.get(1));
        int result = 1;
        for (int race = 0; race < times.length; race++) {
            result = result * waysToWin(times[race], bestScores[race]);
        }
        return result;
    }

    int waysToWin(int maxRaceTime, int bestScore) {
        Integer lastOk = null;
        int pressTime = maxRaceTime / 2; // start in the middle
        while (distance(pressTime, maxRaceTime) > bestScore) {
            lastOk = pressTime;
            pressTime = pressTime / 2;
        }
        if (lastOk == null) throw new RuntimeException("lastOk is null");

        for (int i = pressTime; i <= lastOk; i++) {
            if (distance(i, maxRaceTime) > bestScore) {
                return maxRaceTime - i - i + 1;
            }
        }

        throw new RuntimeException("Not found");
    }

    long waysToWin2(long maxRaceTime, long bestScore) {
        Long lastOk = null;
        long pressTime = maxRaceTime / 2; // start in the middle
        while (distance2(pressTime, maxRaceTime) > bestScore) {
            lastOk = pressTime;
            pressTime = pressTime / 2;
        }
        if (lastOk == null) throw new RuntimeException("lastOk is null");

        for (long i = pressTime; i <= lastOk; i++) {
            if (distance2(i, maxRaceTime) > bestScore) {
                return maxRaceTime - i - i + 1;
            }
        }

        throw new RuntimeException("Not found");
    }

    long distance2(long pressTime, long maxRaceTime) {
        return (maxRaceTime - pressTime) * pressTime;
    }

    int distance(int pressTime, int maxRaceTime) {
        return (maxRaceTime - pressTime) * pressTime;
    }

    private static Integer[] processLine(String line) {
        return Arrays.stream(line.replaceAll("\s+", " ").split(" ")
        ).filter(t -> t.matches("\\d+")).map(Integer::parseInt).toArray(Integer[]::new);
    }

    private static Long processLineV2(String line) {
        return Long.parseLong(line.replaceAll("\s+", "").split(":")[1]);
    }

    @Override
    public Long part2(List<String> input) {
        Long time = processLineV2(input.get(0));
        Long bestScore = processLineV2(input.get(1));
        return waysToWin2(time, bestScore);
    }
}
