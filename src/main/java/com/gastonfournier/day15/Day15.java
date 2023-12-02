package com.gastonfournier.day15;


import com.gastonfournier.utils.*;
import io.vavr.Tuple2;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 implements Solution<Integer, Long> {
  private static final Pattern pattern =
      Pattern.compile(
          "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
  private final int part1Row;
  private final int part2limit;

  public Day15(int part1Row, int part2limit) {
    this.part1Row = part1Row;
    this.part2limit = part2limit;
  }

  public static void main(String[] args) {
    DailyChallenge challenge = new DailyChallenge(new Day15(2000000, 4000000), "day15.input.txt");
    challenge.run();
  }

  @Override
  public Integer part1(List<String> input) {
    Tuple2<SparseMatrix<Character>, List<Tuple2<Point, Point>>> t = processInput(input);
    SparseMatrix<Character> matrix = t._1;
    List<Tuple2<Point, Point>> sensors = t._2;
    Set<Integer> xNoBeacon = new HashSet<>();
    for (Tuple2<Point, Point> tuple : sensors) {
      int distance = tuple._1().manhattanDistance(tuple._2());
      for (int y = tuple._1.y() - distance; y <= tuple._1.y() + distance; y++) {
        if (part1Row == y) {
          for (int x = tuple._1.x() - distance; x <= tuple._1.x() + distance; x++) {
            Point p = new Point(x, y);
            if (p.manhattanDistance(tuple._1) <= distance && matrix.at(p.x(), p.y()) == '·') {
              xNoBeacon.add(x);
            }
          }
        }
      }
    }
    return xNoBeacon.size();
  }

  @Override
  public Long part2(List<String> input) {
    Tuple2<SparseMatrix<Character>, List<Tuple2<Point, Point>>> puzzleInput = processInput(input);
    List<Tuple2<Point, Point>> sensors = puzzleInput._2;
    List<Point> beacons = sensors.stream().map(pair -> pair._2).toList();
    beacons.forEach(
        beacon -> {
          System.out.println(beacon + " distress signal " + distress(beacon));
        });

    List<Tuple2<Point, Integer>> sensorsAndDistances =
        sensors.stream()
            .map(tup -> new Tuple2<>(tup._1, tup._1.manhattanDistance(tup._2)))
            .toList();
    for (int x = 0; x < part2limit; x++) {
      var y = 0;
      while (y <= part2limit) {
        Point current = new Point(x, y);
        Optional<Tuple2<Point, Integer>> closerSensorWithDistance =
            sensorsAndDistances.stream()
                .filter(tup -> tup._1.manhattanDistance(current) <= tup._2)
                .findAny();
        if (closerSensorWithDistance.isEmpty()) {
          return distress(current);
        }
        // this gives the amount of squares required to exit the radius of the sensor
        int yDistanceToSensor = closerSensorWithDistance.get()._1.y() - y;
        if (yDistanceToSensor > 0) {
          y += yDistanceToSensor * 2 + 1;
        } else if (yDistanceToSensor < 0) {
          y +=
              closerSensorWithDistance.get()._2
                  - current.manhattanDistance(closerSensorWithDistance.get()._1)
                  + 1;
        } else {
          y++;
        }
      }
    }
    return 1L;
  }

  private Long distress(Point beacon) {
    return beacon.x() * 4000000l + beacon.y();
  }

  private Tuple2<SparseMatrix<Character>, List<Tuple2<Point, Point>>> processInput(
      List<String> input) {

    List<Tuple2<Point, Point>> sensors = new ArrayList<>();
    for (String line : input) {
      Matcher m = pattern.matcher(line);
      if (m.matches()) {
        Point sensor = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        Point beacon = new Point(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
        sensors.add(new Tuple2<>(sensor, beacon));
      }
    }

    // instead of making a matrix that grows, I'm predicting what the max matrix can be and
    // allocating accordingly
    SparseMatrix<Character> matrix = new SparseMatrix<>('·');
    for (Tuple2<Point, Point> tuple : sensors) {
      Point sensor = tuple._1();
      matrix.set(sensor.x(), sensor.y(), 'S');
      Point beacon = tuple._2();
      matrix.set(beacon.x(), beacon.y(), 'B');
    }

    /*for (Tuple2<Point, Point> tuple: sensors) {
        int distance = tuple._1().manhattanDistance(tuple._2());
        for (int y = tuple._1.y() - distance; y <= tuple._1.y() + distance; y++) {
            for (int x = tuple._1.x() - distance; x <= tuple._1.x() + distance; x++) {
                Point p = new Point(x, y);
                if (p.manhattanDistance(tuple._1) <= distance && matrix.at(p.x(), p.y()) == '·') {
                    matrix.set(p.x(), p.y(), '#');
                }
            }
        }
    }*/
    // matrix.print();
    return new Tuple2<>(matrix, sensors);
  }
}
