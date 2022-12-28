package com.gastonfournier.day13;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import com.gastonfournier.utils.Tuple;
import io.vavr.control.Either;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Day13 implements Solution<Integer, Integer> {

    public static void main(String[] args) {
        DailyChallenge challenge = new DailyChallenge(new Day13(), "day13.input.txt");
        challenge.run();
    }

    @Override
    public Integer part1(List<String> input) {
        List<Tuple<PacketData, PacketData>> packetData = processInput(input);
        int inOrder = 0;
        for (int i = 0; i < packetData.size(); i++) {
            Tuple<PacketData, PacketData> pair = packetData.get(i);
            if (pair.left().compareTo(pair.right()) < 0) {
                inOrder += (i + 1);
            }
        }
        return inOrder;
    }

    List<Tuple<PacketData, PacketData>> processInput(List<String> input) {
        List<Tuple<PacketData, PacketData>> packets = new ArrayList<>();
        Iterator<String> it = input.iterator();
        while (it.hasNext()) {
            String current = it.next();
            if (!current.isBlank()) {
                packets.add(new Tuple<>(
                    parsePacket(current),
                    parsePacket(it.next())
                ));
            }
        }
        return packets;
    }

    private PacketData parsePacket(String line) {
        StringCharacterIterator iterator = new StringCharacterIterator(line);
        char start = iterator.current();
        if (start == '[') {
            iterator.next();
            return parsePacket(iterator, new PacketData());
        } else {
            throw new RuntimeException("All lines with data should start with [");
        }
    }
    private PacketData parsePacket(StringCharacterIterator iterator, PacketData parent) {
        List<Either<Integer, PacketData>> data = new ArrayList<>();
        char currentChar = iterator.current();
        while (currentChar != CharacterIterator.DONE && currentChar != ']') {
            if (currentChar == '[') {
                iterator.next();
                data.add(Either.right(parsePacket(iterator, new PacketData())));
            } else if (Character.isDigit(currentChar)) {
                StringBuilder sb = new StringBuilder("");
                while (Character.isDigit(currentChar)) {
                    sb.append(currentChar);
                    currentChar = iterator.next();
                }
                currentChar = iterator.previous();
                data.add(Either.left(parseInt(sb.toString())));
            }
            currentChar = iterator.next();
        }

        parent.setData(data);
        return parent;
    }

    @Override
    public Integer part2(List<String> input) {
        List<Tuple<PacketData, PacketData>> packetData = processInput(input);
        PacketData needle1 = parsePacket("[[2]]");
        PacketData needle2 = parsePacket("[[6]]");
        packetData.add(new Tuple<>(needle1, needle2));

        List<PacketData> all = packetData.stream().map(t -> List.of(t.left(), t.right())).reduce(this::mergeLists).orElseThrow();
        all.sort(PacketData::compareTo);
        return (all.indexOf(needle1) + 1) * (all.indexOf(needle2) + 1);
    }

    private List<PacketData> mergeLists(List<PacketData> l1, List<PacketData> l2) {
        List<PacketData> merged = new ArrayList<>();
        merged.addAll(l1);
        merged.addAll(l2);
        return merged;
    }

    class PacketData implements Comparable<PacketData> {
        List<Either<Integer, PacketData>> data = new ArrayList<>();

        public void setData(List<Either<Integer, PacketData>> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "["+data.stream().map(p -> p.isLeft()? p.getLeft().toString() : p.get().toString())
                    .collect(Collectors.joining(","))+"]";
        }

        @Override
        public int compareTo(PacketData other) {
            int min = Math.min(data.size(), other.data.size());
            for (int i = 0; i < min; i++) {

                var thisPacket = data.get(i);
                var otherPacket = other.data.get(i);

                if (thisPacket.isLeft() && otherPacket.isLeft()) {
                    int compareValues = Integer.compare(thisPacket.getLeft(), otherPacket.getLeft());
                    if (compareValues != 0) {
                        return compareValues;
                    } // else continue
                } else if (thisPacket.isRight() && otherPacket.isRight()) {
                    int innerComparison = thisPacket.get().compareTo(otherPacket.get());
                    if (innerComparison != 0) {
                        return innerComparison;
                    } // else continue
                } else {
                    PacketData toList = new PacketData();
                    int innerComparison;
                    if (thisPacket.isLeft()) {
                        toList.data.add(Either.left(thisPacket.getLeft()));
                        innerComparison = toList.compareTo(otherPacket.get());
                    } else if (otherPacket.isLeft()) {
                        toList.data.add(Either.left(otherPacket.getLeft()));
                        innerComparison = thisPacket.get().compareTo(toList);
                    } else {
                        throw new RuntimeException("Invalid situation");
                    }
                    if (innerComparison != 0) {
                        return innerComparison;
                    } // else continue
                }
            }
            return Integer.compare(data.size(), other.data.size()); // if the first list run out of items first, they're in order
        }
    }
}
