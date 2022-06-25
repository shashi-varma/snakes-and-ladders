package org.sl.model;

import java.util.List;

public class MovingWormHole implements WormHole {

    private final String name;
    private final WormHoleType type;
    private final List<Integer> startingPoints;
    private final int numberOfStartingPoints;
    private final List<Integer> destinations;
    private final int numberOfDestinations;

    public MovingWormHole(String name, WormHoleType type, List<Integer> startingPoints, List<Integer> destinations){
        this.name = name;
        this.type = type;
        this.startingPoints = startingPoints;
        this.destinations = destinations;
        this.numberOfStartingPoints = startingPoints.size();
        this.numberOfDestinations = destinations.size();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDestination() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getStartingPointBasedOnTurnNumber(Integer turnNumber) {
        return startingPoints.get(turnNumber % numberOfStartingPoints);

    }

    @Override
    public Integer getDestinationBasedOnTurnNumber(Integer turnNumber) {
        return destinations.get(turnNumber % numberOfDestinations);
    }

}
