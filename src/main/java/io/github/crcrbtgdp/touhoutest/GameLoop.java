package io.github.crcrbtgdp.touhoutest;

import io.github.crcrbtgdp.touhoutest.barrage.BarrageManager;
import io.github.crcrbtgdp.touhoutest.barrage.BossMoving;
import io.github.crcrbtgdp.touhoutest.playermovement.InputManager;
import io.github.crcrbtgdp.touhoutest.playermovement.PlayerMoving;
import io.github.crcrbtgdp.touhoutest.screens.BarrageScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameLoop implements ActionListener {

    JFrame window;
    BarrageScreen barrageScreen;

    int player = 3;

    double bossX = (double) GameScreen.windowSize /2;
    double lastBossX = (double) GameScreen.windowSize /2;
    int lastBossFrame;

    int totalFrames = 0;
    public static ArrayList<BarrageManager> barrage = new ArrayList<>();

    GameLoop(JFrame j, BarrageScreen sc) {
        window = j;
        barrageScreen = sc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (totalFrames == 0) {
            //window.setLayout(new GridBagLayout());
            //GridBagConstraints gbc = new GridBagConstraints();
            window.setSize(GameScreen.windowSize, GameScreen.windowSize);
            window.add(barrageScreen);
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setFocusable(true);
            window.requestFocusInWindow();
            window.addKeyListener(InputManager.keys);
        }



        if (totalFrames%180 == 0) {
            barrage.add(new BarrageManager((double)totalFrames/60));
            barrage.getLast().generate();
            if (totalFrames >= 1440) {
                barrage.removeFirst();
            }
        }

        if (totalFrames%180 >= 60) {
            if (totalFrames%180 == 60) {
                lastBossX = BossMoving.getBossCoordinateX();
                bossX = Math.random()*(double)GameScreen.windowSize;
                lastBossFrame = totalFrames;
                System.out.println(true);
            }
            BossMoving.setBossCoordinateX((bossX-lastBossX)*(totalFrames-lastBossFrame)/120+lastBossX);
        }

            int len = barrage.size();
        double[][][] barrages = new double[len][][];
        int n = 0;
        for (BarrageManager barrage2: barrage) {
            barrages[n] = barrage2.update((double)totalFrames/60);
            n++;
        }
        int len2 = 0;
        int[] len3 = new int[len];
        int[] len4 = new int[len];
        for (int i = 0; i < len; i++) {
            len2 += barrages[i][0].length;
            len3[i] = barrages[i][0].length;
            len4[i] = len2;
        }
        double[] coordinatesX = new double[len2];
        double[] coordinatesY = new double[len2];
        for (int i = 0; i < len; i++) {
            System.arraycopy(barrages[i][0], 0, coordinatesX, i==0?0:len4[i-1], len3[i]);
            System.arraycopy(barrages[i][1], 0, coordinatesY, i==0?0:len4[i-1], len3[i]);
        }
        GameScreen.coordinates = new double[][]{coordinatesX, coordinatesY};

        //GameScreen.coordinates = barrage.update((double)(totalFrames%720)/60);
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

        barrageScreen.repaint();

        if (totalFrames%60 == 0) {
            System.out.println(GameScreen.playerX);
        }

        totalFrames++;
    }
}
