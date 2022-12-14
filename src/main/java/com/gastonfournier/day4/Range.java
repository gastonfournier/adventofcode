package com.gastonfournier.day4;

public record Range(int from, int to) {
    public boolean includes(Range other) {
        return this.from <= other.from && this.to >= other.to;
    }

    public boolean overlaps(Range other) {
        return this.from <= other.from && other.from <= this.to
                || this.from <= other.to && other.to <= this.to;
    }
}
