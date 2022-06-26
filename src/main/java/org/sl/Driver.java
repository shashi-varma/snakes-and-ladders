package org.sl;

import org.sl.board.Dice;
import org.sl.game.Game;
import org.sl.board.Board;
import org.sl.game.GameLogger;
import org.sl.game.Player;
import org.sl.game.PlayerTurnInfo;
import org.sl.model.SimpleWormHole;
import org.sl.model.WormHole;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static org.sl.model.WormHoleType.Ladder;
import static org.sl.model.WormHoleType.Snake;

public class Driver {

    public void play(){
        int numberOfPlayers = 2;

        List<Player> playerList = new ArrayList<>();
        for(int i=0; i<numberOfPlayers; i++){
            playerList.add(new Player("P-" + i,1));
        }

        List<WormHole> wormHoles = new ArrayList<>();
        wormHoles.add(new SimpleWormHole("S1",98,25, Snake));
        wormHoles.add(new SimpleWormHole("S2",70,10, Snake));
        wormHoles.add(new SimpleWormHole("S3", 50,35, Snake));
        wormHoles.add(new SimpleWormHole("L1",8,22, Ladder));
        wormHoles.add(new SimpleWormHole("L2",29,88, Ladder));
        wormHoles.add(new SimpleWormHole("L3",66,96, Ladder));

        Board board = new Board(100, wormHoles);
        Dice dice = new Dice(1,6);

        GameLogger gameLogger = new GameLogger(board, dice);

        Game game = new Game(board, playerList, gameLogger);

        Deque<Player> turns = new ArrayDeque<>( playerList );

        while(!game.isGameOver() && !turns.isEmpty()) {
            Player currentPlayer = turns.poll();
            gameLogger.logPlayerTurnStart(currentPlayer);

            List<Integer> diceRollsInCurrentTurn = new ArrayList<>();

            Integer diceRoll = dice.roll();
            gameLogger.logDiceRoll(currentPlayer, diceRoll);

            int numberOfConsecutiveSixes = 0;
            while(diceRoll == 6) {
                numberOfConsecutiveSixes++;
                if(numberOfConsecutiveSixes == 3) { break; }
                diceRollsInCurrentTurn.add(diceRoll);
                diceRoll = dice.roll();
                gameLogger.logDiceRoll(currentPlayer, diceRoll);
            }
            diceRollsInCurrentTurn.add(diceRoll);

            PlayerTurnInfo turnInfo = new PlayerTurnInfo(currentPlayer, diceRollsInCurrentTurn);
            gameLogger.logTurn(turnInfo);

            if(numberOfConsecutiveSixes == 3){
                gameLogger.logWastedTurn(currentPlayer, "Three consecutive six are rolled");
                turns.add(currentPlayer);
                turnInfo.setWastedTurn(true);
                continue;
            }

            game.movePlayer( turnInfo );

            if (!game.isPlayerDone(currentPlayer)) {
                turns.add(currentPlayer);
            }
        }
        gameLogger.logGameResults(game.getWinnersInOrder());

        gameLogger.printPlayerTurnInfoLog();
        gameLogger.printMessages();
    }
}
