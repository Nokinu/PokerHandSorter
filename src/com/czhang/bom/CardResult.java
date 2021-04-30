package com.czhang.bom;

import com.czhang.core.PokerCardEvaluationChecker;

/**
 * Class to represent the result.
 */
public class CardResult {
    private int _cardScore = PokerCardEvaluationChecker.HIGH_CARD_SCORE;
    private int[] _primaryCardHighestValue = new int[0];
    private int[] _secondaryCardHighestArrayValue = new int[0];

    public int getCardScore() {
        return _cardScore;
    }

    public void setCardScore(int cardScore) {
        _cardScore = cardScore;
    }

    public int[] getPrimaryCardHighestValue() {
        return _primaryCardHighestValue;
    }

    public void setPrimaryCardHighestValue(int[] primaryCardHighestValue) {
        _primaryCardHighestValue = primaryCardHighestValue;
    }

    public int[] getSecondaryCardHighestArrayValue() {
        return _secondaryCardHighestArrayValue;
    }

    public void setSecondaryCardHighestArrayValue(int[] secondaryCardHighestArrayValue) {
        _secondaryCardHighestArrayValue = secondaryCardHighestArrayValue;
    }


}
