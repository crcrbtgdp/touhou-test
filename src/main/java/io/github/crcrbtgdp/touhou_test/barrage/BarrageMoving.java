package io.github.crcrbtgdp.touhou_test.barrage;

public class BarrageMoving {

    private final double theta;
    private BossMoving bm;

    BarrageMoving(BossMoving bm, int theta) {
        this.theta = Math.PI/5*theta;
        this.bm = bm;
    }

    double[] update(double t) {
        t -= 1;
        if (t < 1) {t = 1;}
        double power = theta/t+1;
        return new double[]{t*Math.cos(power)*50+bm.getBossCoordinateX(), t*Math.sin(power)*50+bm.getBossCoordinateY()};
    }
}
