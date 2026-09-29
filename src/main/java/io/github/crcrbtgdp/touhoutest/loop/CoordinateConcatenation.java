package io.github.crcrbtgdp.touhoutest.loop;

import io.github.crcrbtgdp.touhoutest.barrage.BarrageManager;

import java.util.ArrayList;

public class CoordinateConcatenation {

    public static double[][] concatenation(ArrayList<BarrageManager> barrage, int totalFrames) {
        int len = barrage.size();
        double[][][] barrages = new double[len][][];
        int n = 0;
        for (BarrageManager barrage2: barrage) {
            barrages[n] = barrage2.update((double)totalFrames/60);
            n++;
        }
        int len2 = 0;
        int[] len3 = new int[len];
        int[] len4 = new int[len];
        for (int i = 0; i < len; i++) {
            len2 += barrages[i][0].length;
            len3[i] = barrages[i][0].length;
            len4[i] = len2;
        }
        double[] coordinatesX = new double[len2];
        double[] coordinatesY = new double[len2];
        for (int i = 0; i < len; i++) {
            System.arraycopy(barrages[i][0], 0, coordinatesX, i==0?0:len4[i-1], len3[i]);
            System.arraycopy(barrages[i][1], 0, coordinatesY, i==0?0:len4[i-1], len3[i]);
        }
        return new double[][]{coordinatesX, coordinatesY};
    }
}
