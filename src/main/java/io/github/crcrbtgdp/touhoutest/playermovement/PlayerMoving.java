package io.github.crcrbtgdp.touhoutest.playermovement;

import io.github.crcrbtgdp.touhoutest.PlayerData;

public class PlayerMoving {

    private static final float FPS = PlayerData.fps;
    private static final double FOCUSED_SPEED = 200;
    private static final double NORMAL_SPEED = 500;

    public static double windowRatio;

    public static double[] move(double playerX, double playerY) {
        double speed = InputManager.pressingKeys.get(UsedKey.VK_SHIFT) ? FOCUSED_SPEED : NORMAL_SPEED;
        double movement = speed / FPS * windowRatio;
        playerY += movementFor(UsedKey.VK_DOWN, UsedKey.VK_UP) * movement;
        playerX += movementFor(UsedKey.VK_RIGHT, UsedKey.VK_LEFT) * movement;
        return new double[]{playerX, playerY};
    }

    private static int movementFor(UsedKey positiveDirection, UsedKey negativeDirection) {
        return (InputManager.pressingKeys.get(positiveDirection) ? 1 : 0)
                - (InputManager.pressingKeys.get(negativeDirection) ? 1 : 0);
    }
}
