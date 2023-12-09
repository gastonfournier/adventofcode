package com.gastonfournier.y2023.day7;

import java.util.Arrays;

public enum CardLabel {
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    T("T", 10),
    J("J", 11),
    Q("Q", 12),
    K("K", 13),
    A("A", 14);

    private final String cardLabel;
    private final int value;

    CardLabel(int i) {
        this.cardLabel = String.valueOf(i);
        this.value = i;
    }

    CardLabel(String cardLabel, int i) {
        this.cardLabel = cardLabel;
        this.value = i;
    }

    public static CardLabel fromString(String s) {
        return Arrays.stream(values()).filter(v -> v.cardLabel.equals(s)).findFirst().orElseThrow();
    }

    @Override
    public String toString() {
        return cardLabel;
    }
}
