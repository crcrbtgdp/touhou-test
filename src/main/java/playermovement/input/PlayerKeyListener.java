package playermovement.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlayerKeyListener implements KeyListener {

    public HashMap<Integer, Boolean> pressingKey = new HashMap<>();
    private final Integer[] typeKeys2 = {KeyEvent.VK_X, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE};
    private final List<Integer> typeKeys ;

    public PlayerKeyListener() {
        pressingKey.put(KeyEvent.VK_Z, false);
        pressingKey.put(KeyEvent.VK_X, false);
        pressingKey.put(KeyEvent.VK_SHIFT, false);
        pressingKey.put(KeyEvent.VK_ENTER, false);
        pressingKey.put(KeyEvent.VK_ESCAPE, false);
        pressingKey.put(KeyEvent.VK_UP, false);
        pressingKey.put(KeyEvent.VK_DOWN, false);
        pressingKey.put(KeyEvent.VK_LEFT, false);
        pressingKey.put(KeyEvent.VK_RIGHT, false);
        typeKeys = Arrays.asList(typeKeys2);
    }

    public void reset() {
        for (int a : pressingKey.keySet()) {
            if (typeKeys.contains(a)) {
                pressingKey.replace(a, false);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!typeKeys.contains(e.getKeyCode())) {
            pressingKey.replace(e.getKeyCode(), true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!typeKeys.contains(e.getKeyCode())) {
            pressingKey.replace(e.getKeyCode(), false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (typeKeys.contains(e.getKeyCode())) {
            pressingKey.replace(e.getKeyCode(), true);
        }
    }
}
