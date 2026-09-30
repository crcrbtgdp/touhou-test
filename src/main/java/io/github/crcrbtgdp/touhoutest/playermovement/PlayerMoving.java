package io.github.crcrbtgdp.touhoutest.playermovement;

import io.github.crcrbtgdp.touhoutest.GameScreen;

public class PlayerMoving {

    static float fps = GameScreen.fps;//後で消さない

    public static double windowRatio;

    public static double[] move(double playerX, double playerY) {
        if (InputManager.pressingKeys.get(UsedKey.VK_SHIFT)) {
            if (InputManager.pressingKeys.get(UsedKey.VK_UP)) {
                playerY -= 200/fps*windowRatio;
            } if (InputManager.pressingKeys.get(UsedKey.VK_DOWN)) {
                playerY += 200/fps*windowRatio;
            } if (InputManager.pressingKeys.get(UsedKey.VK_LEFT)) {
                playerX -= 200/fps*windowRatio;
            } if (InputManager.pressingKeys.get(UsedKey.VK_RIGHT)) {
                playerX += 200/fps*windowRatio;
            }
        } else {
            if (InputManager.pressingKeys.get(UsedKey.VK_UP)) {
                playerY -= 500/fps*windowRatio;
            } if (InputManager.pressingKeys.get(UsedKey.VK_DOWN)) {
                playerY += 500/fps*windowRatio;
            } if (InputManager.pressingKeys.get(UsedKey.VK_LEFT)) {
                playerX -= 500/fps*windowRatio;
            } if (InputManager.pressingKeys.get(UsedKey.VK_RIGHT)) {
                playerX += 500/fps*windowRatio;
            }
        }
        return new double[]{playerX, playerY};
    }
}
