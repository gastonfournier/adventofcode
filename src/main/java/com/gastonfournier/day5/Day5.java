package com.gastonfournier.day5;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 implements Solution<String, String> {

    public static void main(String[] args) {
        DailyChallenge challenge = new DailyChallenge(new Day5(), "day5.input.txt");
        challenge.run();
    }

    private Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    @Override
    public String part1(List<String> input) {
        Map<Integer, LinkedList<Character>> stacks = new HashMap<>();
        boolean instructions = false;
        for (String line: input) {
            if (line.isBlank()) {
                instructions = true; // stop processing crates
                continue; // skip this line
            }

            if (!instructions) {
                // process crates
                for (int i = 0; i < line.length(); i++) {
                    char it = line.charAt(i);
                    if (Character.isLetter(it)) {
                        int number = stackNumber(i);
                        // due to navigating the input from top to bottom, I'm
                        // using a queue instead of a stack so the first that comes the first that will get out
                        stacks.putIfAbsent(number, new LinkedList<>());
                        stacks.get(number).add(it);
                    }
                }
            } else {
                // process instructions
                Matcher m = pattern.matcher(line);
                if (m.find()) {
                    int amount = Integer.parseInt(m.group(1));
                    int from = Integer.parseInt(m.group(2));
                    int to = Integer.parseInt(m.group(3));

                    LinkedList<Character> destination = stacks.get(to);
                    Queue<Character> origin = stacks.get(from);
                    for (int i = 0; i < amount; i++) {
                        destination.add(0, origin.poll());
                    }
                } else {
                    throw new RuntimeException("Unexpected format of line: "+line);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stacks.size(); i++) {
            result.append(stacks.get(i+1).poll());
        }

        // validate we process stacks correctly
//        for (Map.Entry<Integer, Queue<Character>> entry: stacks.entrySet()) {
//            System.out.println("Stack "+entry.getKey()+" contains:");
//            Queue<Character> stack = entry.getValue();
//            while (!stack.isEmpty()) {
//                System.out.println("\t ["+stack.poll()+"]");
//            }
//        }
        return result.toString();
    }


    int stackNumber(int i) {
        // i is zero based
        return (i + 3) / 4;
    }

    @Override
    public String part2(List<String> input) {
        Map<Integer, LinkedList<Character>> stacks = new HashMap<>();
        boolean instructions = false;
        for (String line: input) {
            if (line.isBlank()) {
                instructions = true; // stop processing crates
                continue; // skip this line
            }

            if (!instructions) {
                // process crates
                for (int i = 0; i < line.length(); i++) {
                    char it = line.charAt(i);
                    if (Character.isLetter(it)) {
                        int number = stackNumber(i);
                        // due to navigating the input from top to bottom, I'm
                        // using a queue instead of a stack so the first that comes the first that will get out
                        stacks.putIfAbsent(number, new LinkedList<>());
                        stacks.get(number).add(it);
                    }
                }
            } else {
                // process instructions
                Matcher m = pattern.matcher(line);
                if (m.find()) {
                    int amount = Integer.parseInt(m.group(1));
                    int from = Integer.parseInt(m.group(2));
                    int to = Integer.parseInt(m.group(3));

                    LinkedList<Character> destination = stacks.get(to);
                    Queue<Character> origin = stacks.get(from);
                    Stack<Character> craneLoad = new Stack<>();
                    for (int i = 0; i < amount; i++) {
                        craneLoad.add(origin.poll());
                    }
                    destination.addAll(0, craneLoad);
                } else {
                    throw new RuntimeException("Unexpected format of line: "+line);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stacks.size(); i++) {
            result.append(stacks.get(i+1).poll());
        }

        // validate we process stacks correctly
//        for (Map.Entry<Integer, Queue<Character>> entry: stacks.entrySet()) {
//            System.out.println("Stack "+entry.getKey()+" contains:");
//            Queue<Character> stack = entry.getValue();
//            while (!stack.isEmpty()) {
//                System.out.println("\t ["+stack.poll()+"]");
//            }
//        }
        return result.toString();
    }
}
