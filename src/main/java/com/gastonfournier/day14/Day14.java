package com.gastonfournier.day14;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.MatrixWrapper;
import com.gastonfournier.utils.Point;
import com.gastonfournier.utils.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Day14 implements Solution<Integer, Integer> {

    public static final char EMPTY = '·';
    public static final char SAND = '+';
    public static final char ROCK = '#';
    public static final Point START = new Point(500, 0);

    public static void main(String[] args) {
        DailyChallenge challenge = new DailyChallenge(new Day14(), "day14.input.txt");
        challenge.run();
    }

    @Override
    public Integer part1(List<String> input) {
        MatrixWrapper matrixWrapper = parseInput(input);
        matrixWrapper.set(500, 0, SAND);
        matrixWrapper.print();

        int count = 0;
        Point sand = START;
        while (!intoVoid(sand, matrixWrapper)) {
            Point previous = null;
            while ((previous == null || !previous.equals(sand)) && !intoVoid(sand, matrixWrapper)) { // while not come to rest
                previous = sand;
                sand = fall(sand, matrixWrapper);
            }

            if (!intoVoid(sand, matrixWrapper)) {
                count ++; // previous came to rest
                sand = START; // new grain
            }
            System.out.println(count+" ==================================");
            matrixWrapper.print();
        }
        return count;
    }

    private boolean intoVoid(Point sand, MatrixWrapper matrixWrapper) {
        return sand.y() > matrixWrapper.maxY();
    }

    private Point fall(Point sand, MatrixWrapper matrixWrapper) {
        Integer newX = sand.x();
        Integer newY = sand.y() + 1;
        try {
            if (matrixWrapper.at(newX, newY) != EMPTY) {
                newX = sand.x() - 1;
            }
            if (matrixWrapper.at(newX, newY) != EMPTY) {
                newX = sand.x() + 1;
            }
            if (matrixWrapper.at(newX, newY) != EMPTY) {
                return sand;
            } else {
                matrixWrapper.set(sand.x(), sand.y(), EMPTY);
                matrixWrapper.set(newX, newY, SAND);
                return new Point(newX, newY);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return new Point(newX, newY); // I went out
        }
    }

    @Override
    public Integer part2(List<String> input) {
        MatrixWrapper matrixWrapper = parseInput2(input);
        drawLine(matrixWrapper, new Point(0, matrixWrapper.maxY()), new Point(matrixWrapper.maxX(), matrixWrapper.maxY()), ROCK);
        matrixWrapper.set(500, 0, SAND);
        matrixWrapper.print();

        int count = 0;
        Point sand = START;
        Point previous = null;
        while (!START.equals(sand) || !START.equals(previous)) {
            while ((previous == null || !previous.equals(sand))) { // while not come to rest
                previous = sand;
                sand = fall(sand, matrixWrapper);
            }

            System.out.println(count+"\t resting at " + sand);
            count ++; // previous came to rest
            sand = START; // new grain
            //matrixWrapper.print();
        }
        return count;
    }

    private MatrixWrapper parseInput2(List<String> input) {

        int maxX = 0, maxY = 0;

        List<List<Point>> lines = new ArrayList<>();
        for (String line : input) {
            List<Point> points = new ArrayList<>();
            lines.add(points);
            String[] strPoints = line.split(" -> ");
            for (String strPoint : strPoints) {
                String[] parts = strPoint.split(",");
                int x = parseInt(parts[0]);
                int y = parseInt(parts[1]);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
                points.add(new Point(x, y));
            }
        }
        MatrixWrapper matrix = new MatrixWrapper(maxX * 2 + 1 , maxY + 3, EMPTY);

        for (List<Point> line: lines){
            Point previous = null;
            for (Point p: line) {
                if (previous != null) {
                    drawLine(matrix, previous, p, ROCK);
                }
                previous = p;
            }
        }
        return matrix;
    }

    private MatrixWrapper parseInput(List<String> input) {

        int maxX = 0, maxY = 0;

        List<List<Point>> lines = new ArrayList<>();
        for (String line : input) {
            List<Point> points = new ArrayList<>();
            lines.add(points);
            String[] strPoints = line.split(" -> ");
            for (String strPoint : strPoints) {
                String[] parts = strPoint.split(",");
                int x = parseInt(parts[0]);
                int y = parseInt(parts[1]);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
                points.add(new Point(x, y));
            }
        }
        MatrixWrapper matrix = new MatrixWrapper(maxX + 1 , maxY + 1, EMPTY);

        for (List<Point> line: lines){
            Point previous = null;
            for (Point p: line) {
                if (previous != null) {
                    drawLine(matrix, previous, p, ROCK);
                }
                previous = p;
            }
        }
        return matrix;
    }

    private void drawLine(MatrixWrapper matrix, Point from, Point to, char c) {
        //System.out.println("Draw line from "+from+" to "+to+" into matrix of "+matrix.length+" rows and "+matrix[0].length+" columns");
        if (Objects.equals(from.y(), to.y())) {
            List<Integer> parts = Stream.of(from.x(), to.x()).sorted().toList();
            for (int i = parts.get(0); i <= parts.get(1); i++) {
                matrix.set(i, from.y(), c);
            }
        } else if (Objects.equals(from.x(), to.x())) {
            List<Integer> parts = Stream.of(from.y(), to.y()).sorted().toList();
            for (int i = parts.get(0); i <= parts.get(1); i++) {
                matrix.set(from.x(), i, c);
            }
        } else {
            throw new RuntimeException("Didn't expect this");
        }
    }

}
