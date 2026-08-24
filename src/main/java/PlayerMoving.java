public class PlayerMoving {

    float fps = 60f;//後で消す

    public double[] move(double playerX, double playerY) {
        if (InputManager.pressingKeys.get(UsedKey.VK_SHIFT)) {
            if (InputManager.pressingKeys.get(UsedKey.VK_UP)) {
                playerY -= 250/fps;
            } if (InputManager.pressingKeys.get(UsedKey.VK_DOWN)) {
                playerY += 250/fps;
            } if (InputManager.pressingKeys.get(UsedKey.VK_LEFT)) {
                playerX -= 250/fps;
            } if (InputManager.pressingKeys.get(UsedKey.VK_RIGHT)) {
                playerX += 250/fps;
            }
        } else {
            if (InputManager.pressingKeys.get(UsedKey.VK_UP)) {
                playerY -= 750/fps;
            } if (InputManager.pressingKeys.get(UsedKey.VK_DOWN)) {
                playerY += 750/fps;
            } if (InputManager.pressingKeys.get(UsedKey.VK_LEFT)) {
                playerX -= 750/fps;
            } if (InputManager.pressingKeys.get(UsedKey.VK_RIGHT)) {
                playerX += 750/fps;
            }
        }
        return new double[]{playerX, playerY};
    }
}
