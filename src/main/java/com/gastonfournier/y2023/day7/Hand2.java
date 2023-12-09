package com.gastonfournier.y2023.day7;

import java.util.*;

public class Hand2 implements Comparable<Hand2> {
    private final CardLabel[] cards;
    public final HandType handType;
    public final long bid;

    public Hand2(String cards, long bid) {
        this.cards = Arrays.stream(cards.split("")).map(CardLabel::fromString).toArray(CardLabel[]::new);
        this.bid = bid;
        Map<CardLabel, Integer> cardCount = new HashMap<>();
        for (CardLabel cardLabel: this.cards) {
            cardCount.put(cardLabel, cardCount.getOrDefault(cardLabel, 0) + 1);
        }
        int jays = cardCount.getOrDefault(CardLabel.J, 0);
        cardCount.remove(CardLabel.J);
        Collection<Integer> values = cardCount.values();
        List<Integer> valueList = new ArrayList<>(values);
        Collections.sort(valueList);
        if (valueList.equals(List.of(5)) || valueList.equals(List.of(5 - jays))) {
            this.handType = HandType.FiveOfAKind;
        } else if (valueList.equals(List.of(1, 4)) || valueList.equals(List.of(1, 4 - jays))) {
            this.handType = HandType.FourOfAKind;
        } else if (valueList.equals(List.of(2, 3)) || valueList.equals(List.of(2, 3 - jays))) {
            this.handType = HandType.FullHouse;
        } else if (valueList.equals(List.of(1, 1, 3)) || valueList.equals(List.of(1, 1, 3 - jays))) {
            this.handType = HandType.ThreeOfAKind;
        } else if (valueList.equals(List.of(1, 2, 2)) || valueList.equals(List.of(1, 2 - jays, 2))) {
            this.handType = HandType.TwoPair;
        } else if (valueList.equals(List.of(1, 1, 1, 2 - jays))) {
            this.handType = HandType.OnePair;
        } else if (valueList.equals(List.of(1, 1, 1, 1, 1))) {
            this.handType = HandType.HighCard;
        } else if (valueList.isEmpty()) {
            this.handType = HandType.FiveOfAKind; // all jays
        } else {
            throw new RuntimeException("Unknown hand type");
        }
    }

    @Override
    public int compareTo(Hand2 hand2) {
        int byType = this.handType.compareTo(hand2.handType);
        if (byType != 0) {
            return byType;
        }

        for (int i = 0; i < cards.length; i++) {
            CardLabel cardA = cards[i];
            CardLabel cardB = hand2.cards[i];
            if (cardA == CardLabel.J && cardB != CardLabel.J) {
                return -1;
            }
            if (cardA != CardLabel.J && cardB == CardLabel.J) {
                return 1;
            }
            int byCard = cardA.compareTo(cardB);
            if (byCard != 0) {
                return byCard;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return handType + "," + String.join(",", Arrays.stream(cards).map(CardLabel::toString).toArray(String[]::new));
    }
}
