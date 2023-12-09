package com.gastonfournier.y2023;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class DailyTest<T, U> {
    private final Solution<T, U> solver;
    private final List<Object> solutions;
    private final List<String> inputs;

    public DailyTest(Solution<T, U> solver, List<String> inputs, List<Object> solutions) {
        this.solver = solver;
        this.solutions = solutions;
        this.inputs = inputs;
    }

    void testPart1(int index) {
        String input = inputs.get(index);
        T expected = (T) solutions.get(index);
        DailyChallenge<T, U> challenge = new DailyChallenge<>(solver, Arrays.asList(input.split("\n")));
        T response = challenge.part1();
        System.out.println("Got response " + response);
        assertThat(response).isEqualTo(expected);
    }

    @Test
    void testPart1Example() {
        testPart1(0);
    }

    @Test
    void testPart1() {
        testPart1(1);
    }

    @Test
    void testPart2Example() {
        testPart2(2);
    }

    @Test
    void testPart2() {
        testPart2(3);
    }

    void testPart2(int index) {
        String input = inputs.get(index);
        U expected = (U) solutions.get(index);
        DailyChallenge<T, U> challenge = new DailyChallenge<>(solver, Arrays.asList(input.split("\n")));
        U response = challenge.part2();
        System.out.println("Got response " + response);
        assertThat(response).isEqualTo(expected);
    }
}
