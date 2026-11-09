package io.github.crcrbtgdp.touhoutest.playerbullets;

import io.github.crcrbtgdp.touhoutest.HitDetection;
import io.github.crcrbtgdp.touhoutest.PlayerData;
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
    //double vy = PlayerData.windowSize / PlayerData.fps;
    ArrayList<Double> w = new ArrayList<>();
    ArrayList<Double> firstPlayerX = new ArrayList<>();

    public void generate() {
        bulletsX.add(0d/*PlayerData.playerX*/);
        bulletsY.add(0d);
        for (int i = 0; i < Math.floor(PlayerData.playerPower); i++) {
            double j = switch ((int) Math.floor(PlayerData.playerPower)) {
                case 1 -> 0;
                case 2 -> i==2?PlayerData.windowRatio*10:PlayerData.windowRatio*-10;
                default -> i<=2?(i==2?PlayerData.windowRatio*10:PlayerData.windowRatio*-10):(i==3?PlayerData.windowRatio*5:PlayerData.windowRatio*-5);
            };
            firstPlayerX.add(PlayerData.playerX + j);
            homingBulletsX.add(PlayerData.playerX + j);
            homingBulletsY.add(PlayerData.playerY + Math.abs(j)/2);
            misalignment.add(Math.abs(j)/2);
            //vy.add(BossMoving.getBossCoordinateY() - PlayerData.playerY);
            w.add(i<=2?0.001:PlayerData.windowRatio*30);
            time.add(0);
        }
    }

    public double[][] update() {
        double[][] re = new double[2][];
        for (int j = 0; j < bulletsX.size(); j++) {
            if (bulletsX.get(j) < 0 || bulletsX.get(j) >= PlayerData.windowSize || bulletsY.get(j) < 0 && bulletsY.get(j) >= PlayerData.windowSize) {
                bulletsX.remove(j);
                bulletsY.remove(j);
            } else if (HitDetection.judgement(BossMoving.getBossCoordinateX(), BossMoving.getBossCoordinateY(), bulletsX.get(j)+PlayerData.playerX, bulletsY.get(j)+PlayerData.playerY, 40)) {
                bulletsX.remove(j);
                bulletsY.remove(j);
                bossHP -= 4;
            }
        }
        for (int i = 0; i < 2; i++) {
            re[i] = new double[bulletsX.size()];
            for (int j = 0; j < bulletsX.size(); j++) {
                if (i==1) {
                    bulletsY.set(j, bulletsY.get(j) - PlayerData.windowSize / PlayerData.fps);
                }
                re[i][j] = (i==0?bulletsX.get(j)+PlayerData.playerX:bulletsY.get(j)+PlayerData.playerY);

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
        for (int i = 0; i < Math.floor(PlayerData.playerPower); i++) {
            for (int j = homingBulletsX.size()-1; j >= 0 ; j--) {
                if (homingBulletsX.get(j) < 0 || homingBulletsX.get(j) >= PlayerData.windowSize || homingBulletsY.get(j) + PlayerData.playerY < 0 && homingBulletsY.get(j) + PlayerData.playerY >= PlayerData.windowSize) {
                    homingBulletsX.remove(j);
                    homingBulletsY.remove(j);
                    time.remove(j);
                    w.remove(j);
                    misalignment.remove(j);
                    firstPlayerX.remove(j);
                } else if (HitDetection.judgement(BossMoving.getBossCoordinateX(), BossMoving.getBossCoordinateY(), homingBulletsX.get(j)/* + PlayerData.playerX*/, homingBulletsY.get(j) + misalignment.get(j) + PlayerData.playerY, 40)) {
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
                        homingBulletsY.set(k, (BossMoving.getBossCoordinateY() - PlayerData.playerY) * time2 / (2 * w.get(k)));
                    //}
                    re[j][k] = (j == 0 ? homingBulletsX.get(k) : homingBulletsY.get(k) + PlayerData.playerY + misalignment.get(k));
                    time.set(k, time.get(k) + 2);
                }
            }
        }
        return re;
    }
}
