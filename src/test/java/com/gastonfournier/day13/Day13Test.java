package com.gastonfournier.day13;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import com.gastonfournier.utils.Tuple;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day13Test extends DailyChallengeTest {

    @Override
    public Solution solution() {
        return new Day13();
    }

    @Override
    public String getStringInput() {
        return """
        [1,1,3,1,1]
        [1,1,5,1,1]
                        
        [[1],[2,3,4]]
        [[1],4]
                        
        [9]
        [[8,7,6]]
                        
        [[4,4],4,4]
        [[4,4],4,4,4]
                        
        [7,7,7,7]
        [7,7,7]
                        
        []
        [3]
                        
        [[[]]]
        [[]]
                        
        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
        """;
    }

    @Override
    public Integer expected1() {
        return 13;
    }

    @Override
    public Integer expected2() {
        return 140;
    }

    @Test
    void testProcessInput() {
        List<Tuple<Day13.PacketData, Day13.PacketData>>  packets = new Day13().processInput(getInput());
        assertThat(packets.get(0).left().data).hasSize(5);
        assertThat(packets.get(0).right().data).hasSize(5);

        assertThat(packets.get(5).left().data).hasSize(0);
        assertThat(packets.get(5).right().data).hasSize(1);
        assertThat(packets.size()).isEqualTo(8);
        assertThat(packets.get(0).left().compareTo(packets.get(0).right())).isLessThan(1);
        assertThat(packets.get(1).left().compareTo(packets.get(1).right())).isLessThan(1);
        assertThat(packets.get(2).left().compareTo(packets.get(2).right())).isEqualTo(1);
        assertThat(packets.get(3).left().compareTo(packets.get(3).right())).isLessThan(1);
        assertThat(packets.get(4).left().compareTo(packets.get(4).right())).isEqualTo(1);
        assertThat(packets.get(5).left().compareTo(packets.get(5).right())).isLessThan(1);
        assertThat(packets.get(6).left().compareTo(packets.get(6).right())).isEqualTo(1);
        assertThat(packets.get(7).left().compareTo(packets.get(7).right())).isEqualTo(1);
    }

    @Test
    void doubleDigitNumbers() {
        List<String> input = List.of(
                "[[10,7],[9,7,2,5,1]]",
                "[[],[],[[]],[[7,2,[],[2,10,7],8],4,0,[2,[],[8,8,9],5,[6,1]],[1,4,[6,5,4,1,4],[7,7,9],2]],[[],5]]");

        List<Tuple<Day13.PacketData, Day13.PacketData>> packets = new Day13().processInput(input);
        assertThat(packets).hasSize(1);
        List<Either<Integer, Day13.PacketData>> data = packets.get(0).left().data;
        assertThat(data).hasSize(2);
        assertThat(data.get(0).isRight()).isTrue();
        assertThat(data.get(0).get().data.get(0).getLeft()).isEqualTo(10);
    }

    @Test
    void expected() {
        // got the expected value from day13.py
        DailyChallenge challenge = new DailyChallenge(new Day13(), "day13.input.txt");
        assertThat(challenge.part1()).isEqualTo(6272);
    }
}