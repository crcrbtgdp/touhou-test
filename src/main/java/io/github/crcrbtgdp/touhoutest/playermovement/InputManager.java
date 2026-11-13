package io.github.crcrbtgdp.touhoutest.playermovement;

import io.github.crcrbtgdp.touhoutest.playermovement.input.PlayerKeyListener;

import java.util.EnumMap;
import java.util.HashMap;
import java.awt.event.KeyEvent;

public class InputManager {

    public static PlayerKeyListener keys;
    public static final EnumMap<UsedKey, Boolean> pressingKeys = new EnumMap<>(UsedKey.class);

    static {
        keys = new PlayerKeyListener();
        for (UsedKey key : UsedKey.values()) {
            pressingKeys.put(key, false);
        }
    }

    public static void update() {
        var rawKeys = new HashMap<>(keys.pressingKey);
        keys.reset();
        for (UsedKey key : UsedKey.values()) {
            pressingKeys.put(key, rawKeys.get(keyCode(key)));
        }
    }

    private static int keyCode(UsedKey key) {
        return switch (key) {
            case VK_Z -> KeyEvent.VK_Z;
            case VK_X -> KeyEvent.VK_X;
            case VK_SHIFT -> KeyEvent.VK_SHIFT;
            case VK_ENTER -> KeyEvent.VK_ENTER;
            case VK_ESCAPE -> KeyEvent.VK_ESCAPE;
            case VK_UP -> KeyEvent.VK_UP;
            case VK_DOWN -> KeyEvent.VK_DOWN;
            case VK_LEFT -> KeyEvent.VK_LEFT;
            case VK_RIGHT -> KeyEvent.VK_RIGHT;
        };
    }
}
