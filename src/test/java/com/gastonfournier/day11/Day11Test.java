package com.gastonfournier.day11;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day11Test extends DailyChallengeTest {

    @Override
    public Solution solution() {
        return new Day11();
    }

    @Override
    public String getStringInput() {
        return """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
                
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
                
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
                
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
                
                """;
    }

    @Override
    public BigInteger expected1() {
        return BigInteger.valueOf(10605l);
    }

    @Override
    public BigInteger expected2() {
        return BigInteger.valueOf(2713310158l);
    }

    @Test
    void applyReliefDivision() {
        Function<Long, Long> relief = (Long w) -> Math.divideExact(w, 3);
        assertThat(relief.apply(1501l)).isEqualTo(500);
        assertThat(relief.apply(1862l)).isEqualTo(620);
        assertThat(relief.apply(60l)).isEqualTo(20);
        assertThat(relief.apply(71l)).isEqualTo(23);
        assertThat(relief.apply(80l)).isEqualTo(26);
    }

    @Test
    void model() {
        List<String> input = this.getInput();
        List monkeys = new Day11().model(input).left();
        assertThat(monkeys).hasSize(4);
    }
}