package io.github.crcrbtgdp.touhoutest.barrage;

import io.github.crcrbtgdp.touhoutest.GameScreen;
import io.github.crcrbtgdp.touhoutest.PlayerData;

public class BossMoving {
    private static double bossCoordinateX = (double) PlayerData.windowSize /2;
    private static double bossCoordinateY = (double) PlayerData.windowSize /10;

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
