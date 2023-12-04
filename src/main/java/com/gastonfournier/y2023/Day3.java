package com.gastonfournier.y2023;

import com.gastonfournier.utils.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day3 implements Solution<Integer, Integer> {
    @Override
    public Integer part1(List<String> input) {
        List<Integer> partNumbers = new ArrayList<>();
        for (int row = 0; row < input.size(); row++) {
            String line = input.get(row);
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                boolean hasAdjacentSymbol = false;
                Integer partNumber = null;
                while (Character.isDigit(c) && col < line.length()) {
                    hasAdjacentSymbol = hasAdjacentSymbol || findAdjacentSymbol(input, row, col);
                    if (partNumber == null) {
                        partNumber = 0;
                    }
                    partNumber = partNumber * 10 + Character.getNumericValue(c);
                    col++;
                    if (col < line.length()) {
                        c = line.charAt(col);
                    }
                }
                if (partNumber != null && hasAdjacentSymbol) {
                    //System.out.println(row + "," + col + " Part number " + partNumber + " found");
                    partNumbers.add(partNumber);
                }
            }

        }
        return partNumbers.stream().reduce(0, Integer::sum);
    }

    private boolean findAdjacentSymbol(List<String> input, int row, int col) {
        String currentLine = input.get(row);
        if (findSymbolInLine(col, currentLine)) return true;
        if (row > 0 && findSymbolInLine(col, input.get(row - 1))) return true;
        if (row < input.size() - 1 && findSymbolInLine(col, input.get(row + 1))) return true;
        return false;
    }

    /**
     * Finds on the column (when the same row is given) it will be a pointless check, but it favors simplicity of code
     */
    private boolean findSymbolInLine(int col, String line) {
        if (isSymbol(line.charAt(col))) {
            return true;
        }
        if (col > 0 && isSymbol(line.charAt(col - 1))) {
            return true;
        }
        if (col < line.length() - 1 && isSymbol(line.charAt(col + 1))) {
            return true;
        }
        return false;
    }

    private boolean isSymbol(char charAt) {
        return !Character.isDigit(charAt) && charAt != '.';
    }
