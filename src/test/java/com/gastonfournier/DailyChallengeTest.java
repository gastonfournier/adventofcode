package com.gastonfournier;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class DailyChallengeTest {

    private DailyChallenge challenge;

    public abstract Solution solution();
    public abstract List<String> getInput();
    public abstract <T> T expected1();
    public abstract <T> T expected2();

    @BeforeEach
    void setup() {
        this.challenge = new DailyChallenge(solution(), getInput());
    }
    @Test
    void testPart1() {
        Object response = challenge.part1();
        System.out.println("Got response "+response);
        assertThat(response).isEqualTo(expected1());
    }

    @Test
    void testPart2() {
        Object response = challenge.part2();
        System.out.println("Got response "+response);
        assertThat(response).isEqualTo(expected2());
    }
}
