package org.sl.game;

public class Player {

    private final String playerId;
    private Integer location;

    public Player(String playerId, Integer location){
        this.playerId = playerId;
        this.location = location;
    }

    public String getPlayerId(){
        return playerId;
    }

    public Integer getLocation(){
        return location;
    }

    public void setLocation(Integer newLocation){
        this.location = newLocation;
    }

}
