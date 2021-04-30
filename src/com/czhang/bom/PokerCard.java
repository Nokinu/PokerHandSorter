package com.czhang.bom;

import com.czhang.utils.CardUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class to represent the poker card
 */
public class PokerCard {
    //2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace
    private static final Character[] VALID_VALUE = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    //Diamonds (D), Hearts (H), Spades (S), Clubs (C)
    private static final Character[] VALID_SUIT = {'D','H','S','C'};

    private final Character _cardValue;
    private final Character _cardSuit;
    private final int _cardIntValue;

    public PokerCard(String cardData) {
        _cardValue = cardData.charAt(0);
        _cardSuit = cardData.charAt(1);
        _cardIntValue = CardUtils.convertCardValueToInt(_cardValue);
    }

    public Character getCardSuit() {
        return _cardSuit;
    }

    public int getCardIntValue() {
        return _cardIntValue;
    }

    public boolean isValidateCard() {
        return Arrays.asList(VALID_SUIT).contains(_cardSuit)
                && Arrays.asList(VALID_VALUE).contains(_cardValue);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokerCard)) return false;
        PokerCard pokerCard = (PokerCard) o;
        return _cardIntValue == pokerCard._cardIntValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_cardIntValue, _cardSuit);
    }
}
