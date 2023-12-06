package com.gastonfournier.y2023;

import java.util.*;
import java.util.stream.Stream;

public class Day5 implements com.gastonfournier.utils.Solution<Long, Long> {
    List<Map<Range, Long>> mappings = new ArrayList<>();

    List<Long> seeds = new ArrayList<>();

    List<Range> seedsRange = new ArrayList<>();

    @Override
    public Long part1(List<String> input) {
        processInput(input, true);
        long minLocation = Integer.MAX_VALUE;
        for (Long seed : seeds) {
            long current = seed;
            for (Map<Range, Long> mapping : mappings) {
                current = map(current, mapping);
            }
            minLocation = Math.min(minLocation, current);
        }
        return minLocation;
    }

    void processInput(List<String> input, boolean v1) {
        if (v1) {
            processSeedsV1(input);
        } else {
            processSeedsV2(input);
        }

        Map<Range, Long> current = null;
        for (int i = 1; i < input.size(); i++) {
            String line = input.get(i);
            if (!line.isBlank()) {
                if (line.contains("map:")) {
                    if (current != null) {
                        mappings.add(current);
                    }
                    current = new HashMap<>();
                } else {
                    current.putAll(processRangeLine(line));
                }
            }
        }
        if (current != null) {
            mappings.add(current);
        }
    }

    private void processSeedsV1(List<String> input) {
        String seedsInput = input.get(0);
        String[] seeds = seedsInput.split(" ");
        this.seeds = Arrays.stream(seeds).filter(s -> s.matches("\\d+")).map(Long::parseLong).toList();
    }

    private void processSeedsV2(List<String> input) {
        String seedsInput = input.get(0);
        String[] seeds = seedsInput.split(" ");
        List<Long> seedList = Arrays.stream(seeds).filter(s -> s.matches("\\d+")).map(Long::parseLong).toList();
        Long start = null;
        Long length = null;
        for (int i = 0; i < seedList.size(); i++) {
            if (start == null) {
                start = seedList.get(i);
            } else {
                length = seedList.get(i);
            }
            if (start != null && length != null) {
                this.seedsRange.add(new Range(start, start + length - 1));
                start = null;
                length = null;
            }
        }
    }


    @Override
    public Long part2(List<String> input) {
        processInput(input, false);
        long minLocation = Integer.MAX_VALUE;

        for (Range range: seedsRange) {
            // find minimum of this range not included in other ranges
            for (Map<Range, Long> layer: mappings) {
                for (Range otherRange: layer.keySet()) {
                    if (!otherRange.contains(range.from())) {
                        minLocation = Math.min(minLocation, range.from());
                    } else {
                        long potentialNewMin = range.contains(otherRange.to())? otherRange.to() + 1 : range.to();
                        minLocation = Math.min(minLocation, potentialNewMin);
                    }
                }
            }
        }

        for (Range seedRange : seedsRange) {
            System.out.println("Processing seed range " + seedRange.from() + " to " + seedRange.to() + " size "+ (seedRange.to() - seedRange.from()));
            long rangeMin = Long.MAX_VALUE;
            for (Map<Range, Long> mapping : mappings) {
                rangeMin = Math.min(rangeMin, findMin(seedRange, mapping));
            }
            minLocation = Math.min(minLocation, rangeMin);

        }
        return minLocation;
    }

    private long findMin(Range seedRange, Map<Range, Long> mapping) {
        long min = Long.MAX_VALUE;
        for (Range otherRange: mapping.keySet()) {
            min = seedRange.intersection(otherRange)
                    .map(intersection -> intersection.from() - otherRange.from())
                    .map(length -> mapping.get(otherRange) + length)
                    .orElse(min);
        }
        return min;
    }

    public Map<Range, Long> processRangeLine(String line) {
        String[] split = line.trim().split(" ");
        long destinationStart = Long.parseLong(split[0]);
        long sourceStart = Long.parseLong(split[1]);
        long rangeLength = Long.parseLong(split[2]) - 1;
        return Map.of(
                new Range(sourceStart, sourceStart + rangeLength),
                destinationStart
        );
    }

    public long map(long i, Map<Range, Long> rangeMap) {
        for (Range source : rangeMap.keySet()) {
            if (source.contains(i)) {
                Long destinationFrom = rangeMap.get(source);
                long position = i - source.from();
                return destinationFrom + position;
            }
        }
        return i;
    }
}
