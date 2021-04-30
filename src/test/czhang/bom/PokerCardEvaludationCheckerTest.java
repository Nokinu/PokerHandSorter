package test.czhang.bom;

import com.czhang.bom.CardResult;
import com.czhang.bom.PokerCard;
import com.czhang.core.PokerCardEvaluationChecker;
import com.czhang.utils.CardUtils;
import junit.framework.TestCase;

public class PokerCardEvaludationCheckerTest extends TestCase {

    public void testTwoPairPlayers() {
        PokerCard[] pokerCards1 = CardUtils.generatePokerCards(new String[]{"4H", "4C", "6S", "7S", "KD"});
        PokerCard[] pokerCards2 = CardUtils.generatePokerCards(new String[]{"2C", "3S", "9S", "9D", "TD"});
        CardResult cardResult1 = new CardResult();
        CardResult cardResult2 = new CardResult();

        assertTrue(PokerCardEvaluationChecker.isPairStrategies(pokerCards1, cardResult1));
        assertTrue(PokerCardEvaluationChecker.isPairStrategies(pokerCards2, cardResult2));

        assertEquals(-1, PokerCardEvaluationChecker.compareTheCardResult(cardResult1, cardResult2));

    }
}
