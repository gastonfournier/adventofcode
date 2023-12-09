package com.gastonfournier.y2023.day7;

import java.util.*;

public class Hand implements Comparable<Hand> {
    private final CardLabel[] cards;
    private final HandType handType;
    public final long bid;

    public Hand(String cards, long bid) {
        this.cards = Arrays.stream(cards.split("")).map(CardLabel::fromString).toArray(CardLabel[]::new);
        this.bid = bid;
        Map<CardLabel, Integer> cardCount = new HashMap<>();
        for (CardLabel cardLabel: this.cards) {
            cardCount.put(cardLabel, cardCount.getOrDefault(cardLabel, 0) + 1);
        }
        Collection<Integer> values = cardCount.values();
        List<Integer> valueList = new ArrayList<>(values);
        Collections.sort(valueList);
        if (valueList.equals(List.of(5))) {
            this.handType = HandType.FiveOfAKind;
        } else if (valueList.equals(List.of(1, 4))) {
            this.handType = HandType.FourOfAKind;
        } else if (valueList.equals(List.of(2, 3))) {
            this.handType = HandType.FullHouse;
        } else if (valueList.equals(List.of(1, 1, 3))) {
            this.handType = HandType.ThreeOfAKind;
        } else if (valueList.equals(List.of(1, 2, 2))) {
            this.handType = HandType.TwoPair;
        } else if (valueList.equals(List.of(1, 1, 1, 2))) {
            this.handType = HandType.OnePair;
        } else if (valueList.equals(List.of(1, 1, 1, 1, 1))) {
            this.handType = HandType.HighCard;
        } else {
            throw new RuntimeException("Unknown hand type");
        }
    }

    @Override
    public int compareTo(Hand hand2) {
        int byType = this.handType.compareTo(hand2.handType);
        if (byType != 0) {
            return byType;
        }

        for (int i = 0; i < cards.length; i++) {
            CardLabel cardA = cards[i];
            CardLabel cardB = hand2.cards[i];
            int byCard = cardA.compareTo(cardB);
            if (byCard != 0) {
                return byCard;
            }
        }
        return 0;
    }
}
