package io.github.crcrbtgdp.touhoutest;

public class HitDetection {
    public static boolean judgement(double x1, double y1, double x2, double y2, double r) {
        return Math.sqrt((x1-x2)*(x1-x2) + ((y1-y2)*(y1-y2))) <= r*GameScreen.windowRatio;
    }
}
