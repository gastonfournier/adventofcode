package com.gastonfournier.y2023;

import com.gastonfournier.utils.CyclicIterator;

import java.util.*;

public class Day8 implements com.gastonfournier.utils.Solution<Integer, Long> {
    @Override
    public Integer part1(List<String> input) {
        String[] instructions = input.get(0).split("");
        Map<String, String[]> graph = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String[] parts = input.get(i).replaceAll(" ", "").split("=");
            String node = parts[0];
            String[] edges = parts[1].replaceAll("[()]", "").split(",");
            graph.put(node, edges);
        }

        String currentNode = "AAA";
        String targetNode = "ZZZ";
        CyclicIterator<String> iterator = new CyclicIterator<>(Arrays.asList(instructions));
        int steps = 0;
        while(!currentNode.equals(targetNode)) {
            String direction = iterator.next();
            currentNode = graph.get(currentNode)[direction.equals("L") ? 0 : 1];
            steps++;
        }
        return steps;
    }

    @Override
    public Long part2(List<String> input) {
        List<String> currentNodes = new ArrayList<>();
        List<String> endingNodes = new ArrayList<>();
        List<String> startingPosition = new ArrayList<>();

        String[] instructions = input.get(0).split("");
        Map<String, String[]> graph = new HashMap<>();
        int skip = 0;
        for (int i = 2; i < input.size(); i++) {
            String[] parts = input.get(i).replaceAll(" ", "").split("=");
            String node = parts[0];
            if (node.endsWith("A")) {//&& currentNodes.size() < 4 && skip++ < 2) {
                currentNodes.add(node);
            }
            if (node.endsWith("Z")) {
                endingNodes.add(node);
            }
            String[] edges = parts[1].replaceAll("[()]", "").split(",");
            graph.put(node, edges);
        }
        startingPosition.addAll(currentNodes);
        System.out.println(Arrays.toString(currentNodes.toArray()));
        System.out.println(Arrays.toString(endingNodes.toArray()));
        CyclicIterator<String> iterator = new CyclicIterator<>(Arrays.asList(instructions));
        Map<String, Long> visited = new HashMap<>();
        long steps = 0;
        while (visited.size() != startingPosition.size()) {
            steps++;
            String direction = iterator.next();
            final long currentSteps = steps;
            for (int i = 0; i < currentNodes.size(); i++) {
                String node = currentNodes.get(i);
                if (node.endsWith("Z")) {
                    visited.putIfAbsent(i + ":"+direction + "-of-"+node, currentSteps);
                    System.out.println(i + " - Found Z at " + currentSteps);
                }
            }
            currentNodes = currentNodes.stream().map(node -> graph.get(node)[direction.equals("L") ? 0 : 1]).toList();
            //System.out.println(Arrays.toString(currentNodes.toArray()));
        }
        System.out.println(visited.entrySet().stream().filter(e -> e.getKey().endsWith("Z")).toList());
        // I don't think this is a generic solution. I found that all the cycles were similar it was 1 + cycle period.
        // Probably I should have done something different if the cycles were different (i.e. 10, 12, 14, 16 series has a cycle of 2 but c would be 8).
        // c is the steps performed before starting the cycle.
        long lcm = findLCM(visited.values().stream().map(v -> v - 1).toList());
        System.out.println("LCM: " + lcm);
        return lcm;
    }

    // Method to find the LCM of a list of numbers
    public static long findLCM(List<Long> numbers) {
        long result = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            result = findLCM(result, numbers.get(i));
        }

        return result;
    }

    // Method to find the LCM of two numbers
    private static long findLCM(long num1, long num2) {
        return (num1 * num2) / findGCD(num1, num2);
    }

    // Method to find the GCD using the Euclidean algorithm
    private static long findGCD(long num1, long num2) {
        while (num2 != 0) {
            long temp = num2;
            num2 = num1 % num2;
            num1 = temp;
        }
        return num1;
    }
}
