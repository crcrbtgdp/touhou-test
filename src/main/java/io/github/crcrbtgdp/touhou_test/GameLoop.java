package io.github.crcrbtgdp.touhou_test;

import io.github.crcrbtgdp.touhou_test.danmaku.BarrageManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop implements ActionListener {

    JFrame window;
    Screen screen;

    int player = 3;

    int totalFrames = 0;
    BarrageManager barrage;

    GameLoop(JFrame j, Screen sc) {
        window = j;
        screen = sc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (totalFrames == 0) {
            //window.setLayout(new GridBagLayout());
            //GridBagConstraints gbc = new GridBagConstraints();
            window.setSize(GameScreen.windowSize, GameScreen.windowSize);
            window.add(screen);
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setFocusable(true);
            window.requestFocusInWindow();
            window.addKeyListener(InputManager.keys);
        }

        if (totalFrames%720 == 0) {
            barrage = new BarrageManager();
            barrage.generate();
        }
        GameScreen.coordinates = barrage.update((double)(totalFrames%720)/60);
        double[] playerCoordinate = PlayerMoving.move(GameScreen.playerX, GameScreen.playerY);
        GameScreen.playerX = playerCoordinate[0];
        GameScreen.playerY = playerCoordinate[1];
        InputManager.update();

        for (int i = 0; i < GameScreen.coordinates[0].length; i++) {
            boolean hit = HitDetection.judgement(playerCoordinate[0], playerCoordinate[1], GameScreen.coordinates[0][i], GameScreen.coordinates[1][i]);
            if (hit) {
                player--;
                break;
            }
        }
        if (player < 0) {
            System.exit(0);
        }

        screen.repaint();

        if (totalFrames%60 == 0) {
            System.out.println(GameScreen.playerX);
        }

        totalFrames++;
    }
}
