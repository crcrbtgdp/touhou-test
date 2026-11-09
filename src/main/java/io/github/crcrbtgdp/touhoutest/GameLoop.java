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
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static io.github.crcrbtgdp.touhoutest.PlayerData.fps;

public class GameLoop implements ActionListener {

    JFrame window;
    BarrageScreen barrageScreen;

    public static String jarFile;

    SpellCard spell;

    static {
        try {
            jarFile = new File(GameScreen.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + File.separator + "assets" + File.separator;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    boolean hit;
    public static int pichu = -1;

    public static final int playerMax = 3;
    public static int player = playerMax;

    double bossX = (double) PlayerData.windowSize /2;
    double lastBossX = (double) PlayerData.windowSize /2;
    int lastBossFrame;

    File pichuun;
    AudioInputStream pichuunAudio;

    double[] lastPlayerCoordinate;

    public static int totalFrames = 0;
    public static ArrayList<BarrageManager> barrage = new ArrayList<>();

    public static BulletsMoving bullets;
    public static double[][] playerBullets;

    public static double[][] playerHomingBullets;

    GameLoop(JFrame j, BarrageScreen sc) {
        window = j;
        barrageScreen = sc;
    }

    int bossMoveTime;

    @Override
    public void actionPerformed(ActionEvent e) {


        if (totalFrames == 0) {
            spell = new SpellCard();
            //window.setLayout(new GridBagLayout());
            //GridBagConstraints gbc = new GridBagConstraints();
            window.setSize((int)(PlayerData.windowSize*1.33), PlayerData.windowSize);
            window.add(barrageScreen);
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setFocusable(true);
            window.requestFocusInWindow();
            window.addKeyListener(InputManager.keys);
            try {
                File file = new File(jarFile + "bgm.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();

            } catch (Exception ex) {
                if (!ex.toString().contains("NullPointerException")) {
                    ex.printStackTrace();
                }
            }
            bullets = new BulletsMoving();
            try {
                bossMoveTime = Integer.parseInt(new FileReader(new File(GameLoop.jarFile + "bossmove.txt")).readAllAsString());
            } catch (IOException ex) {ex.printStackTrace();}
        }

        if (totalFrames%((int) fps) == 0) {
            spell.generate(barrage);
        }

        if (bossMoveTime != 0 && (totalFrames != 0 && totalFrames%((int) fps*bossMoveTime) >= fps)) {
            if (totalFrames%((int) fps*bossMoveTime) == fps) {
                lastBossX = BossMoving.getBossCoordinateX();
                while (Math.abs(lastBossX-bossX) < PlayerData.windowSize/5) {
                    bossX = Math.random() * (double) PlayerData.windowSize;
                }
                lastBossFrame = totalFrames;
                System.out.println(true);
            }
//            BossMoving.setBossCoordinateX((bossX-lastBossX)*(totalFrames-lastBossFrame)/120+lastBossX);
            BossMoving.setBossCoordinateX((bossX-lastBossX)*(Math.atan((totalFrames-lastBossFrame)/24d-2.5)/2d/Math.atan(2.5)+0.5)+lastBossX);
        }

        PlayerData.coordinates = CoordinateConcatenation.concatenation(barrage, totalFrames);

        //PlayerData.coordinates = barrage.update((double)(totalFrames%720)/60);

        double[] playerCoordinate = PlayerMoving.move(PlayerData.playerX, PlayerData.playerY);
        if (totalFrames != 0) {
            if (playerCoordinate[0] < PlayerData.windowSize-(25*PlayerData.windowRatio) && playerCoordinate[0] >= -25*PlayerData.windowRatio) {
                PlayerData.playerX = playerCoordinate[0];
            }
            if (playerCoordinate[1] < PlayerData.windowSize-(25*PlayerData.windowRatio) && playerCoordinate[1] >= -25*PlayerData.windowRatio) {
                PlayerData.playerY = playerCoordinate[1];
            }
        }

        if (InputManager.pressingKeys.get(UsedKey.VK_Z) && totalFrames % (int)(fps/30) == 0) {
            bullets.generate();
        }
        playerBullets = bullets.update();

        InputManager.update();
        lastPlayerCoordinate = playerCoordinate;

        if (pichu == -1) {
            for (int i = 0; i < PlayerData.coordinates[0].length; i++) {
                boolean hit = HitDetection.judgement(playerCoordinate[0], playerCoordinate[1], PlayerData.coordinates[0][i], PlayerData.coordinates[1][i], 10);
                if (!this.hit && hit) {
                    player--;
                    this.hit = true;
                    try {
                        pichuunAudio = AudioSystem.getAudioInputStream(new File(jarFile + "pichuun.wav"));
                        pichu = totalFrames;
                        Clip clip = AudioSystem.getClip();
                        clip.open(pichuunAudio);
                        clip.start();
                    } catch (Exception ex) {
                        if (!ex.toString().contains("NullPointerException")) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                }
                if (i + 1 == PlayerData.coordinates[0].length) {
                    this.hit = hit;
                }
            }
        }
        if (player < 0) {
            System.exit(0);
        }

        barrageScreen.repaint();

        if (totalFrames%(int) fps == 0) {
            System.out.println(PlayerData.playerX);
        }
        if (bullets.bossHP <= 0) {
            System.exit(0);
        }
        totalFrames++;
        spell.update(totalFrames);
    }
}
