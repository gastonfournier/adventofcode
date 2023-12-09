package com.gastonfournier.y2023.day7;

public enum HandType {
    HighCard(1),
    OnePair(2),
    TwoPair(3),
    ThreeOfAKind(4),
    FullHouse(5),
    FourOfAKind(6),
    FiveOfAKind(7);

    private final int strength;

    HandType(int strength) {
        this.strength = strength;
    }
}
