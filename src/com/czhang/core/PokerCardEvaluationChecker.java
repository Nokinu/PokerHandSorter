package com.czhang.core;

import com.czhang.bom.CardResult;
import com.czhang.bom.PokerCard;
import com.czhang.utils.CardUtils;

import java.util.*;

public class PokerCardEvaluationChecker {
    public static final int HIGH_CARD_SCORE = 1;
    public static final int PAIR_SCORE = 2;
    public static final int TWO_PAIR_SCORE = 3;
    public static final int THREE_OF_A_KIND_SCORE = 4;
    public static final int STRAIGHT_ALL_SCORE = 5;
    public static final int FLUSH_ALL_SCORE = 6;
    public static final int FULL_HOUSE_SCORE = 7;
    public static final int FOUR_OF_A_KIND_SCORE = 8;
    public static final int STRAIGHT_FLUSH_ALL_SCORE = 9;
    public static final int ROYAL_FLUSH_SCORE = 10;

    private static int advancedCardResultCompare(final CardResult player1CardResult, final CardResult player2CardResult) {
        int result = compareIntArrays(player1CardResult.getPrimaryCardHighestValue(), player2CardResult.getPrimaryCardHighestValue());
        if(result == 0 && player1CardResult.getSecondaryCardHighestArrayValue().length > 0) {
            result = compareIntArrays(player1CardResult.getSecondaryCardHighestArrayValue(), player2CardResult.getSecondaryCardHighestArrayValue());
        }
        return result;
    }

    private static int compareIntArrays(final int[] array1, final int[] array2) {
        for(int i = array1.length-1; i > -1; i--) {
            if(array1[i] > array2[i]) {
                return 1;
            } else if(array1[i] < array2[i]) {
                return -1;
            }
        }
        return 0;
    }

    /**
     *
     * @return 0 - even, 1-player1 win, -1-player2 win
     */
    public static int compareTheCardResult(final CardResult player1CardResult, final CardResult player2CardResult) {
        if(player1CardResult.getCardScore() == player2CardResult.getCardScore()) {
            switch(player1CardResult.getCardScore()) {
                // compare primary value only
                case STRAIGHT_FLUSH_ALL_SCORE:
                case FOUR_OF_A_KIND_SCORE:
                case FULL_HOUSE_SCORE:
                case STRAIGHT_ALL_SCORE:
                case THREE_OF_A_KIND_SCORE:
                    return Integer.compare(player1CardResult.getPrimaryCardHighestValue()[0], player2CardResult.getPrimaryCardHighestValue()[0]);
                    // compare both primary and secondary
                case HIGH_CARD_SCORE:
                case FLUSH_ALL_SCORE:
                case PAIR_SCORE:
                case TWO_PAIR_SCORE:
                    return advancedCardResultCompare(player1CardResult, player2CardResult);
                case ROYAL_FLUSH_SCORE:
                default:
                    return 0;
            }
        }
        return player1CardResult.getCardScore() > player2CardResult.getCardScore() ? 1 : -1;
    }

    public static void checkPokerCards(final PokerCard[] pokerCards, CardResult cardResult) {
        if(!isStraightStrategies(pokerCards, cardResult) && !isPairStrategies(pokerCards, cardResult)) {
            cardResult.setPrimaryCardHighestValue(CardUtils.generatePrimaryCardValue(pokerCards));
        }
    }

