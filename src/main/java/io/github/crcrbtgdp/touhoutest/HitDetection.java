package io.github.crcrbtgdp.touhoutest;

public class HitDetection {
    public static boolean judgement(double x1, double y1, double x2, double y2, double r) {
        double deltaX = x1 - x2;
        double deltaY = y1 - y2;
        double scaledRadius = r * PlayerData.windowRatio;
        return deltaX * deltaX + deltaY * deltaY <= scaledRadius * scaledRadius;
    }
}
