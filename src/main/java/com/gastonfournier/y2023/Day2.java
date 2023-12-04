package com.gastonfournier.y2023;

import com.gastonfournier.utils.Reader;
import com.gastonfournier.utils.Tuple;

import java.util.*;
import java.util.stream.Collectors;

import static com.gastonfournier.y2023.Day2.Color.*;

public class Day2 {
    enum Color {
        Blue("blue"),
        Red("red"),
        Green("green");

        private final String color;
        Color(String color) {
            this.color = color;
        }

        static Color from(String color) {
            return Arrays.stream(Color.values())
                    .filter(c -> c.color.equals(color))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unknown color: " + color));
        }
    }
    public static void main(String[] args) {
        part1(Arrays.asList("""
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".split("\n")), Map.of(Red, 12, Green, 13, Blue, 14));

        part1(Reader.readFileAsLines("y2023/day2.input.txt"), Map.of(Red, 12, Green, 13, Blue, 14));

        part2(Arrays.asList("""
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".split("\n")));

        part2(Reader.readFileAsLines("y2023/day2.input.txt"));
    }

    private static int part1(List<String> input, Map<Color, Integer> available) {
        List<Game> games = input.stream()
                .map(Game::from).collect(Collectors.toList());
/*        System.out.println(games);*/

        int sum = 0;
        for (Game game: games) {
            boolean allRoundsPossible = true;
            for (Round round: game.rounds) {
                if (!round.possibleWith(available)){
                    allRoundsPossible = false;
                }
            }
            if (allRoundsPossible) {
                sum += game.id;
            }
        }
        System.out.println(sum);
        return sum;
    }

    private static int part2(List<String> input) {
        List<Game> games = input.stream()
                .map(Game::from).collect(Collectors.toList());
        /*        System.out.println(games);*/

        List<Integer> powers = new ArrayList<>(games.size());
        for (Game game: games) {
            Map<Color, Integer> minSet = new HashMap<>();
            for (Round round: game.rounds) {
                for (Tuple<Color, Integer> color: round.colors) {
                    minSet.put(color.left(), Math.max(minSet.getOrDefault(color.left(), 0), color.right()));
                }
            }
            powers.add(minSet.values().stream().reduce(1, (a, b) -> a * b));
        }
        Integer sumPowers = powers.stream().reduce(0, Integer::sum);
        System.out.println("Part2: "+sumPowers);
        return sumPowers;
    }

    private static class Game {
        final int id;
        private final List<Round> rounds;

        private Game(int id, List<Round> rounds) {
            this.id = id;
            this.rounds = rounds;
        }

        public static Game from(String line) {
            String[] parts = line.trim().split(":");
            int id = Integer.parseInt(parts[0].split(" ")[1]);
            return new Game(id, Arrays.stream(parts[1].split(";"))
                    .map(Round::new)
                    .collect(Collectors.toList()));
        }
    }

    private static class Round {
        private final List<Tuple<Color, Integer>> colors;

        private Round(String line) {
            this.colors = Arrays.stream(line.trim().split(","))
                    .map(s -> s.trim().split(" "))
                    .map(s -> new Tuple<>(Color.from(s[1].trim()), Integer.parseInt(s[0].trim())))
                    .collect(Collectors.toList());
        }

        public boolean possibleWith(Map<Color, Integer> available) {
            for (Tuple<Color, Integer> round: colors) {
                Integer availableOfColor = available.get(round.left());
                if (availableOfColor == null || availableOfColor < round.right()) {
                    return false;
                }
            }
            return true;
        }
    }
}
