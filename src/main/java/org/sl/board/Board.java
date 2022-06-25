package org.sl.board;

import org.sl.model.MovingWormHole;
import org.sl.model.SimpleWormHole;
import org.sl.model.SpecialWormHole;
import org.sl.model.WormHole;

import java.util.*;

import static org.sl.model.WormHoleType.Snake;

public class Board {

    private final Map<Integer, WormHole> simpleSnakeLocations = new HashMap<>();
    private final Map<Integer, WormHole> simpleLadderLocations = new HashMap<>();

    private final Map<Integer, WormHole> specialLadderLocations = new HashMap<>();
    private final Map<Integer, WormHole> specialSnakeLocations = new HashMap<>();

    private final Map<Integer, WormHole> movingSnakeLocations = new HashMap<>();
    private final Map<Integer, WormHole> movingLadderLocations = new HashMap<>();

    private final Integer boardLength;

    public Board(Integer boardLength, List<WormHole> wormHoles){
        this.boardLength = boardLength;

        // todo : validate that no two worm holes start from same location
        for(WormHole wormHole : wormHoles) {
            if(wormHole instanceof SimpleWormHole) {
                if(wormHole.getType().equals(Snake)){
                    simpleSnakeLocations.put( wormHole.getStartingPoint(), wormHole );
                } else {
                    simpleLadderLocations.put( wormHole.getStartingPoint(), wormHole );
                }
            } else if(wormHole instanceof SpecialWormHole) {
                if(wormHole.getType().equals(Snake)){
                    specialSnakeLocations.put( wormHole.getStartingPoint(), wormHole );
                } else {
                    specialSnakeLocations.put( wormHole.getStartingPoint(), wormHole );
                }
            } else if(wormHole instanceof MovingWormHole) {
                if(wormHole.getType().equals(Snake)){
                    movingSnakeLocations.put( wormHole.getStartingPointBasedOnTurnNumber(0), wormHole );
                } else {
                    movingSnakeLocations.put( wormHole.getStartingPointBasedOnTurnNumber(0), wormHole );
                }
            }
        }
    }

    public BoardLocationInfo getLocationInfo(Integer location){
        if(simpleSnakeLocations.containsKey(location)){
            return new BoardLocationInfo(true, simpleSnakeLocations.get(location));
        } else if(simpleLadderLocations.containsKey(location)) {
            return new BoardLocationInfo(true, simpleLadderLocations.get(location));
        } else if(specialLadderLocations.containsKey(location)) {
            return new BoardLocationInfo(true, specialLadderLocations.get(location));
        } else if(specialSnakeLocations.containsKey(location)) {
            return new BoardLocationInfo(true, specialSnakeLocations.get(location));
        } else if(movingSnakeLocations.containsKey(location)) {
            return new BoardLocationInfo(true, movingSnakeLocations.get(location));
        } else if(movingLadderLocations.containsKey(location)) {
            return new BoardLocationInfo(true, movingLadderLocations.get(location));
        } else {
            return new BoardLocationInfo(false, null);
        }
    }

    public void updateMovingSnakeAndLadderLocations(Integer totalDiceRolls){
        Collection<WormHole> movingSnakes = movingSnakeLocations.values();
        Collection<WormHole> movingLadders = movingLadderLocations.values();
        movingSnakeLocations.clear();
        movingLadderLocations.clear();
        for(WormHole movingSnake : movingSnakes){
            movingSnakeLocations.put( movingSnake.getStartingPointBasedOnTurnNumber(totalDiceRolls), movingSnake );
        }
        for(WormHole movingLadder : movingLadders){
            movingLadderLocations.put( movingLadder.getStartingPointBasedOnTurnNumber(totalDiceRolls), movingLadder );
        }
    }

    public boolean isEndLocation(Integer location){
        return Objects.equals(location, boardLength);
    }

    public boolean isGreaterThanEndLocation(Integer location){
        return location > boardLength;
    }

    public Map<Integer, WormHole> getSimpleSnakeLocations() {
        return simpleSnakeLocations;
    }

    public Map<Integer, WormHole> getSimpleLadderLocations() {
        return simpleLadderLocations;
    }

    public Map<Integer, WormHole> getSpecialLadderLocations() {
        return specialLadderLocations;
    }

    public Map<Integer, WormHole> getSpecialSnakeLocations() {
        return specialSnakeLocations;
    }

    public Map<Integer, WormHole> getMovingSnakeLocations() {
        return movingSnakeLocations;
    }

    public Map<Integer, WormHole> getMovingLadderLocations() {
        return movingLadderLocations;
    }

}
