package org.sl.model;

import java.util.Map;

public class SpecialWormHole implements WormHole {

    private final String name;
    private final int startingPoint;
    private final Map<Integer, Integer> diceRollVsDestinations;
    private final WormHoleType type;

    public SpecialWormHole(String name, int statingPoint, Map<Integer, Integer> diceRollVsDestinations, WormHoleType type){
        this.name = name;
        this.startingPoint = statingPoint;
        this.diceRollVsDestinations = diceRollVsDestinations;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public WormHoleType getType() {
        return type;
    }

    @Override
    public Integer getStartingPoint() {
        return startingPoint;
    }

    @Override
    public Integer getDestination() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDestinationBasedOnDiceRoll(Integer diceRoll) {
        return diceRollVsDestinations.get(diceRoll);
    }
}
