package danmaku;

public class BarrageManager {

    BarrageMoving[] barrages = new BarrageMoving[20];

    public void generate() {
        for (int i = 0; i < 20; i++) {
            barrages[i] = new BarrageMoving(i);
        }
    }

    public double[][] update(double t) {
        double[][] coordinates = new double[2][20];
        for (int i = 0; i < 20; i++) {
            double[] a = barrages[i].update(t);
            coordinates[0][i] = a[0];
            coordinates[1][i] = a[1];
        }
        return coordinates;
    }
}
