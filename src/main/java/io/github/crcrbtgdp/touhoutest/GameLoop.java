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
import java.nio.file.Files;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static io.github.crcrbtgdp.touhoutest.PlayerData.fps;

public class GameLoop implements ActionListener {

    private static final int PLAYER_SIZE = 25;
    private static final int BOSS_MOVE_MIN_DISTANCE = PlayerData.windowSize / 5;

    private final JFrame window;
    private final BarrageScreen barrageScreen;

    public static String jarFile;

    private SpellCard spell;

    static {
        try {
            jarFile = new File(GameScreen.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + File.separator + "assets" + File.separator;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hit;
    public static int pichu = -1;

    public static final int playerMax = 3;
    public static int player = playerMax;

    private double bossX = (double) PlayerData.windowSize / 2;
    private double lastBossX = (double) PlayerData.windowSize / 2;
    private int lastBossFrame;

    public static int totalFrames = 0;
    public static ArrayList<BarrageManager> barrage = new ArrayList<>();

    public static BulletsMoving bullets;
    public static double[][] playerBullets;

    public static double[][] playerHomingBullets;

    GameLoop(JFrame j, BarrageScreen sc) {
        window = j;
        barrageScreen = sc;
    }

    private int bossMoveTime;

    @Override
    public void actionPerformed(ActionEvent e) {


        if (totalFrames == 0) {
            initializeGame();
        }

        if (totalFrames%((int) fps) == 0) {
            spell.generate(barrage);
        }

        updateBossPosition();

        PlayerData.coordinates = CoordinateConcatenation.concatenation(barrage, totalFrames);

        //PlayerData.coordinates = barrage.update((double)(totalFrames%720)/60);

        double[] playerCoordinate = PlayerMoving.move(PlayerData.playerX, PlayerData.playerY);
        if (totalFrames != 0) {
            if (isInsidePlayArea(playerCoordinate[0])) {
                PlayerData.playerX = playerCoordinate[0];
            }
            if (isInsidePlayArea(playerCoordinate[1])) {
                PlayerData.playerY = playerCoordinate[1];
            }
        }

        if (InputManager.pressingKeys.get(UsedKey.VK_Z) && totalFrames % (int)(fps/30) == 0) {
            bullets.generate();
        }
        playerBullets = bullets.update();

        InputManager.update();
        if (pichu == -1) {
            checkPlayerHit(playerCoordinate);
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

    private void initializeGame() {
        spell = new SpellCard();
        window.setSize((int) (PlayerData.windowSize * 1.33), PlayerData.windowSize);
        window.add(barrageScreen);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setFocusable(true);
        window.requestFocusInWindow();
        window.addKeyListener(InputManager.keys);
        playAudio("bgm.wav");
        bullets = new BulletsMoving();

        try {
            bossMoveTime = Integer.parseInt(Files.readString(new File(jarFile, "bossmove.txt").toPath()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateBossPosition() {
        int moveDurationFrames = (int) fps * bossMoveTime;
        if (bossMoveTime == 0 || totalFrames == 0 || totalFrames % moveDurationFrames < fps) {
            return;
        }
        if (totalFrames % moveDurationFrames == fps) {
            lastBossX = BossMoving.getBossCoordinateX();
            do {
                bossX = Math.random() * PlayerData.windowSize;
            } while (Math.abs(lastBossX - bossX) < BOSS_MOVE_MIN_DISTANCE);
            lastBossFrame = totalFrames;
        }
        double progress = Math.atan((totalFrames - lastBossFrame) / 24d - 2.5) / (2d * Math.atan(2.5)) + 0.5;
        BossMoving.setBossCoordinateX((bossX - lastBossX) * progress + lastBossX);
    }

    private boolean isInsidePlayArea(double coordinate) {
        double margin = PLAYER_SIZE * PlayerData.windowRatio;
        return coordinate < PlayerData.windowSize - margin && coordinate >= -margin;
    }

    private void checkPlayerHit(double[] playerCoordinate) {
        for (int i = 0; i < PlayerData.coordinates[0].length; i++) {
            boolean collides = HitDetection.judgement(playerCoordinate[0], playerCoordinate[1],
                    PlayerData.coordinates[0][i], PlayerData.coordinates[1][i], 10);
            if (!hit && collides) {
                player--;
                hit = true;
                pichu = totalFrames;
                playAudio("pichuun.wav");
                return;
            }
            if (i + 1 == PlayerData.coordinates[0].length) {
                hit = collides;
            }
        }
    }

    private void playAudio(String filename) {
        try (AudioInputStream audio = AudioSystem.getAudioInputStream(new File(jarFile, filename))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception ex) {
            if (!(ex instanceof NullPointerException)) {
                ex.printStackTrace();
            }
        }
    }
}
