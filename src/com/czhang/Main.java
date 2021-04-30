package com.czhang;

import com.czhang.bom.CardResult;
import com.czhang.bom.PokerCard;
import com.czhang.core.PokerCardEvaluationChecker;
import com.czhang.utils.CardUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;

public class Main {

    private static final String DEFAULT_TEST_DATA_FILE = "poker-hands.txt";
    private static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        int play1Result = 0;
        int play2Result = 0;
        String fileName = DEFAULT_TEST_DATA_FILE;
        if(args.length > 0) {
            fileName = args[0];
        }
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String inputData;
        while ((inputData=bufferedReader.readLine()) != null) {
            String[] data = inputData.split(SPACE);
            if(data.length == 10) {
                PokerCard[] player1PokerCards = CardUtils.generatePokerCards(IntStream.rangeClosed(0,4).mapToObj(i->data[i]).toArray(String[]::new));
                PokerCard[] player2PokerCards = CardUtils.generatePokerCards(IntStream.rangeClosed(5,9).mapToObj(i->data[i]).toArray(String[]::new));
                if(player1PokerCards.length == 5 && player2PokerCards.length ==5){
                    CardResult player1Result = new CardResult();
                    CardResult player2Result = new CardResult();
                    PokerCardEvaluationChecker.checkPokerCards(player1PokerCards, player1Result);
                    PokerCardEvaluationChecker.checkPokerCards(player2PokerCards, player2Result);
                    if(PokerCardEvaluationChecker.compareTheCardResult(player1Result, player2Result) == 1) {
                        play1Result++;
                    } else if(PokerCardEvaluationChecker.compareTheCardResult(player1Result, player2Result) == -1) {
                        play2Result++;
                    }
                }

            }
        }
        CardUtils.displayTheResult(play1Result, play2Result);
    }
}
