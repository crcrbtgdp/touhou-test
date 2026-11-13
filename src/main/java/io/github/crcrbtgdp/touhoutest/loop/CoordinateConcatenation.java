package io.github.crcrbtgdp.touhoutest.loop;

import io.github.crcrbtgdp.touhoutest.PlayerData;
import io.github.crcrbtgdp.touhoutest.barrage.BarrageManager;

import java.util.ArrayList;

public class CoordinateConcatenation {

    public static double[][] concatenation(ArrayList<BarrageManager> barrage, int totalFrames) {
        int len = barrage.size();
        double[][][] barrages = new double[len][][];
        int n = 0;
        for (BarrageManager barrage2: barrage) {
            barrages[n] = barrage2.update((double)totalFrames/ PlayerData.fps);
            n++;
        }
        int coordinateCount = 0;
        for (int i = 0; i < len; i++) {
            coordinateCount += barrages[i][0].length;
        }
        double[] coordinatesX = new double[coordinateCount];
        double[] coordinatesY = new double[coordinateCount];
        int offset = 0;
        for (int i = 0; i < len; i++) {
            int barrageSize = barrages[i][0].length;
            System.arraycopy(barrages[i][0], 0, coordinatesX, offset, barrageSize);
            System.arraycopy(barrages[i][1], 0, coordinatesY, offset, barrageSize);
            offset += barrageSize;
        }
        return new double[][]{coordinatesX, coordinatesY};
    }
}
