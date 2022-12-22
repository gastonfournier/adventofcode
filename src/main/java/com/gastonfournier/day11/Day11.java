package com.gastonfournier.day11;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import com.gastonfournier.utils.Tuple;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gastonfournier.utils.Timer.time;

public class Day11 implements Solution<BigInteger, BigInteger> {

    private static Pattern decisionPattern = Pattern.compile("\s*If (?:true|false): throw to monkey (\\d+)");
    private static Pattern divisibleByPattern = Pattern.compile("\s*Test: divisible by (\\d+)");

    private static Pattern binaryOperationPattern = Pattern.compile("\s*Operation: new = (\\d+|old)\s(\\+|\\*)\s(\\d+|old)");

    public static void main(String[] args) {
        DailyChallenge challenge = new DailyChallenge(new Day11(), "day11.input.txt");
        challenge.run();
    }

    @Override
    public BigInteger part1(List<String> input) {
        int rounds = 20;
        Function<BigInteger, BigInteger> reliefFn = (BigInteger w) -> w.divide(BigInteger.valueOf(3));

        return playWithMonkeys(input, rounds, reliefFn);
    }

    private BigInteger playWithMonkeys(List<String> input, int rounds, Function<BigInteger, BigInteger> reliefFn) {
        Tuple<List<Monkey>, Integer> model = model(input);
        List<Monkey> monkeys = model.left();
        int commonDivisor = model.right();
        Map<Monkey, Integer> inspections = new HashMap<>();
        for (Monkey m : monkeys) {
            inspections.put(m, 0);
        }
        for (int i = 0; i < rounds; i++) {
            for (Monkey m: monkeys) {
                List<Tuple<Item, Integer>> threwAwayItems = m.turn(reliefFn);
                inspections.put(m, inspections.get(m) + threwAwayItems.size());
                for (Tuple<Item, Integer> itemPair: threwAwayItems) {
                    Item item = itemPair.left();
                    item.worryLevel = item.worryLevel.mod(BigInteger.valueOf(commonDivisor));
                    monkeys.get(itemPair.right()).receive(item);
                }
                System.out.println("Monkeys "+ Arrays.toString(monkeys.toArray()));
            }
            //System.out.println("Items after round " + (i+1) + ": "+ monkeys.stream().map(m -> m.items.size()).reduce(Math::addExact).get());
        }

        List<Integer> sorted = inspections.values().stream().sorted().toList();
        System.out.println(inspections.entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue()).toList());
        return BigInteger.valueOf(sorted.get(sorted.size() - 1)).multiply(BigInteger.valueOf(sorted.get(sorted.size() - 2)));
    }

    @Override
    public BigInteger part2(List<String> input) {
        int rounds = 10000;
        return playWithMonkeys(input, rounds, Function.identity());
    }

    Tuple<List<Monkey>, Integer> model(List<String> input) {

        List<Monkey> results = new ArrayList<>();
        Iterator<String> it = input.iterator();
        int commonDivisor = 1;
        int count = 0;
        while (it.hasNext()) {
            String line = it.next();
            if (line.trim().startsWith("Monkey")) {
                var items = parseStartingItems(it.next());
                var operation = parseOperation(it.next());
                Integer divisor = findInt(it.next(), divisibleByPattern);
                commonDivisor = commonDivisor * divisor;
                Function<BigInteger, Boolean> test = (BigInteger n) -> n.mod(BigInteger.valueOf(divisor)).equals(BigInteger.ZERO);
                var destinationWhenTrue = findInt(it.next(), decisionPattern);
                var destinationWhenFalse = findInt(it.next(), decisionPattern);

                results.add(new Monkey(
                        "Monkey-"+(count++),
                        items,
                        operation,
                        test,
                        destinationWhenTrue,
                        destinationWhenFalse
                ));
            }
        }
        return new Tuple<>(results, commonDivisor);
    }

    Integer findInt(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new RuntimeException("Invalid find int line: "+line+" for pattern: "+pattern);
        }
        return Integer.parseInt(matcher.group(1));
    }

    Function<BigInteger, BigInteger> parseOperation(String line) {
        Matcher matcher = binaryOperationPattern.matcher(line);
        if (!matcher.find()) {
            throw new RuntimeException("Invalid binary operation: "+line);
        }
        String operandA = matcher.group(1);
        String operator = matcher.group(2);
        String operandB = matcher.group(3);
        BinaryOperator<BigInteger> operation = parseOperator(operator);
        Function<BigInteger, BigInteger> old = Function.identity();
        Function<BigInteger, BigInteger> first = operandA.equals("old") ? old : just(operandA);
        Function<BigInteger, BigInteger> second = operandB.equals("old") ? old : just(operandB);
        return (BigInteger n) -> operation.apply(first.apply(n), second.apply(n));
    }

    private Function<BigInteger, BigInteger> just(String operand) {
        BigInteger newValue = new BigInteger(operand);
        return (BigInteger oldValue) -> newValue;
    }

    private BinaryOperator<BigInteger> parseOperator(String operator) {
        if (operator.equals("*")) {
            return BigInteger::multiply;
        }
        if (operator.equals("+")) {
            return BigInteger::add;
        }
        throw new RuntimeException("Unknown operator: "+operator);
    }

    private List<Item> parseStartingItems(String line) {
        return Arrays.stream(
                    line.replaceFirst("\s*Starting items: ", "")
                    .replaceAll("\s*,\s*", ",")
                    .split(",")
                )
                .map(BigInteger::new)
                .map(Item::new)
                .toList();
    }

    static class Item {
        BigInteger worryLevel;

        public Item(BigInteger worry) {
            this.worryLevel = worry;
        }

        @Override
        public String toString() {
            return this.worryLevel.toString();
        }
    }

    class Monkey {
        final String name;
        final List<Item> items = new CopyOnWriteArrayList<>();
        final Function<BigInteger, BigInteger> operation;
        final Function<BigInteger, Boolean> test;

        final int destinationWhenTrue;
        final int destinationWhenFalse;

        Monkey(String name, List<Item> items, Function<BigInteger, BigInteger> operation, Function<BigInteger, Boolean> test, int destinationWhenTrue, int destinationWhenFalse) {
            this.name = name;
            this.items.addAll(items);
            this.operation = operation;
            this.test = test;
            this.destinationWhenTrue = destinationWhenTrue;
            this.destinationWhenFalse = destinationWhenFalse;
        }

        void receive(Item item) {
            this.items.add(item);
        }

        List<Tuple<Item, Integer>> turn(Function<BigInteger, BigInteger> reliefFn) {
            List<Tuple<Item, Integer>> thrownItems = new ArrayList<>();
            Collections.unmodifiableList(this.items).forEach(item -> {
                item.worryLevel = this.operation.apply(item.worryLevel);
                item.worryLevel = reliefFn.apply(item.worryLevel);
                // inspect item
                if (time(() -> test.apply(item.worryLevel), 100, "testing ")) {
                    thrownItems.add(new Tuple<>(item, destinationWhenTrue));
                } else {
                    thrownItems.add(new Tuple<>(item, destinationWhenFalse));
                }
                this.items.remove(item);
            });
            return thrownItems;
        }

        @Override
        public String toString() {
            return "(" + name +
                    " " + items +
                    ')';
        }
    }
}
