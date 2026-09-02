import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import danmaku.BarrageManager;
import playermovement.InputManager;
import playermovement.PlayerMoving;

public class GameScreen {
    public static double[][] coordinates;
    float fps = 60;

    public static int windowSize = 720;
    public static double playerX = (double) windowSize /2;
    public static double playerY = (double) windowSize /2;
    public static final double windowRatio = (double) windowSize /1000;

    void main() {

        PlayerMoving.windowRatio = windowRatio;
        InputManager.update();

        JFrame window = new JFrame("東方");
        Screen screen = new Screen();

        ActionListener game = new GameLoop(window, screen);
        new Timer(17, game).start();
    }
}
