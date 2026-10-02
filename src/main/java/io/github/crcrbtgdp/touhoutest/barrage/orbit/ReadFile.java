package io.github.crcrbtgdp.touhoutest.barrage.orbit;

import io.github.crcrbtgdp.touhoutest.GameLoop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

class ReadFile {

    String read(String filename) throws IOException {
        FileReader fr = new FileReader(new File(GameLoop.jarFile + filename));
        String s = fr.readAllAsString();
        fr.close();
        return s;
    }


}
