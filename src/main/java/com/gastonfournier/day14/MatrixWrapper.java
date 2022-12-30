package com.gastonfournier.day14;

import java.util.Arrays;

public class MatrixWrapper {
    final char[][] matrix;
    private int minX;
    private int minY;

    public MatrixWrapper(int columns, int rows) {
        this.matrix = new char[rows][columns];
        this.minX = matrix[0].length;
        this.minY = matrix.length;
    }

    public MatrixWrapper(int columns, int rows, char initialValue) {
        this(columns, rows);
        this.fill(initialValue);
    }

    public void set(int x, int y, char c) {
        this.minX = Math.min(this.minX, x);
        this.minY = Math.min(this.minY, y);
        this.matrix[y][x] = c;
    }

    public char[][] matrix() {
        return this.matrix;
    }

    public int minX() {
        return this.minX;
    }

    public int minY() {
        return this.minY;
    }

    public Integer maxX() {
        return matrix[0].length - 1;
    }

    public int maxY() {
        return this.matrix.length - 1;
    }

    /**
     * This method fills the matrix with non-data, so minX and minY are not updated
     */
    private void fill(char c) {
        for (char[] row : matrix) {
            Arrays.fill(row, c);
        }
    }

    public void print() {
        for (int i = minY; i < matrix.length; i++) {
            System.out.print(i+": ");
            for (int j = minX; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.print("\n");
        }
    }

    public char at(Integer x, Integer y) {
        return matrix[y][x];
    }
}
