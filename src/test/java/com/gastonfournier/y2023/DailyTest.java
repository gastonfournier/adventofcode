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

    @Test
    void testPart1Example() {
        String input = inputs.get(0);
        T expected = (T) solutions.get(0);
        DailyChallenge<T, U> challenge = new DailyChallenge<>(solver, Arrays.asList(input.split("\n")));
        T response = challenge.part1();
        System.out.println("Got response " + response);
        assertThat(response).isEqualTo(expected);
    }

    @Test
    void testPart1() {
        String input = inputs.get(1);
        T expected = (T) solutions.get(1);
        DailyChallenge<T, U> challenge = new DailyChallenge<>(solver, Arrays.asList(input.split("\n")));
        T response = challenge.part1();
        System.out.println("Got response " + response);
        assertThat(response).isEqualTo(expected);
    }

    @Test
    void testPart2Example() {
        String input = inputs.get(2);
        U expected = (U) solutions.get(2);
        DailyChallenge<T, U> challenge = new DailyChallenge<>(solver, Arrays.asList(input.split("\n")));
        U response = challenge.part2();
        System.out.println("Got response " + response);
        assertThat(response).isEqualTo(expected);
    }

    @Test
    void testPart2() {
        String input = inputs.get(3);
        U expected = (U) solutions.get(3);
        DailyChallenge<T, U> challenge = new DailyChallenge<>(solver, Arrays.asList(input.split("\n")));
        U response = challenge.part2();
        System.out.println("Got response " + response);
        assertThat(response).isEqualTo(expected);
    }
}
