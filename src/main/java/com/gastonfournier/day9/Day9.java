package com.gastonfournier.day9;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Point;
import com.gastonfournier.utils.Solution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Day9 implements Solution<Integer, Integer> {

    public static void main(String[] args) {
        DailyChallenge challenge = new DailyChallenge(new Day9(), "day9.input.txt");
        challenge.run();
    }

    @Override
    public Integer part1(List<String> input) {
        var visited = new HashSet<Point>();
        Point head = new Point(0, 0);
        Point tail = new Point(0, 0);
        visited.add(tail);
        for (String line : input) {
            Motion motion = new Motion(line);
            while (motion.hasMotion()) {
                head = motion.move1(head);
                if (head.manhattanDistance(tail) > 1) {
                    tail = moveTowards(tail, head);
                    visited.add(tail);
                }
            }
        }
        return visited.size();
    }

    Point moveTowards(Point tail, Point head) {
        Integer newX = oneStepCloser(tail.x(), head.x());
        Integer newY = oneStepCloser(tail.y(), head.y());
        return new Point(newX, newY);
    }

    private Integer oneStepCloser(Integer v1, Integer v2) {
        if (!v1.equals(v2)) {
            int step = (v2 - v1) / Math.abs(v2 - v1);
            return v1 + step;
        }
        return v1;
    }


    @Override
    public Integer part2(List<String> input) {
        var visited = new HashSet<Point>();
        var rope = new LinkedList<Point>();
        for (int i = 0; i < 10; i++) {
            rope.add(new Point(0, 0));
        }
        visited.add(rope.getLast());
        for (String line : input) {
            Motion motion = new Motion(line);
            while (motion.hasMotion()) {
                Point head = motion.move1(rope.getFirst());
                rope.set(0, head);
                Point follow = head;
                //System.out.printf("Head at (%s, %s)%n", head.x(), head.y());
                for (int i = 1; i < rope.size(); i++) {
                    Point knot = rope.get(i);
                    if (follow.manhattanDistance(knot) > 1) {
                        knot = moveTowards(knot, follow);
                        rope.set(i, knot); // replace know it rope with new position
                        //System.out.printf("\tTail %s moved to (%s, %s)%n", i, knot.x(), knot.y());
                    }
                    follow = knot; // move follow to the next one
                }
                visited.add(rope.getLast());
            }
        }
        return visited.size();
    }

    private enum Direction {
        RIGHT('R'), UP('U'), LEFT('L'), DOWN('D');

        private final char encoded;

        Direction(char encoded) {
            this.encoded = encoded;
        }

        public static Direction from(char encoded) {
            return Arrays.stream(values()).filter(d -> d.encoded == encoded).findFirst().orElseThrow();
        }

        public Integer applyX(Integer x) {
            if (this == LEFT) {
                return x - 1;
            } else if (this == RIGHT) {
                return x + 1;
            }
            return x;
        }

        public Integer applyY(Integer y) {
            if (this == DOWN) {
                return y - 1;
            } else if (this == UP) {
                return y + 1;
            }
            return y;
        }
    }

    private class Motion {
        private Direction direction;
        private int count;

        public Motion(String line) {
            String[] parts = line.split(" ");
            this.direction = Direction.from(parts[0].charAt(0));
            this.count = Integer.parseInt(parts[1]);
        }

        public Point move1(Point p) {
            if (count == 0) {
                return p;
            }
            this.count--;
            return new Point(direction.applyX(p.x()), direction.applyY(p.y()));
        }

        public boolean hasMotion() {
            return this.count > 0;
        }
    }
}
