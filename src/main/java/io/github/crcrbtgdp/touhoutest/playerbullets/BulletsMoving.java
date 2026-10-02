package io.github.crcrbtgdp.touhoutest.playerbullets;

import io.github.crcrbtgdp.touhoutest.GameScreen;
import io.github.crcrbtgdp.touhoutest.HitDetection;
import io.github.crcrbtgdp.touhoutest.barrage.BossMoving;

import java.util.ArrayList;

public class BulletsMoving {

    public int bossHP = 4000;
    ArrayList<Double> bulletsX = new ArrayList<>();
    ArrayList<Double> bulletsY = new ArrayList<>();
    ArrayList<Double> time = new ArrayList<>();

    ArrayList<Double> homingBulletsX = new ArrayList<>();
    ArrayList<Double> homingBulletsY = new ArrayList<>();

    public void generate() {
        bulletsX.add(0d/*GameScreen.playerX*/);
        bulletsY.add(0d);
        time.add(0d);
        homingBulletsX.add(GameScreen.playerX);
        homingBulletsY.add(GameScreen.playerY);
    }

    public double[][] update() {
        double[][] re = new double[2][];
        for (int j = 0; j < bulletsX.size(); j++) {
            if (bulletsX.get(j) < 0 || bulletsX.get(j) >= GameScreen.windowSize || bulletsY.get(j) < 0 && bulletsY.get(j) >= GameScreen.windowSize) {
                bulletsX.remove(j);
                bulletsY.remove(j);
            } else if (HitDetection.judgement(BossMoving.getBossCoordinateX(), BossMoving.getBossCoordinateY(), bulletsX.get(j)+GameScreen.playerX, bulletsY.get(j)+GameScreen.playerY, 40)) {
                bulletsX.remove(j);
                bulletsY.remove(j);
                bossHP -= 4;
            }
        }
        for (int i = 0; i < 2; i++) {
            re[i] = new double[bulletsX.size()];
            for (int j = 0; j < bulletsX.size(); j++) {
                if (i==1) {
                    bulletsY.set(j, bulletsY.get(j) - GameScreen.windowSize / GameScreen.fps);
                }
                re[i][j] = (i==0?bulletsX.get(j)+GameScreen.playerX:bulletsY.get(j)+GameScreen.playerY);

            }
        }
        return re;
    }

    public double[][] homingUpdate() {
        double[][] re = new double[2][];
        for (int j = 0; j < homingBulletsX.size(); j++) {
            if (homingBulletsX.get(j) < 0 || homingBulletsX.get(j) >= GameScreen.windowSize || homingBulletsY.get(j) < 0 && homingBulletsY.get(j) >= GameScreen.windowSize) {
                homingBulletsX.remove(j);
                homingBulletsY.remove(j);
                time.remove(j);
            } else if (HitDetection.judgement(BossMoving.getBossCoordinateX(), BossMoving.getBossCoordinateY(), bulletsX.get(j) + GameScreen.playerX, bulletsY.get(j) + GameScreen.playerY, 40)) {
                homingBulletsX.remove(j);
                homingBulletsY.remove(j);
                time.remove(j);
                bossHP--;
            }
        }
        for (int i = 0; i < 2; i++) {
            re[i] = new double[homingBulletsX.size()];
            for (int j = 0; j < homingBulletsX.size(); j++) {
                if (i == 1) {
                    homingBulletsY.set(j, homingBulletsY.get(j) - GameScreen.windowSize / GameScreen.fps);
                }
                re[i][j] = (i == 0 ? homingBulletsX.get(j) + GameScreen.playerX : homingBulletsY.get(j) + GameScreen.playerY);

            }
        }
        return re;
    }
}