    public static boolean isStraightStrategies(final PokerCard[] pokerCards, CardResult cardResult) {
        boolean isStraight = false;
        boolean isFlush = false;
        if(isStraight(pokerCards)) {
            cardResult.setCardScore(STRAIGHT_ALL_SCORE);
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[4].getCardIntValue()});
            isStraight = true;
        }
        if(isFlush(pokerCards)) {
            cardResult.setCardScore(FLUSH_ALL_SCORE);
            cardResult.setPrimaryCardHighestValue(CardUtils.generatePrimaryCardValue(pokerCards));
            isFlush = true;
        }

        if(isStraight && isFlush) {
            cardResult.setCardScore(STRAIGHT_FLUSH_ALL_SCORE);
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[4].getCardIntValue()});
            if(isRoyalFlush(pokerCards)) {
                cardResult.setCardScore(ROYAL_FLUSH_SCORE);
            }
        }
        return isFlush || isStraight;
    }

    public static boolean isPairStrategies(final PokerCard[] pokerCards, CardResult cardResult) {
        boolean checkResult = false;
        if(isFourOfAKind(pokerCards, cardResult)) {
            cardResult.setCardScore(FOUR_OF_A_KIND_SCORE);
            checkResult = true;
        } else if(isFullHouse(pokerCards, cardResult)) {
            cardResult.setCardScore(FULL_HOUSE_SCORE);
            checkResult = true;
        } else if(isThreeOfAkind(pokerCards, cardResult)){
            cardResult.setCardScore(THREE_OF_A_KIND_SCORE);
            checkResult = true;
        } else if(isTwoPair(pokerCards, cardResult)) {
            cardResult.setCardScore(TWO_PAIR_SCORE);
            checkResult = true;
        } else if(isPair(pokerCards, cardResult)){
            cardResult.setCardScore(PAIR_SCORE);
            checkResult = true;
        }
        return checkResult;
    }

    /**
     *
     * @return true if All five cards in consecutive value order
     */
    private static boolean isStraight(final PokerCard[] pokerCards) {
        for(int i = 0; i < pokerCards.length-1; i++) {
            if(pokerCards[i+1].getCardIntValue() - pokerCards[i].getCardIntValue() != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return true if All five cards having the same suit
     */
    private static boolean isFlush(final PokerCard[] pokerCards) {
        char initSuit = pokerCards[0].getCardSuit();
        return Arrays.stream(pokerCards).allMatch(pokerCard -> pokerCard.getCardSuit() == initSuit);
    }

    /**
     * This method is only called when pokerCards are both Straight and Flush
     * @return true
     */
    private static boolean isRoyalFlush(final PokerCard[] pokerCards) {
        return pokerCards[0].getCardIntValue() == 10;
    }

    private static boolean isFourOfAKind(final PokerCard[] pokerCards, CardResult cardResult) {
        boolean checkResult = false;
        if(Objects.equals(pokerCards[1].getCardIntValue(), pokerCards[4].getCardIntValue())) {
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[1].getCardIntValue()});
            checkResult = true;
        } else if(Objects.equals(pokerCards[0].getCardIntValue(), pokerCards[3].getCardIntValue())) {
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[0].getCardIntValue()});
            checkResult = true;
        }
        return checkResult;
    }

    private static boolean isFullHouse(final PokerCard[] pokerCards, CardResult cardResult) {
        boolean checkResult = false;
        if(Objects.equals(pokerCards[0].getCardIntValue(), pokerCards[2].getCardIntValue())
        && Objects.equals(pokerCards[3].getCardIntValue(), pokerCards[4].getCardIntValue())) {
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[0].getCardIntValue()});
            checkResult = true;
        } else if(Objects.equals(pokerCards[0].getCardIntValue(), pokerCards[1].getCardIntValue())
                && Objects.equals(pokerCards[2].getCardIntValue(), pokerCards[4].getCardIntValue())) {
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[2].getCardIntValue()});
            checkResult = true;
        }
        return  checkResult;
    }

    private static boolean isThreeOfAkind(final PokerCard[] pokerCards, CardResult cardResult) {
        boolean checkResult = false;
        if(Objects.equals(pokerCards[0].getCardIntValue(), pokerCards[2].getCardIntValue())) {
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[0].getCardIntValue()});
            checkResult = true;
        } else if(Objects.equals(pokerCards[1].getCardIntValue(), pokerCards[3].getCardIntValue())) {
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[1].getCardIntValue()});
            checkResult = true;
        } else if(Objects.equals(pokerCards[2].getCardIntValue(), pokerCards[4].getCardIntValue())) {
            cardResult.setPrimaryCardHighestValue(new int[] {pokerCards[2].getCardIntValue()});
            checkResult = true;
        }
        return checkResult;
    }

    private static boolean isTwoPair(final PokerCard[] pokerCards, CardResult cardResult) {
        return checkPair(pokerCards, cardResult) == 3;
    }

    private static boolean isPair(final PokerCard[] pokerCards, CardResult cardResult) {
        return checkPair(pokerCards, cardResult) == 4;
    }

    private static int checkPair(final PokerCard[] pokerCards, CardResult cardResult) {
        Set<Integer> checkPairSet = new HashSet<>();
        List<Integer> primaryCardHighestArrayValue = new ArrayList<>();
        Arrays.stream(pokerCards).iterator().forEachRemaining(pokerCard -> {
            if(checkPairSet.contains(pokerCard.getCardIntValue())) {
                primaryCardHighestArrayValue.add(pokerCard.getCardIntValue());
            }
            checkPairSet.add(pokerCard.getCardIntValue());
        });
        cardResult.setPrimaryCardHighestValue(primaryCardHighestArrayValue.stream().mapToInt(Integer::intValue).toArray());
        cardResult.setSecondaryCardHighestArrayValue(checkPairSet.stream().filter(setData -> !primaryCardHighestArrayValue.contains(setData)).mapToInt(Integer::intValue).toArray());
        return checkPairSet.size();
    }

}
