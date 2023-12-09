package com.gastonfournier.y2023;

import com.gastonfournier.y2023.day7.Hand;
import com.gastonfournier.y2023.day7.Hand2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day7 implements com.gastonfournier.utils.Solution<Long, Long> {
    @Override
    public Long part1(List<String> input) {
        List<Hand> hands = new ArrayList<>();
        for (String line: input) {
            String[] parts = line.trim().split(" ");
            long bid = Long.parseLong(parts[1]);
            Hand hand = new Hand(parts[0], bid);
            hands.add(hand);
        }
        hands.sort(Hand::compareTo);
        long result = 0;
        for (int i = 0; i < hands.size(); i++) {
            result += hands.get(i).bid * (i + 1);
        }
        return result;
    }

    @Override
    public Long part2(List<String> input) {
        List<Hand2> hands = new ArrayList<>();
        for (String line: input) {
            String[] parts = line.trim().split(" ");
            long bid = Long.parseLong(parts[1]);
            Hand2 hand = new Hand2(parts[0], bid);
            hands.add(hand);
        }
        hands.sort(Hand2::compareTo);
        long result = 0;
        for (int i = 0; i < hands.size(); i++) {
            System.out.println(hands.get(i));
            result += hands.get(i).bid * (i + 1);
        }
        return result;
    }
}
