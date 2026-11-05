package io.github.crcrbtgdp.touhoutest.barrage;

import io.github.crcrbtgdp.touhoutest.GameLoop;
import io.github.crcrbtgdp.touhoutest.barrage.orbit.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BarrageManager {
    int[] r;

    BarrageMoving[] barrages;
    public BossMoving bm;
    private double frames;
    private Parser parser;

    public BarrageManager(double frames) {
        try {
            FileReader fr = new FileReader(new File(GameLoop.jarFile + "r.txt"));
            List<String> str = fr.readAllLines();
            r = new int[str.size()];
            for (int i = 0; i < str.size(); i++) {
                r[i] = Integer.parseInt(str.get(i));
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        barrages = new BarrageMoving[r[0]];
        parser = new Parser();
        this.frames = frames;
    }

    public void generate() {
        bm = new BossMoving();
        for (int i = 0; i < r[0]; i++) {
            barrages[i] = new BarrageMoving(bm, parser, Math.PI*2*i/r[0]);
        }
    }

    public double[][] update(double t) {
        double[][] coordinates = new double[2][r[0]];
        for (int i = 0; i < r[0]; i++) {
            double[] a = barrages[i].update(t-frames);
            //System.out.println(Double.toString(a[0]) + Double.toString(a[1]));
            coordinates[0][i] = a[0];
            coordinates[1][i] = a[1];
        }
        return coordinates;
    }
}
