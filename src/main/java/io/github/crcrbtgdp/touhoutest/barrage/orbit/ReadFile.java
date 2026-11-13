package io.github.crcrbtgdp.touhoutest.barrage.orbit;

import io.github.crcrbtgdp.touhoutest.GameLoop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class ReadFile {

    String read(String filename) throws IOException {
        return Files.readString(new File(GameLoop.jarFile, filename).toPath());
    }


}
