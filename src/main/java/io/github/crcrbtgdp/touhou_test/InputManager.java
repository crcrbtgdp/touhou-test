package io.github.crcrbtgdp.touhou_test;

import java.util.EnumMap;
import java.util.HashMap;
import java.awt.event.KeyEvent;

public class InputManager {

    public static PlayerKeyListener keys;
    private static HashMap<Integer, Boolean> pressingKeys2;
    static EnumMap<UsedKey, Boolean> pressingKeys;

    static {
        keys = new PlayerKeyListener();
        pressingKeys = new EnumMap<>(UsedKey.class);
    }

    public static void update() {
        pressingKeys2 = new HashMap<>(keys.pressingKey);
        keys.reset();
        for (int a : pressingKeys2.keySet()) {
            switch (a) {
                case KeyEvent.VK_Z -> pressingKeys.put(UsedKey.VK_Z, pressingKeys2.get(a));
                case KeyEvent.VK_X -> pressingKeys.put(UsedKey.VK_X, pressingKeys2.get(a));
                case KeyEvent.VK_SHIFT -> pressingKeys.put(UsedKey.VK_SHIFT, pressingKeys2.get(a));
                case KeyEvent.VK_ENTER -> pressingKeys.put(UsedKey.VK_ENTER, pressingKeys2.get(a));
                case KeyEvent.VK_ESCAPE -> pressingKeys.put(UsedKey.VK_ESCAPE, pressingKeys2.get(a));
                case KeyEvent.VK_UP -> pressingKeys.put(UsedKey.VK_UP, pressingKeys2.get(a));
                case KeyEvent.VK_DOWN -> pressingKeys.put(UsedKey.VK_DOWN, pressingKeys2.get(a));
                case KeyEvent.VK_LEFT -> pressingKeys.put(UsedKey.VK_LEFT, pressingKeys2.get(a));
                case KeyEvent.VK_RIGHT -> pressingKeys.put(UsedKey.VK_RIGHT, pressingKeys2.get(a));
            }
        }
    }
}
