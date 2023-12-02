package com.gastonfournier.day12;


import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import com.gastonfournier.utils.Tuple;
import java.util.*;

public class Day12 implements Solution<Integer, Integer> {

  public static void main(String[] args) {
    DailyChallenge challenge = new DailyChallenge(new Day12(), "day12.input.txt");
    challenge.run();
  }

  @Override
  public Integer part1(List<String> input) {
    Tuple<Integer, Integer> origin = null;
    Tuple<Integer, Integer> destination = null;
    int height = input.size();
    int width = input.get(0).length();
    char[][] matrix = new char[height][width];
    for (int i = 0; i < height; i++) {
      // matrix[i] = input.get(i).toCharArray();
      String line = input.get(i);
      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);
        if (c == 'S') {
          origin = new Tuple<>(i, j);
          c = 'a';
        } else if (c == 'E') {
          destination = new Tuple<>(i, j);
          c = 'z';
        }
        matrix[i][j] = c;
      }
    }
    if (origin == null || destination == null) {
      throw new RuntimeException();
    }
    return dijkstra(origin, destination, matrix);
  }

  private int dijkstra(
      Tuple<Integer, Integer> origin, Tuple<Integer, Integer> destination, char[][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    int[] distances = new int[height * width];
    Arrays.fill(distances, Integer.MAX_VALUE);

    // Stack<Integer> stack = new Stack<>();
    var shortestDistanceFirst =
        new PriorityQueue<Integer>(
            (o1, o2) ->
                distances[o1] != distances[o2]
                    ? Integer.compare(distances[o1], distances[o2])
                    : o1.compareTo(o2));
    boolean[] isVisited = new boolean[height * width];
    int start = listPos(origin, width);
    distances[start] = 0;
    shortestDistanceFirst.add(start);
    while (!shortestDistanceFirst.isEmpty()) {
      int current = shortestDistanceFirst.poll();
      if (!isVisited[current] && !isAt(current, destination, width)) {
        // visit current
        isVisited[current] = true;
        for (int i : adjacents(current, matrix)) {
          if (!isVisited[i]) {
            // Note 1 is the cost from current to i
            distances[i] = Math.min(distances[i], distances[current] + 1);
            shortestDistanceFirst.add(i);
          }
        }
      }
      // System.out.println(asMatrixString(distances, width));
      // System.out.println(shortestDistanceFirst);
    }
    if (distances[listPos(destination, width)] == Integer.MAX_VALUE) {
      throw new RuntimeException("Path not found");
    }
    return distances[listPos(destination, width)];
  }

  private String asMatrixString(int[] distances, int width) {
    var sb = new StringBuilder("");
    for (int i = 0; i < distances.length; i++) {
      sb.append(distances[i]).append(",");
      if ((i + 1) % width == 0) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  private int dfs(
      Tuple<Integer, Integer> origin, Tuple<Integer, Integer> destination, char[][] matrix) {
    int height = matrix.length;
    int width = matrix[0].length;
    // DFS height * width as list, where the position x, y appears at x * width + y in the list
    int steps = 0;
    Stack<Integer> stack = new Stack<>();
    boolean[] isVisited = new boolean[height * width];
    int start = listPos(origin, width);
    stack.push(start);
    while (!stack.isEmpty()) {
      int current = stack.pop();
      if (!isVisited[current]) {
        isVisited[current] = true;
        // visit current
        steps++; // TODO when doing backtracking I should reduce steps
        if (isAt(current, destination, width)) {
          return steps;
        }
        for (int i : adjacents(current, matrix)) {
          if (!isVisited[i]) {
            stack.push(i);
          }
        }
      }
    }
    return steps;
  }

  private Integer[] adjacents(int current, char[][] matrix) {
    List<Integer> adjacents = new ArrayList<>(4);
    int width = matrix[0].length;
    Tuple<Integer, Integer> pos = asTuple(current, width);
    char currentHeight = matrix[pos.left()][pos.right()];
    tryPath(matrix, currentHeight, pos.left() - 1, pos.right()).ifPresent(adjacents::add);
    tryPath(matrix, currentHeight, pos.left() + 1, pos.right()).ifPresent(adjacents::add);
    tryPath(matrix, currentHeight, pos.left(), pos.right() - 1).ifPresent(adjacents::add);
    tryPath(matrix, currentHeight, pos.left(), pos.right() + 1).ifPresent(adjacents::add);
    return adjacents.toArray(new Integer[0]);
  }

  private Optional<Integer> tryPath(char[][] matrix, char currentHeight, int x, int y) {
    int width = matrix[0].length;
    if (x >= 0 && x < matrix.length && y >= 0 && y < width && matrix[x][y] <= currentHeight + 1) {
      return Optional.of(listPos(x, y, width));
    }
    return Optional.empty();
  }

  private boolean isAt(int current, Tuple<Integer, Integer> destination, Integer width) {
    return destination.equals(asTuple(current, width));
  }

  Tuple<Integer, Integer> asTuple(int listPos, Integer width) {
    return new Tuple<>(listPos / width, listPos % width);
  }

  int listPos(Tuple<Integer, Integer> point, Integer width) {
    return listPos(point.left(), point.right(), width);
  }

  private int listPos(Integer x, Integer y, Integer width) {
    return x * width + y;
  }

  @Override
  public Integer part2(List<String> input) {
    List<Tuple<Integer, Integer>> origins = new ArrayList<>();
    Tuple<Integer, Integer> destination = null;
    int height = input.size();
    int width = input.get(0).length();
    char[][] matrix = new char[height][width];
    for (int i = 0; i < height; i++) {
      // matrix[i] = input.get(i).toCharArray();
      String line = input.get(i);
      for (int j = 0; j < line.length(); j++) {
        char c = line.charAt(j);
        if (c == 'S') {
          origins.add(new Tuple<>(i, j));
          c = 'a';
        } else if (c == 'E') {
          destination = new Tuple<>(i, j);
          c = 'z';
        } else if (c == 'a') {
          origins.add(new Tuple<>(i, j));
        }
        matrix[i][j] = c;
      }
    }
    if (origins.isEmpty() || destination == null) {
      throw new RuntimeException();
    }
    int min = Integer.MAX_VALUE;
    for (Tuple<Integer, Integer> start : origins) {
      try {
        min = Math.min(min, dijkstra(start, destination, matrix));
      } catch (RuntimeException e) {
        System.out.println("Failed from " + start + ". " + e.getMessage());
      }
    }
    return min;
  }
}
