package com.gastonfournier.day8;


import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;
import java.util.List;
import java.util.function.IntFunction;

public class Day8 implements Solution<Integer, Integer> {

  public static void main(String[] args) {
    DailyChallenge challenge = new DailyChallenge(new Day8(), "day8.input.txt");
    challenge.run();
  }

  private static int[][] buildMatrix(List<String> input) {
    int[][] matrix = new int[input.size()][input.get(0).length()];
    for (int i = 0; i < input.size(); i++) {
      String[] parts = input.get(i).split("");
      for (int j = 0; j < parts.length; j++) {
        matrix[i][j] = Integer.parseInt(parts[j]);
      }
    }
    return matrix;
  }

  @Override
  public Integer part1(List<String> input) {
    int[][] matrix = buildMatrix(input);

    int innerVisibleTrees = 0;
    IntFunction<Integer> upOrLeft = (int i) -> i - 1;
    IntFunction<Integer> bottomOrRight = (int i) -> i + 1;
    IntFunction<Integer> noMove = (int i) -> i;

    for (int i = 1; i < matrix.length - 1; i++) {
      for (int j = 1; j < matrix[i].length - 1; j++) {
        if (isVisible(matrix, matrix[i][j], upOrLeft.apply(i), j, upOrLeft, noMove)
            || isVisible(matrix, matrix[i][j], i, upOrLeft.apply(j), noMove, upOrLeft)
            || isVisible(matrix, matrix[i][j], bottomOrRight.apply(i), j, bottomOrRight, noMove)
            || isVisible(matrix, matrix[i][j], i, bottomOrRight.apply(j), noMove, bottomOrRight)) {
          // System.out.println("Tree at "+ i + ","+j+" of height "+matrix[i][j]+" is visible");
          innerVisibleTrees++;
        } else {
          // System.out.println("Tree at "+ i + ","+j+" of height "+matrix[i][j]+" is NOT visible");
        }
      }
    }

    // System.out.println(innerVisibleTrees);
    // count width and height but reduce 4 for the trees on the corners that get counted twice
    return input.size() * 2 + input.get(0).length() * 2 - 4 + innerVisibleTrees;
  }

  private boolean isVisible(
      int[][] matrix,
      int checkedHeight,
      int x,
      int y,
      IntFunction<Integer> lateralMove,
      IntFunction<Integer> verticalMove) {
    // if current point inside matrix
    if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[x].length) {
      return checkedHeight > matrix[x][y]
          && isVisible(
              matrix,
              checkedHeight,
              lateralMove.apply(x),
              verticalMove.apply(y),
              lateralMove,
              verticalMove);
    } else {
      return true; // I made it out of the matrix so this one is visible
    }
  }

  private int scenicScore(
      int[][] matrix,
      int checkedHeight,
      int x,
      int y,
      IntFunction<Integer> lateralMove,
      IntFunction<Integer> verticalMove) {
    // if current point inside matrix
    if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[x].length) {
      if (checkedHeight > matrix[x][y]) {
        return 1
            + scenicScore(
                matrix,
                checkedHeight,
                lateralMove.apply(x),
                verticalMove.apply(y),
                lateralMove,
                verticalMove);
      } else {
        return 1;
      }
    } else {
      return 0; // I made it out of the matrix so this one is visible
    }
  }

  @Override
  public Integer part2(List input) {
    int[][] matrix = buildMatrix(input);

    IntFunction<Integer> upOrLeft = (int i) -> i - 1;
    IntFunction<Integer> bottomOrRight = (int i) -> i + 1;
    IntFunction<Integer> noMove = (int i) -> i;

    int highestScenicScore = 0;
    for (int i = 1; i < matrix.length - 1; i++) {
      for (int j = 1; j < matrix[i].length - 1; j++) {
        int scenicScore =
            scenicScore(matrix, matrix[i][j], upOrLeft.apply(i), j, upOrLeft, noMove)
                * scenicScore(matrix, matrix[i][j], i, upOrLeft.apply(j), noMove, upOrLeft)
                * scenicScore(
                    matrix, matrix[i][j], bottomOrRight.apply(i), j, bottomOrRight, noMove)
                * scenicScore(
                    matrix, matrix[i][j], i, bottomOrRight.apply(j), noMove, bottomOrRight);
        if (scenicScore > highestScenicScore) {
          // System.out.println("Tree at "+ i + ","+j+" of height "+matrix[i][j]+" is visible");
          highestScenicScore = scenicScore;
        }
      }
    }
    return highestScenicScore;
  }
}
