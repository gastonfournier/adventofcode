package com.gastonfournier.day12;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;
import com.gastonfournier.utils.Tuple;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

class Day12Test extends DailyChallengeTest {

    @Override
    public Solution solution() {
        return new Day12();
    }

    @Override
    public String getStringInput() {
        return """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
        """;
    }

    @Override
    public Integer expected1() {
        return 31;
    }

    @Override
    public Integer expected2() {
        return 29;
    }

    @Test
    void fromAndToTuple() {
        Day12 day12 = new Day12();
        int width = 10;
        compare(day12, 0, 0, 0, width);
        compare(day12, 0, 3, 3, width);
        compare(day12, 0, 9, 9, width);
        compare(day12, 1, 0, 10, width);
        compare(day12, 9, 9, 99, width);
    }

    private static void compare(Day12 day12, int x, int y, int listPos, int width) {
        assertThat(day12.listPos(new Tuple<>(x, y), width)).isEqualTo(listPos);
        assertThat(day12.asTuple(listPos, width)).isEqualTo(new Tuple<>(x, y));
    }

    @Test
    void priorityQueue() {
        int[] weights = new int[]{10, 3, 5, 1, 2};
        var queue = new PriorityQueue<Integer>((o1, o2) ->
                Integer.compare(weights[o1-1], weights[o2-1]));
        queue.add(3);
        queue.add(2);
        queue.add(5);
        queue.add(1);
        queue.add(4);
        while(!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}