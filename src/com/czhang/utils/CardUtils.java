package com.czhang.utils;

import com.czhang.bom.PokerCard;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utils class to represent common methods
 */
public class CardUtils {

    private static final String PLAYER_1_RESULT = "Player 1: ";
    private static final String PLAYER_2_RESULT = "Player 2: ";

    public static PokerCard[] generatePokerCards(final String[] inputValue) {
        if(inputValue !=null && inputValue.length == 5) {
            PokerCard[] pokerCards = Arrays.stream(inputValue).map(PokerCard::new).toArray(PokerCard[]::new);
            if(Arrays.stream(pokerCards).allMatch(PokerCard::isValidateCard)) {
                Arrays.sort(pokerCards, Comparator.comparingInt(PokerCard::getCardIntValue));
                return pokerCards;
            }
        }
        return new PokerCard[0];
    }

    public static int convertCardValueToInt(final Character cardValue) {
        switch (cardValue){
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'A':
                return 14;
            default:
                return Character.getNumericValue(cardValue);
        }
    }

    public static int[] generatePrimaryCardValue(final PokerCard[] pokerCards) {
        return Arrays.stream(pokerCards).mapToInt(PokerCard::getCardIntValue).toArray();
    }

    public static void displayTheResult(final int player1Result, final int player2Result) {
        System.out.println(PLAYER_1_RESULT + player1Result);
        System.out.println(PLAYER_2_RESULT + player2Result);
    }
}
