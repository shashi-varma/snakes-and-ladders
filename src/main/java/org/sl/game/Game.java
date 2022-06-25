package org.sl.game;

import org.sl.Player;
import org.sl.board.Board;
import org.sl.board.BoardLocationInfo;
import org.sl.model.MovingWormHole;
import org.sl.model.SimpleWormHole;
import org.sl.model.SpecialWormHole;
import org.sl.model.WormHole;

import java.util.*;

public class Game {

    private final Map<String, Player> players = new HashMap<>();
    private final Integer numberOfPlayers;

    private final Map<String, Integer> winners = new HashMap<>();

    private final Board board;

    private final GameLogger gameLogger;

    public Game(Board board, List<Player> playerList, GameLogger gameLogger){
        this.board = board;
        for(Player player : playerList) {
            players.put(player.getPlayerId(), player);
        }
        numberOfPlayers = playerList.size();
        this.gameLogger = gameLogger;
    }

    private Integer totalTurns = 0;

    public void movePlayer(Player currentPlayer, List<Integer> diceRollsInCurrentTurn){
        totalTurns++;

        Integer startingLocation = currentPlayer.getLocation();

        Deque<Integer> diceRolls = new ArrayDeque<>(diceRollsInCurrentTurn);

        while(!diceRolls.isEmpty()) {
            int diceRoll = diceRolls.poll();
            int nextLocation = currentPlayer.getLocation() + diceRoll;

            if(board.isEndLocation(nextLocation)) {
                if(diceRolls.isEmpty()){
                    // Got to end location by using up all dice rolls, hence player completes the game
                    winners.put(currentPlayer.getPlayerId(), winners.size() + 1);
                } else {
                    // Got to end location without using up dice rolls, hence this turn of the player is wasted, putting him at starting location
                    currentPlayer.setLocation(startingLocation);
                }
                break;
            } else if(board.isGreaterThanEndLocation(nextLocation)) {
                // Dice rolls are more than required and Player can't accommodate them (i.e next location > finish line), hence the turn is wasted
                currentPlayer.setLocation(startingLocation);
                break;
            }

            // looking for snakes / ladders at the next location
            BoardLocationInfo locationInfo = board.getLocationInfo(nextLocation);
            if(locationInfo.hasWormHole()){
                while(locationInfo.hasWormHole()) { // taking care of cascading snakes / ladders case
                    WormHole wormHole = locationInfo.getWormHoleOnTheLocation();
                    if(wormHole instanceof SimpleWormHole){
                        nextLocation = wormHole.getDestination();
                    } else if(wormHole instanceof SpecialWormHole) {
                        nextLocation = wormHole.getDestinationBasedOnDiceRoll(diceRoll);
                    } else if(wormHole instanceof MovingWormHole) {
                        nextLocation = wormHole.getDestinationBasedOnTurnNumber(totalTurns);
                    }
                    locationInfo = board.getLocationInfo(nextLocation);
                }
            }

            currentPlayer.setLocation(nextLocation);
        }
    }

    public boolean isGameOver(){
        return winners.size() == numberOfPlayers - 1;
    }

    public boolean isPlayerDone(Player player){
        return winners.containsKey(player.getPlayerId());
    }

    public List<Player> getPlayers(){
        return new ArrayList<>(players.values());
    }

    public GameLogger getGameLogger() {
        return gameLogger;
    }
}
