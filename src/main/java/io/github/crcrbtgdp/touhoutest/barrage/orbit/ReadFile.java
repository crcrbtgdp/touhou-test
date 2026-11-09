package io.github.crcrbtgdp.touhoutest.barrage.orbit;

import io.github.crcrbtgdp.touhoutest.GameLoop;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class ReadFile {

    String read(String filename) throws IOException {
        FileReader fr = new FileReader(new File(GameLoop.jarFile + filename));
        String s = fr.readAllAsString();
        fr.close();
        return s;
    }


}
