package io.github.crcrbtgdp.touhoutest.barrage;

public class BarrageMoving {

    private final double theta;
    private BossMoving bm;

    double[] firstBossCoordinate;

    BarrageMoving(BossMoving bm, int theta) {
        this.theta = Math.PI/5*theta;
        firstBossCoordinate = new double[]{bm.getBossCoordinateX(), bm.getBossCoordinateY()};
    }

    double[] update(double t) {
        t += 1;
        if (t < 1) {t = 1;}
        double power = theta/t+1;
        return new double[]{t*Math.cos(power)*50+firstBossCoordinate[0], t*Math.sin(power)*50+firstBossCoordinate[0]};
    }
}
