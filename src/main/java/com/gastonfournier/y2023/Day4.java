package com.gastonfournier.y2023;

import java.util.*;

public class Day4 implements com.gastonfournier.utils.Solution<Integer, Long> {
    @Override
    public Integer part1(List<String> input) {
        return input.stream().map(this::processLine).reduce(0, Integer::sum);
    }

    private Integer processLine(String line) {
        String[] parts = line.split(":");
        String[] numbers = parts[1].split("\\|");
        List<Integer> winners = Arrays.stream(numbers[0].trim().split("\s+")).map(Integer::parseInt).toList();
        List<Integer> have = Arrays.stream(numbers[1].trim().split("\s+")).map(Integer::parseInt).toList();
        return have.stream().filter(winners::contains).reduce(0, (a, b) -> a == 0? 1 : a * 2);
    }

    private long[] memory;
    @Override
    public Long part2(List<String> input) {
        memory = new long[input.size()];
        Long[] cardResults = new Long[input.size()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            String[] parts = line.split(":");
            String[] numbers = parts[1].split("\\|");
            List<Integer> winners = Arrays.stream(numbers[0].trim().split("\s+")).map(Integer::parseInt).toList();
            List<Integer> have = Arrays.stream(numbers[1].trim().split("\s+")).map(Integer::parseInt).toList();
            cardResults[i] = have.stream().filter(winners::contains).count();
        }
        long total = 0L;
        for (int i = 0; i < input.size(); i++) {
            total += countCopies(cardResults, i);
        }
        return total;
    }

    Long countCopies(Long[] cardResults, int i) {
        if (cardResults[i] == 0) {
            memory[i] = 1L;
            return 1L;
        }
        long total = 0L;
        long copies = cardResults[i];
        for (int j = i + 1; j <= i + copies; j++) {
            if (memory[j] == 0) {
                memory[j] = countCopies(cardResults, j);
            }
            total += memory[j];
        }
        return 1L + total;
    }
}
