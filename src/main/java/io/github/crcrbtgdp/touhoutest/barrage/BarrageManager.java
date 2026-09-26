package io.github.crcrbtgdp.touhoutest.barrage;

public class BarrageManager {

    BarrageMoving[] barrages = new BarrageMoving[20];
    public BossMoving bm;
    private double frames;

    public BarrageManager(double frames) {
        this.frames = frames;
    }

    public void generate() {
        bm = new BossMoving();
        for (int i = 0; i < 20; i++) {
            barrages[i] = new BarrageMoving(bm, i);
        }
    }

    public double[][] update(double t) {
        double[][] coordinates = new double[2][20];
        for (int i = 0; i < 20; i++) {
            double[] a = barrages[i].update(t-frames);
            coordinates[0][i] = a[0];
            coordinates[1][i] = a[1];
        }
        return coordinates;
    }
}
