package com.gastonfournier.utils;

import java.util.List;

public class DailyChallenge<T, U> {
    private final List<String> lines;
    private final Solution<T, U> solution;

    public DailyChallenge(Solution<T, U> solution, String inputFile) {
        this.solution = solution;
        this.lines = Reader.readFileAsLines(inputFile);
    }

    public DailyChallenge(Solution<T, U> solution, List<String> lines) {
        this.solution = solution;
        this.lines = lines;
    }

    public T part1() {
        return this.solution.part1(this.lines);
    }

    public U part2() {
        return this.solution.part2(this.lines);
    }

    public void run() {
        System.out.println("Solution 1: " + this.part1());
        System.out.println("Solution 2: " + this.part2());
    }
}
