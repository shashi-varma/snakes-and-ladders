package org.sl.game;

import java.util.List;

public class PlayerTurnInfo {

    private final Player player;
    private final List<Integer> diceRolls;
    private boolean isWastedTurn = false;

    public PlayerTurnInfo(Player player, List<Integer> diceRolls) {
        this.player = player;
        this.diceRolls = diceRolls;
    }

    public void setWastedTurn(boolean wastedTurn) {
        isWastedTurn = wastedTurn;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Integer> getDiceRolls() {
        return diceRolls;
    }

    public boolean isWastedTurn() {
        return isWastedTurn;
    }

    @Override
    public String toString() {
        return "{ " +
                "playerId = " + player.getPlayerId() +
                ", diceRolls = " + diceRolls +
                ", isWastedTurn = " + isWastedTurn + '}';
    }
}