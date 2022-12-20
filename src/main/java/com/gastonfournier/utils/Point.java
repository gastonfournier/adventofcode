package com.gastonfournier.utils;

public record Point(Integer x, Integer y) {
    public int cellDistance(Point other) {
        return Math.max(
          Math.abs(x - other.x),
          Math.abs(y - other.y)
        );
    }
}