/*
    List<Integer> gears = new ArrayList<>();
        for (int row = 0; row < input.size(); row++) {
        String line = input.get(row);
        for (int col = 0; col < line.length(); col++) {
            char c = line.charAt(col);
            Integer partNumber = null;
            Integer partNumber2 = null;
            while (Character.isDigit(c) && col < line.length()) {
                partNumber2 = partNumber2 != null? partNumber2 : findGear(input, row, col);
                if (partNumber == null) {
                    partNumber = 0;
                }
                partNumber = partNumber * 10 + Character.getNumericValue(c);
                col++;
                if (col < line.length()) {
                    c = line.charAt(col);
                }
            }
            if (partNumber != null && partNumber2 != null) {
                System.out.println(row + "," + col + " Part number " + partNumber + " found with " + partNumber2);
                if (gears.contains(partNumber*partNumber2)) {
                    System.out.println("Already found " + partNumber*partNumber2);
                    debug(input, row, col, 2);
                }
                gears.add(partNumber*partNumber2);
            }
        }

    }
        return gears.stream().reduce(0, Integer::sum);*/
    @Override
    public Integer part2(List<String> input) {
        List<Integer> gears = new ArrayList<>();
        for (int row = 0; row < input.size(); row++) {
            String line = input.get(row);
            for (int col = 0; col < line.length(); col++) {
                Gear gear = new Gear();
                char c = line.charAt(col);
                if (c == '*') {
                    debug(input, row, col, 1);
                    if (isDigit(input, row - 1, col - 1)){
                        gear.assignPart(buildNumber(input.get(row - 1), col - 1));
                        if (!isDigit(input, row - 1, col) && isDigit(input, row - 1, col + 1)) {
                            gear.assignPart(buildNumber(input.get(row - 1), col + 1));
                        }
                    } else if (isDigit(input, row - 1, col)){
                        gear.assignPart(buildNumber(input.get(row - 1), col));
                    } else if (isDigit(input, row - 1, col+1)){
                        gear.assignPart(buildNumber(input.get(row - 1), col+1));
                    }

                    if (isDigit(input, row, col - 1)){
                        gear.assignPart(buildNumber(input.get(row), col - 1));
                    }
                    if (isDigit(input, row, col + 1)){
                        gear.assignPart(buildNumber(input.get(row), col + 1));
                    }

                    if (isDigit(input, row + 1, col - 1)){
                        gear.assignPart(buildNumber(input.get(row + 1), col - 1));
                        if (!isDigit(input, row + 1, col) && isDigit(input, row + 1, col + 1)) {
                            gear.assignPart(buildNumber(input.get(row + 1), col + 1));
                        }
                    } else if (isDigit(input, row + 1, col)){
                        gear.assignPart(buildNumber(input.get(row + 1), col));
                    } else if (isDigit(input, row + 1, col + 1)){
                        gear.assignPart(buildNumber(input.get(row + 1), col + 1));
                    }
                }

                if (gear.isValid()) {
                    System.out.println(row + "," + col + " Part number " + gear.partNumber1 + " found with " + gear.partNumber2);
                    gears.add(gear.partNumber1*gear.partNumber2);
                }
            }

        }
        return gears.stream().reduce(0, Integer::sum);
    }

    private Integer buildNumber(String line, int col) {
        if (!Character.isDigit(line.charAt(col))) {
            throw new RuntimeException("Not a digit");
        }
        int startOfDigit = col;
        while(startOfDigit >= 0 && Character.isDigit(line.charAt(startOfDigit))) {
            startOfDigit --;
        }
        return buildNumberFrom(line, startOfDigit+1);
    }
    private boolean isDigit(List<String> input, int row, int col) {
        if (row >= 0 && row < input.size()) {
            String line = input.get(row);
            if (col >= 0 && col < line.length()) {
                return Character.isDigit(line.charAt(col));
            }
        }
        return false;
    }

    Integer findGear(List<String> input, int row, int col) {
        String currentLine = input.get(row);
        if (col < currentLine.length() - 1 && currentLine.charAt(col+1) == '*') {
            System.out.println("Found gear at → " + row + "," + col);
            return findOtherPart(input, row, col+1, Direction.RIGHT);
        }
        if (col > 0 && currentLine.charAt(col-1) == '*') {
            System.out.println("Found gear at ← " + row + "," + col);
            return findOtherPart(input, row, col - 1, Direction.LEFT);
        }
        if (col < currentLine.length() - 1 && row > 0 && input.get(row-1).charAt(col+1) == '*') {
            System.out.println("Found gear at ↗ " + row + "," + col);
            return findOtherPart(input, row - 1, col + 1, Direction.TOP_RIGHT);
        }
        if (row < input.size() - 1) {
            String nextLine = input.get(row + 1);
            if (col < currentLine.length() - 1 && nextLine.charAt(col) == '*') {
                System.out.println("Found gear at ↓ of " + row + "," + col);
                return findOtherPart(input, row + 1, col, Direction.DOWN);
            } else if (col > 0 && nextLine.charAt(col-1) == '*') {
                System.out.println("Found gear at ↙ of " + row + "," + col);
                return findOtherPart(input, row + 1, col-1, Direction.DOWN_LEFT);
            } else if (col < nextLine.length() - 1 && nextLine.charAt(col+1) == '*') {
                System.out.println("Found gear at ↘ of " + row + "," + col);
                Integer otherPart = findOtherPart(input, row + 1, col + 1, Direction.DOWN_RIGHT);
                if (otherPart == null && col + 2 < currentLine.length() && Character.isDigit(currentLine.charAt(col + 2))) {
                    System.out.println("\tSearching number at ↗ of *");
                    otherPart = buildNumberFrom(currentLine, col + 2);
                }
                return otherPart;
            }
        }
        return null;
    }

    /**
     * row x col is where * was found
     */
    private Integer findOtherPart(List<String> input, int row, int col, Direction foundAt) {
        Integer gearNumber = null;
        // find forward
        String currentRow = input.get(row);
        if (!foundAt.skipCurrentRow) {
            if (col < currentRow.length() - 1 && Character.isDigit(currentRow.charAt(col + 1))) {
                int startOfDigit = col + 1;
                while(startOfDigit >= 0 && Character.isDigit(currentRow.charAt(startOfDigit))) {
                    startOfDigit --;
                }
                gearNumber = buildNumberFrom(currentRow, startOfDigit+1);
            }
            if (gearNumber == null && foundAt.findBack) {
                if (col - 1 >= 0 && Character.isDigit(currentRow.charAt(col - 1))) {
                    int startOfDigit = col - 1;
                    while(startOfDigit >= 0 && Character.isDigit(currentRow.charAt(startOfDigit))) {
                        startOfDigit --;
                    }
                    gearNumber = buildNumberFrom(currentRow, startOfDigit+1);
                }
            }
        }
        if (gearNumber == null && row < input.size() - 1) {
            String nextRow = input.get(row + 1);
            for (int i = col + 1; i >= (foundAt == Direction.TOP_RIGHT? col + 1 : col-1); i--) {
                if (i >= 0 && i < nextRow.length() && Character.isDigit(nextRow.charAt(i))) {
                    while (i >= 0 && Character.isDigit(nextRow.charAt(i))) {
                        i --;
                    }
                    if (gearNumber == null) gearNumber = buildNumberFrom(nextRow, i+1);
                }
            }
        }
        if (gearNumber == null) {
            //System.out.println("Could not find other part of gear at " + row + "," + col);
            //debug(input, row, col, 1);
        }
        return gearNumber;
    }

    private static void debug(List<String> input, int row, int col, int cols) {
        int x = row - 1;
        int y = col - cols;
        boolean found = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= cols * 2; j++) {
                try {
                    char charAt = input.get(x + i).charAt(y + j);
                    if (charAt == '*') {
                        found = true;
                    }
                    sb.append(charAt);
                } catch (IndexOutOfBoundsException e) {
                }
            }
            if (sb.length() > 0) sb.append("\n");
        }
        if (found) System.out.println(sb+"\t--center "+row+"@"+col+"\n");
    }

    private static String ctx(List<String> input, int row, int col, int cols) {
        int x = row - 1;
        int y = col - cols;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= cols * 2; j++) {
                try {
                    char charAt = input.get(x + i).charAt(y + j);
                    sb.append(charAt);
                } catch (IndexOutOfBoundsException e) {
                }
            }
            if (sb.length() > 0) sb.append("\n");
        }
        return sb.toString();
    }

    private Integer buildNumberFrom(String line, int col) {
        Integer number = null;
        char c = line.charAt(col);
        while (Character.isDigit(c) && col < line.length()) {
            if (number == null) {
                number = 0;
            }
            number = number * 10 + Character.getNumericValue(c);
            col++;
            if (col < line.length()) {
                c = line.charAt(col);
            }
        }
        return number;
    }

    private enum Direction {
        RIGHT(false, false),
        LEFT(false, true),
        TOP_RIGHT(false, true),
        DOWN(true, false),
        DOWN_LEFT(true,false),
        DOWN_RIGHT(true, false);

        private final boolean findBack;
        private final boolean skipCurrentRow;

        Direction(boolean findBack, boolean skipCurrentRow) {
            this.findBack = findBack;
            this.skipCurrentRow = skipCurrentRow;
        }
    }

    private class Gear {
        Integer partNumber1;
        Integer partNumber2;
        void assignPart(Integer partNumber) {
            if (partNumber1 == null) {
                partNumber1 = partNumber;
            } else {
                if (partNumber2 != null) {
                    throw new RuntimeException("Already assigned both parts");
                }
                partNumber2 = partNumber;
            }
        }

        boolean isValid() {
            return partNumber1 != null && partNumber2 != null;
        }
    }
}
