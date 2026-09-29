package io.github.crcrbtgdp.touhoutest.barrage.orbit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

class ReadFile {

    String read(String filename) throws IOException {
        URL url = getClass().getResource("/" + filename + ".txt");
        FileReader fr = new FileReader(url.getPath());
        String s = fr.readAllAsString();
        fr.close();
        return s;
    }


}
