package io.github.crcrbtgdp.touhoutest.barrage;

import io.github.crcrbtgdp.touhoutest.GameScreen;

public class BossMoving {
    private double bossCoordinateX = (double) GameScreen.windowSize /2;
    private double bossCoordinateY = (double) GameScreen.windowSize /10;

    public double getBossCoordinateY() {
        return bossCoordinateY;
    }

    public void setBossCoordinateY(double bossCoordinateY) {
        this.bossCoordinateY = bossCoordinateY;
    }

    public double getBossCoordinateX() {
        return bossCoordinateX;
    }

    public void setBossCoordinateX(double bossCoordinateX) {
        this.bossCoordinateX = bossCoordinateX;
    }
}
