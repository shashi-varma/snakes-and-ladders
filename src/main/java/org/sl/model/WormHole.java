package org.sl.model;

public interface WormHole {

    String getName();

    WormHoleType getType();

    Integer getStartingPoint();

    Integer getDestination();

    default Integer getStartingPointBasedOnTurnNumber(Integer turnNumber) {
        return getStartingPoint();
    }

    default Integer getDestinationBasedOnTurnNumber(Integer turnNumber) {
        return getDestination();
    }

    default Integer getDestinationBasedOnDiceRoll(Integer diceRoll) {
        return getDestination();
    }

    default Integer getDestinationBasedOnDiceRollAndTurns(Integer time, Integer totalNumberOfTurns) {
        return getDestination();
    }
}
