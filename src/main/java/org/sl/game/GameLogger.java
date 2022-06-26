package org.sl.game;

import org.sl.board.Dice;
import org.sl.board.Board;
import org.sl.model.WormHole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameLogger {

    private final List<String> messages = new ArrayList<>();

    private final List<PlayerTurnInfo> playerTurnInfoLog = new ArrayList<>();

    private final Board board;
    private final Dice dice;

    private Player[] winnersInOrder;

    public GameLogger(Board board, Dice dice) {
        this.board = board;
        this.dice = dice;
    }

    public void log(String message){
        messages.add(message);
    }

    public void logPlayerTurnStart(Player player){
        messages.add("# Its Player " + player.getPlayerId() + "'s turn ");
    }

    public void logDiceRoll(Player player, Integer diceRoll){
        messages.add("Player " + player.getPlayerId() + " rolled " + diceRoll);
    }

    public void logMovingForDiceRoll(Player player, Integer diceRoll){
        messages.add("Moving Player " + player.getPlayerId() + " for the dice roll " + diceRoll);
    }

    public void logWastedTurn(Player player, String reason){
        messages.add("Player " + player.getPlayerId() + "'s turn got wasted because " + reason);
    }

    public void logPlayerMovement(Player player, Integer startingLocation, Integer endingLocation){
        messages.add("Player " + player.getPlayerId() + " moved from Location " + startingLocation + " to Location " + endingLocation);
    }

    public void logWormHoleEncounter(Player player, WormHole wormHole, Integer startingLocation, Integer endingLocation){
        messages.add("Player " + player.getPlayerId() + " encountered a " + wormHole.getType().name()
                + ". Moved from Location " + startingLocation + " to Location " + endingLocation);
    }

    public void logPlayerWinning(Player player){
        messages.add("Player " + player.getPlayerId() + " reached the finish point");
    }

    public void logGameResults(Player[] winnersInOrder){
        this.winnersInOrder = winnersInOrder;
        messages.add("Game Over." + " Winners in Order : " + Arrays.stream(winnersInOrder).map(Player::getPlayerId).collect(Collectors.joining()));
    }

    public void logTurn(PlayerTurnInfo turnInfo){
        playerTurnInfoLog.add(turnInfo);
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<PlayerTurnInfo> getPlayerTurnInfoLog() {
        return playerTurnInfoLog;
    }

    public void printPlayerTurnInfoLog(){
        System.out.println("\n\n---- PRINTING LOG OF PLAYER TURN INFORMATION ----");
        for (PlayerTurnInfo turnInformation: getPlayerTurnInfoLog()) {
            System.out.println(turnInformation.toString());
        }
        System.out.println("---- LOG OF PLAYER TURN INFORMATION END ----\n");
    }

    public void printMessages(){
        System.out.println("\n\n---- PRINTING GAME MESSAGES ----");
        for (String message: getMessages()) {
            System.out.println(message);
        }
        System.out.println("---- GAME MESSAGES END ----\n");
    }

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }

    public Player[] getWinnersInOrder() {
        return winnersInOrder;
    }
}
