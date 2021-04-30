package test.czhang.bom;

import com.czhang.bom.CardResult;
import com.czhang.bom.PokerCard;
import com.czhang.core.PokerCardEvaluationChecker;
import com.czhang.utils.CardUtils;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Comparator;

public class PokerCardTest extends TestCase {

    public void testPokerCardsSort() {
        PokerCard[] pokerCards = CardUtils.generatePokerCards(new String[]{"KD", "4C", "6S", "7S", "4H"});
        assertTrue(Arrays.equals(new int[]{4,4,6,7,13}, CardUtils.generatePrimaryCardValue(pokerCards)));
    }

    public void testPokerCardValidation() {
        PokerCard pokerCard = new PokerCard("TD");
        assertTrue(pokerCard.isValidateCard());
    }

    public void testInvalidatePorkerCard() {
        PokerCard pokerCard = new PokerCard("1Q");
        assertFalse(pokerCard.isValidateCard());
    }

    public void testPairCheck() {
        PokerCard[] pokerCards = CardUtils.generatePokerCards(new String[]{"4H","4C", "6S", "7S", "KD"});
        CardResult cardResult = new CardResult();
        assertTrue(PokerCardEvaluationChecker.isPairStrategies(pokerCards, cardResult));
        assertEquals(1, cardResult.getPrimaryCardHighestValue().length);
        assertEquals(3, cardResult.getSecondaryCardHighestArrayValue().length);
        assertEquals(4, cardResult.getPrimaryCardHighestValue()[0]);
        assertEquals(13, cardResult.getSecondaryCardHighestArrayValue()[2]);

        pokerCards = CardUtils.generatePokerCards(new String[]{"2C", "3S", "9S", "9D", "TD"});
        cardResult = new CardResult();
        assertTrue(PokerCardEvaluationChecker.isPairStrategies(pokerCards, cardResult));
        assertEquals(1, cardResult.getPrimaryCardHighestValue().length);
        assertEquals(3, cardResult.getSecondaryCardHighestArrayValue().length);
        assertEquals(9, cardResult.getPrimaryCardHighestValue()[0]);
        assertEquals(10, cardResult.getSecondaryCardHighestArrayValue()[2]);
    }

    public void testThreeAkind() {
        PokerCard[] pokerCards = CardUtils.generatePokerCards(new String[]{"2D", "9C", "AS" ,"AH", "AC"});
        CardResult cardResult = new CardResult();
        assertTrue(PokerCardEvaluationChecker.isPairStrategies(pokerCards, cardResult));
        assertEquals(1, cardResult.getPrimaryCardHighestValue().length);
        assertEquals(0, cardResult.getSecondaryCardHighestArrayValue().length);
        assertEquals(14, cardResult.getPrimaryCardHighestValue()[0]);
    }

    public void testFlush() {
        PokerCard[] pokerCards = CardUtils.generatePokerCards(new String[]{"3D", "6D", "7D", "TD", "QD"});
        CardResult cardResult = new CardResult();
        assertTrue(PokerCardEvaluationChecker.isStraightStrategies(pokerCards, cardResult));
        assertEquals(5, cardResult.getPrimaryCardHighestValue().length);
        assertEquals(0, cardResult.getSecondaryCardHighestArrayValue().length);
    }

    public void testFullHouse() {
        PokerCard[] pokerCards = CardUtils.generatePokerCards(new String[]{"2H", "2D", "4C", "4D", "4S"});
        CardResult cardResult = new CardResult();
        assertTrue(PokerCardEvaluationChecker.isPairStrategies(pokerCards, cardResult));
        assertEquals(1, cardResult.getPrimaryCardHighestValue().length);
        assertEquals(0, cardResult.getSecondaryCardHighestArrayValue().length);
        assertEquals(4, cardResult.getPrimaryCardHighestValue()[0]);
    }


}
