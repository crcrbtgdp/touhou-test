package io.github.crcrbtgdp.touhoutest.barrage;

import io.github.crcrbtgdp.touhoutest.barrage.orbit.Parser;

public class BarrageMoving {

    private final double theta;
    private BossMoving bm;
    private Parser parser;

    double[] firstBossCoordinate;

    BarrageMoving(BossMoving bm, Parser parser, int theta) {
        this.parser = parser;
        this.theta = Math.PI/5*theta;
        firstBossCoordinate = new double[]{BossMoving.getBossCoordinateX(), BossMoving.getBossCoordinateY()};
    }

    double[] update(double t) {
        t += 1;
        if (t < 1) {t = 1;}
        //double power = theta/t+1;
        return new double[]{parser.calculate(t,theta)[0]+firstBossCoordinate[0], parser.calculate(t,theta)[1]+firstBossCoordinate[1]};
        //return new double[]{t*Math.cos(power)*50+firstBossCoordinate[0], t*Math.sin(power)*50+firstBossCoordinate[1]};
    }
}
