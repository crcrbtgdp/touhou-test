package io.github.crcrbtgdp.touhoutest;

import io.github.crcrbtgdp.touhoutest.playermovement.InputManager;
import io.github.crcrbtgdp.touhoutest.playermovement.PlayerMoving;
import io.github.crcrbtgdp.touhoutest.screens.BarrageScreen;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class GameScreen {
    public static double[][] coordinates;
    public static float fps = 60;

    public static int windowSize = 720;
    public static double playerX = (double) windowSize /2;
    public static double playerY = (double) windowSize /2;
    public static final double windowRatio = (double) windowSize /1000;

    void main() throws MalformedURLException {

        PlayerMoving.windowRatio = windowRatio;
        InputManager.update();

        JFrame window = new JFrame("東方");
        BarrageScreen screen = new BarrageScreen();

        ActionListener game = new GameLoop(window, screen);
        new Timer((int)(1000/fps), game).start();
    }
}
