package org.sl.game;

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

    public void movePlayer(PlayerTurnInfo playerTurnInfo){
        totalTurns++;

        Player currentPlayer = playerTurnInfo.getPlayer();
        Integer startingLocation = currentPlayer.getLocation();

        Deque<Integer> diceRollsInCurrentTurn = new ArrayDeque<>( playerTurnInfo.getDiceRolls() );

        Integer previousLocation = startingLocation;
        while(!diceRollsInCurrentTurn.isEmpty()) {
            int diceRoll = diceRollsInCurrentTurn.poll();
            gameLogger.logMovingForDiceRoll(currentPlayer, diceRoll);

            int nextLocation = currentPlayer.getLocation() + diceRoll;
            if(board.isEndLocation(nextLocation)) {
                if(diceRollsInCurrentTurn.isEmpty()){
                    // Got to end location by using up all dice rolls, hence player completes the game
                    winners.put(currentPlayer.getPlayerId(), winners.size());
                    gameLogger.logPlayerMovement(currentPlayer, previousLocation, nextLocation);
                    gameLogger.logPlayerWinning(currentPlayer);
                } else {
                    // Got to end location without using up dice rolls, hence this turn of the player is wasted, putting him at starting location
                    currentPlayer.setLocation(startingLocation);
                    playerTurnInfo.setWastedTurn(true);
                    gameLogger.logWastedTurn(currentPlayer, "Unable to accommodate dice rolls. Resetting player's location to starting location " + startingLocation);
                }
                break;
            } else if(board.isGreaterThanEndLocation(nextLocation)) {
                // Dice rolls are more than required and Player can't accommodate them (i.e next location > finish line), hence the turn is wasted
                currentPlayer.setLocation(startingLocation);
                playerTurnInfo.setWastedTurn(true);
                gameLogger.logWastedTurn(currentPlayer, "Unable to accommodate dice rolls. Resetting player's location to starting location " + startingLocation);
                break;
            }
            gameLogger.logPlayerMovement(currentPlayer, previousLocation, nextLocation);

            // looking for snakes / ladders at the next location
            BoardLocationInfo locationInfo = board.getLocationInfo(nextLocation);
            if(locationInfo.hasWormHole()){
                while(locationInfo.hasWormHole()) { // taking care of cascading snakes / ladders case
                    previousLocation = nextLocation;
                    WormHole wormHole = locationInfo.getWormHoleOnTheLocation();
                    if(wormHole instanceof SimpleWormHole){
                        nextLocation = wormHole.getDestination();
                    } else if(wormHole instanceof SpecialWormHole) {
                        nextLocation = wormHole.getDestinationBasedOnDiceRoll(diceRoll);
                    } else if(wormHole instanceof MovingWormHole) {
                        nextLocation = wormHole.getDestinationBasedOnTurnNumber(totalTurns);
                    }
                    gameLogger.logWormHoleEncounter(currentPlayer, wormHole, previousLocation, nextLocation);
                    locationInfo = board.getLocationInfo(nextLocation);
                }
            }

            previousLocation = nextLocation;
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

    public Player[] getWinnersInOrder() {
        Player[] winnersInOrder = new Player[winners.size()];
        for(String playerId : winners.keySet()){
            winnersInOrder[winners.get(playerId)] = players.get(playerId);
        }
        return winnersInOrder;
    }
}
