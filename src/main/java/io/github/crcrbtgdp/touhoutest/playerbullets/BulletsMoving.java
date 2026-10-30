package io.github.crcrbtgdp.touhoutest.playerbullets;

import io.github.crcrbtgdp.touhoutest.GameLoop;
import io.github.crcrbtgdp.touhoutest.GameScreen;
import io.github.crcrbtgdp.touhoutest.HitDetection;
import io.github.crcrbtgdp.touhoutest.barrage.BossMoving;

import java.util.ArrayList;

public class BulletsMoving {

    public int bossHP = 4000;
    ArrayList<Double> bulletsX = new ArrayList<>();
    ArrayList<Double> bulletsY = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();

    ArrayList<Double> homingBulletsX = new ArrayList<>();
    ArrayList<Double> homingBulletsY = new ArrayList<>();

    int vx = 1;
    ArrayList<Double> misalignment = new ArrayList<>();
    //double vy = GameScreen.windowSize / GameScreen.fps;
    ArrayList<Double> w = new ArrayList<>();
    ArrayList<Double> firstPlayerX = new ArrayList<>();

    public void generate() {
        bulletsX.add(0d/*GameScreen.playerX*/);
        bulletsY.add(0d);
        for (int i = 0; i < Math.floor(GameScreen.playerPower); i++) {
            double j = switch ((int) Math.floor(GameScreen.playerPower)) {
                case 1 -> 0;
                case 2 -> i==2?GameScreen.windowRatio*10:GameScreen.windowRatio*-10;
                default -> i<=2?(i==2?GameScreen.windowRatio*10:GameScreen.windowRatio*-10):(i==3?GameScreen.windowRatio*5:GameScreen.windowRatio*-5);
            };
            firstPlayerX.add(GameScreen.playerX + j);
            homingBulletsX.add(GameScreen.playerX + j);
            homingBulletsY.add(GameScreen.playerY + Math.abs(j)/2);
            misalignment.add(Math.abs(j)/2);
            //vy.add(BossMoving.getBossCoordinateY() - GameScreen.playerY);
            w.add(i<=2?0.001:GameScreen.windowRatio*30);
            time.add(0);
        }
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
        /*double[][] re2 = homingUpdate();
        double[][] re3 = new double[2][];
        re3[0] = new double[re[0].length + re2[0].length];
        re3[1] = new double[re[1].length + re2[1].length];
        System.arraycopy(re[0], 0, re3[0], 0, re[0].length);
        System.arraycopy(re2[0], 0, re3[0], re[0].length, re2[0].length);
        System.arraycopy(re[1], 0, re3[1], 0, re[1].length);
        System.arraycopy(re2[1], 0, re3[1], re[1].length, re2[1].length);*/
        return re/*3*/;
    }

    private double[][] homingUpdate() {
        double[][] re = new double[2][];
        for (int i = 0; i < Math.floor(GameScreen.playerPower); i++) {
            for (int j = homingBulletsX.size()-1; j >= 0 ; j--) {
                if (homingBulletsX.get(j) < 0 || homingBulletsX.get(j) >= GameScreen.windowSize || homingBulletsY.get(j) + GameScreen.playerY < 0 && homingBulletsY.get(j) + GameScreen.playerY >= GameScreen.windowSize) {
                    homingBulletsX.remove(j);
                    homingBulletsY.remove(j);
                    time.remove(j);
                    w.remove(j);
                    misalignment.remove(j);
                    firstPlayerX.remove(j);
                } else if (HitDetection.judgement(BossMoving.getBossCoordinateX(), BossMoving.getBossCoordinateY(), homingBulletsX.get(j)/* + GameScreen.playerX*/, homingBulletsY.get(j) + misalignment.get(j) + GameScreen.playerY, 40)) {
                    homingBulletsX.remove(j);
                    homingBulletsY.remove(j);
                    time.remove(j);
                    w.remove(j);
                    misalignment.remove(j);
                    firstPlayerX.remove(j);
                    bossHP--;
                }
            }
            for (int j = 0; j < 2; j++) {
                re[j] = new double[homingBulletsX.size()];
                for (int k = 0; k < homingBulletsX.size(); k++) {
                    double time2 = time.get(k) * w.get(k) / 500d;

                    //if (j == 1) {
                        homingBulletsX.set(k, (0.5 * time2 * vx - w.get(k)) * time2 + BossMoving.getBossCoordinateX() + ((firstPlayerX.get(k) - BossMoving.getBossCoordinateX()) * (time2 / (-2 * w.get(k)) +1)));
                        homingBulletsY.set(k, (BossMoving.getBossCoordinateY() - GameScreen.playerY) * time2 / (2 * w.get(k)));
                    //}
                    re[j][k] = (j == 0 ? homingBulletsX.get(k) : homingBulletsY.get(k) + GameScreen.playerY + misalignment.get(k));
                    time.set(k, time.get(k) + 2);
                }
            }
        }
        return re;
    }
}
