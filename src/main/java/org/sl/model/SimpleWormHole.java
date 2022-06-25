package org.sl.model;

public class SimpleWormHole implements WormHole {

    private final String name;
    private final int startingPoint;
    private final int destination;
    private final WormHoleType type;

    public SimpleWormHole(String name, int statingPoint, int destination, WormHoleType type){
        this.name = name;
        this.startingPoint = statingPoint;
        this.destination = destination;
        this.type = type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getStartingPoint() {
        return this.startingPoint;
    }

    @Override
    public Integer getDestination() {
        return this.destination;
    }

    @Override
    public WormHoleType getType() {
        return type;
    }
}
