package com.gastonfournier.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PointTest {

    @Test
    void cellDistance() {
        assertThat(new Point(4, 4).manhattanDistance(new Point(5,5))).isEqualTo(1);
        assertThat(new Point(4, 4).manhattanDistance(new Point(5,4))).isEqualTo(1);
        assertThat(new Point(4, 4).manhattanDistance(new Point(5,3))).isEqualTo(1);
        assertThat(new Point(4, 4).manhattanDistance(new Point(4,5))).isEqualTo(1);
        assertThat(new Point(4, 4).manhattanDistance(new Point(4,4))).isEqualTo(0);
        assertThat(new Point(4, 4).manhattanDistance(new Point(4,3))).isEqualTo(1);
        assertThat(new Point(4, 4).manhattanDistance(new Point(3,5))).isEqualTo(1);
        assertThat(new Point(4, 4).manhattanDistance(new Point(3,4))).isEqualTo(1);
        assertThat(new Point(4, 4).manhattanDistance(new Point(3,3))).isEqualTo(1);
    }

}