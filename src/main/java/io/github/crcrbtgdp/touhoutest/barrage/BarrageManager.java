package io.github.crcrbtgdp.touhoutest.barrage;

import io.github.crcrbtgdp.touhoutest.barrage.orbit.Parser;

public class BarrageManager {

    BarrageMoving[] barrages = new BarrageMoving[20];
    public BossMoving bm;
    private double frames;
    private Parser parser;

    public BarrageManager(double frames) {
        parser = new Parser();
        this.frames = frames;
    }

    public void generate() {
        bm = new BossMoving();
        for (int i = 0; i < 20; i++) {
            barrages[i] = new BarrageMoving(bm, parser, i);
        }
    }

    public double[][] update(double t) {
        double[][] coordinates = new double[2][20];
        for (int i = 0; i < 20; i++) {
            double[] a = barrages[i].update(t-frames);
            //System.out.println(Double.toString(a[0]) + Double.toString(a[1]));
            coordinates[0][i] = a[0];
            coordinates[1][i] = a[1];
        }
        return coordinates;
    }
}
