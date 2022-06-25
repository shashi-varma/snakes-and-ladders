package org.sl;

import org.sl.game.Game;
import org.sl.board.Board;
import org.sl.game.GameLogger;
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

        List<Player> playerList = new ArrayList<>();
        for(int i=0; i<5; i++){
            playerList.add(new Player("Player " + i,1));
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
        GameLogger gameLogger = new GameLogger();

        Game game = new Game(board, playerList, gameLogger);

        Deque<Player> turns = new ArrayDeque<>( playerList );

        while(!game.isGameOver() && !turns.isEmpty()) {
            Player currentPlayer = turns.poll();

            List<Integer> diceRollsInCurrentTurn = new ArrayList<>();

            Integer val = dice.roll();
            int numberOfConsecutiveSixes = 0;
            while(val == 6) {
                numberOfConsecutiveSixes++;
                if(numberOfConsecutiveSixes == 3) { break; }
                diceRollsInCurrentTurn.add(val);
                val = dice.roll();
            }
            diceRollsInCurrentTurn.add(val);

            if(numberOfConsecutiveSixes == 3){
                turns.add(currentPlayer);
                continue;
            }

            game.movePlayer(currentPlayer, diceRollsInCurrentTurn);

            if (!game.isPlayerDone(currentPlayer)) {
                turns.add(currentPlayer);
            }
        }
    }
}
