package org.sl.board;

import org.sl.model.WormHole;

public class BoardLocationInfo {

    private boolean hasWormHole;
    private WormHole wormHoleOnTheLocation;

    public BoardLocationInfo(boolean hasWormHole, WormHole wormHoleOnTheLocation) {
        this.hasWormHole = hasWormHole;
        this.wormHoleOnTheLocation = wormHoleOnTheLocation;
    }

    public boolean hasWormHole() {
        return hasWormHole;
    }

    public WormHole getWormHoleOnTheLocation() {
        return wormHoleOnTheLocation;
    }
}
