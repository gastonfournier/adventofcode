package com.gastonfournier.day3;

import com.gastonfournier.utils.Reader;

import java.util.*;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        var lines = Reader.readFileAsLines("day3.input.txt");
//        lines = List.of("vJrwpWtwJgWrhcsFMMfFFhFp",
//                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
//                "PmmdzqPrVvPwwTWBwg",
//                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
//                "ttgJtRGJQctTZtZT",
//                "CrZsJsPPZsGzwwsLwLmpwMDw");

        // first part
        System.out.println(lines.stream().map(Day3::findCommonChar).map(Day3::toPriority).reduce(Math::addExact).orElseThrow());

        // second part
        Map<Character, Integer> occurrences = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (i % 3 == 0) {
                if (!occurrences.isEmpty()) {
                    Character common = occurrences.entrySet().stream().filter(es -> es.getValue() == 3).map(Map.Entry::getKey).findFirst().orElseThrow();
                    //System.out.println("Found common " + common);
                    sum += toPriority(common);
                    occurrences = new HashMap<>();
                }
            }
            for (Character c: toCharacterSet(lines.get(i).toCharArray())) {
                occurrences.compute(c, (k, v) -> (v == null)? 1 : v + 1);
            }
        }
        // last 3
        if (!occurrences.isEmpty()) {
            Character common = occurrences.entrySet().stream().filter(es -> es.getValue() == 3).map(Map.Entry::getKey).findFirst().orElseThrow();
            //System.out.println("Found common " + common);
            sum += toPriority(common);
        }

        System.out.println(sum);
    }

    private static int toPriority(Character character) {
        int priority = character - 96;
        if (priority < 0) {
            priority = priority + 58;
        }
        //System.out.println("Found "+character+ " -> " + priority);
        return priority;
    }

    private static char findCommonChar(String word) {
        int size = word.length();
        if (size % 2 != 0) {
            throw new RuntimeException("Expect all to be even");
        }
        int half = size / 2;
        char[] firstHalf = word.substring(0, half).toCharArray();
        Set<Character> chars = toCharacterSet(firstHalf);
        char[] secondHalf = word.substring(half , size).toCharArray();
        //System.out.println("Split "+word + " into "+ new String(firstHalf) + " & " + new String(secondHalf));
        for (Character c : secondHalf) {
            if (chars.contains(c)) {
                return c;
            }
        }
        throw new RuntimeException("Didn't find same char in "+word);
    }

    private static Set<Character> toCharacterSet(char[] charArray) {
        Set<Character> chars = new HashSet<>();
        for (Character c : charArray) {
            chars.add(c);
        }
        return chars;
    }
}
