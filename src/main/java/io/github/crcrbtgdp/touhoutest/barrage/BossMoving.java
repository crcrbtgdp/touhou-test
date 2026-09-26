package io.github.crcrbtgdp.touhoutest.barrage;

import io.github.crcrbtgdp.touhoutest.GameScreen;

public class BossMoving {
    private static double bossCoordinateX = (double) GameScreen.windowSize /2;
    private static double bossCoordinateY = (double) GameScreen.windowSize /10;

    public static double getBossCoordinateY() {
        return bossCoordinateY;
    }

    public static void setBossCoordinateY(double bossCoordinateY) {
        BossMoving.bossCoordinateY = bossCoordinateY;
    }

    public static double getBossCoordinateX() {
        return bossCoordinateX;
    }

    public static void setBossCoordinateX(double bossCoordinateX) {
        BossMoving.bossCoordinateX = bossCoordinateX;
    }
}
