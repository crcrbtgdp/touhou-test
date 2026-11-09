package io.github.crcrbtgdp.touhoutest;

import io.github.crcrbtgdp.touhoutest.barrage.BarrageManager;
import java.util.ArrayList;


public class SpellCard {

    int index = 0;
    int totalFrames = 0;

    void update(int totalFrames) {
        this.totalFrames = totalFrames;
    }

    void generate(ArrayList<BarrageManager> barrages) {
        barrages.add(new BarrageManager((double)totalFrames/PlayerData.fps));
        barrages.getLast().generate();
        if (totalFrames >= PlayerData.fps*8) {
            barrages.removeFirst();
        }
    }
}
