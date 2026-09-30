package io.github.crcrbtgdp.touhoutest;

import io.github.crcrbtgdp.touhoutest.barrage.BarrageManager;
import io.github.crcrbtgdp.touhoutest.barrage.BossMoving;
import io.github.crcrbtgdp.touhoutest.loop.CoordinateConcatenation;
import io.github.crcrbtgdp.touhoutest.playerbullets.BulletsMoving;
import io.github.crcrbtgdp.touhoutest.playermovement.InputManager;
import io.github.crcrbtgdp.touhoutest.playermovement.PlayerMoving;
import io.github.crcrbtgdp.touhoutest.playermovement.UsedKey;
import io.github.crcrbtgdp.touhoutest.screens.BarrageScreen;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameLoop implements ActionListener {

    JFrame window;
    BarrageScreen barrageScreen;

    boolean hit;
    public static int pichu = -1;

    public static int player = 3;

    double bossX = (double) GameScreen.windowSize /2;
    double lastBossX = (double) GameScreen.windowSize /2;
    int lastBossFrame;

    File pichuun;
    AudioInputStream pichuunAudio;

    double[] lastPlayerCoordinate;

    public static int totalFrames = 0;
    public static ArrayList<BarrageManager> barrage = new ArrayList<>();

    public static BulletsMoving bullets;
    public static double[][] playerBullets;

    GameLoop(JFrame j, BarrageScreen sc) {
        window = j;
        barrageScreen = sc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (totalFrames == 0) {
            //window.setLayout(new GridBagLayout());
            //GridBagConstraints gbc = new GridBagConstraints();
            window.setSize((int)(GameScreen.windowSize*1.33), GameScreen.windowSize);
            window.add(barrageScreen);
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setFocusable(true);
            window.requestFocusInWindow();
            window.addKeyListener(InputManager.keys);
            try {
                File file = new File(getClass().getResource("/bgm.wav").toURI());
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
                //pichuun = new File(getClass().getResource("/pichuun.wav").toURI());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            bullets = new BulletsMoving();
        }

        if (totalFrames%((int)GameScreen.fps) == 0) {
            barrage.add(new BarrageManager((double)totalFrames/GameScreen.fps));
            barrage.getLast().generate();
            if (totalFrames >= GameScreen.fps*24) {
                barrage.removeFirst();
            }
        }

        if (totalFrames != 0 && totalFrames%((int)GameScreen.fps*3) >= GameScreen.fps) {
            if (totalFrames%((int)GameScreen.fps*3) == GameScreen.fps) {
                lastBossX = BossMoving.getBossCoordinateX();
                bossX = Math.random()*(double)GameScreen.windowSize;
                lastBossFrame = totalFrames;
                System.out.println(true);
            }
//            BossMoving.setBossCoordinateX((bossX-lastBossX)*(totalFrames-lastBossFrame)/120+lastBossX);
            BossMoving.setBossCoordinateX((bossX-lastBossX)*(Math.atan((totalFrames-lastBossFrame)/24d-2.5)/2d/Math.atan(2.5)+0.5)+lastBossX);
        }

        GameScreen.coordinates = CoordinateConcatenation.concatenation(barrage, totalFrames);

        //GameScreen.coordinates = barrage.update((double)(totalFrames%720)/60);

        double[] playerCoordinate = PlayerMoving.move(GameScreen.playerX, GameScreen.playerY);
        if (totalFrames != 0) {
            if (playerCoordinate[0] < GameScreen.windowSize-(25*GameScreen.windowRatio) && playerCoordinate[0] >= -25*GameScreen.windowRatio) {
                GameScreen.playerX = playerCoordinate[0];
            }
            if (playerCoordinate[1] < GameScreen.windowSize-(25*GameScreen.windowRatio) && playerCoordinate[1] >= -25*GameScreen.windowRatio) {
                GameScreen.playerY = playerCoordinate[1];
            }
        }

        if (InputManager.pressingKeys.get(UsedKey.VK_Z)) {
            bullets.generate();
        }
        playerBullets = bullets.update();

        InputManager.update();
        lastPlayerCoordinate = playerCoordinate;

        if (pichu == -1) {
            for (int i = 0; i < GameScreen.coordinates[0].length; i++) {
                boolean hit = HitDetection.judgement(playerCoordinate[0], playerCoordinate[1], GameScreen.coordinates[0][i], GameScreen.coordinates[1][i], 10);
                if (!this.hit && hit) {
                    player--;
                    this.hit = true;
                    try {
                        pichuunAudio = AudioSystem.getAudioInputStream(getClass().getResource("/pichuun.wav"));
                        pichu = totalFrames;
                        Clip clip = AudioSystem.getClip();
                        clip.open(pichuunAudio);
                        clip.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                if (i + 1 == GameScreen.coordinates[0].length) {
                    this.hit = hit;
                }
            }
        }
        if (player < 0) {
            System.exit(0);
        }

        barrageScreen.repaint();

        if (totalFrames%(int)GameScreen.fps == 0) {
            System.out.println(GameScreen.playerX);
        }
        if (bullets.bossHP <= 0) {
            System.exit(0);
        }
        totalFrames++;
    }
}
