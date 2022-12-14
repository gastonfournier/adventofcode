package com.gastonfournier.day4;

import java.util.List;

import static com.gastonfournier.utils.Reader.readFileAsLines;

public class Day4 {
    public static void main(String[] args) {
        List<String> input = readFileAsLines("day4.input.txt");
        System.out.println(new Day4().part1(input));
        System.out.println(new Day4().part2(input));
    }

    public int part1(List<String> input) {
        int count = 0;
        for (String pairCsv: input) {
            String[] elves = pairCsv.split(",");
            Range elf1 = this.decodeRange(elves[0]);
            Range elf2 = this.decodeRange(elves[1]);
            if (elf1.includes(elf2) || elf2.includes(elf1)) {
                count ++;
            }
        }
        return count;
    }

    private Range decodeRange(String elf) {
        String[] parts = elf.split("-");
        return new Range(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public int part2(List<String> input) {
        int count = 0;
        for (String pairCsv: input) {
            String[] elves = pairCsv.split(",");
            Range elf1 = this.decodeRange(elves[0]);
            Range elf2 = this.decodeRange(elves[1]);
            if (elf1.overlaps(elf2) || elf2.overlaps(elf1)) {
                count ++;
            }
        }
        return count;
    }
}
