package io.github.crcrbtgdp.touhoutest;

import io.github.crcrbtgdp.touhoutest.playermovement.InputManager;
import io.github.crcrbtgdp.touhoutest.playermovement.PlayerMoving;
import io.github.crcrbtgdp.touhoutest.screens.BarrageScreen;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class GameScreen {
    void main() throws MalformedURLException {

        PlayerMoving.windowRatio = PlayerData.windowRatio;
        InputManager.update();

        JFrame window = new JFrame("東方");
        BarrageScreen screen = new BarrageScreen();

        ActionListener game = new GameLoop(window, screen);
        new Timer((int)(1000/PlayerData.fps), game).start();
    }
}
