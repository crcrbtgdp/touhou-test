import javax.swing.*;
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

        ActionListener game = new ActionListener() {
            JFrame window = new JFrame("東方");

            Screen screen = new Screen();


            int totalFrames = 0;
            BarrageManager barrage;



            int player = 3;

            public void actionPerformed(ActionEvent e) {
                if (totalFrames == 0) {
                    window.setSize(windowSize,windowSize);
                    window.add(screen);
                    window.setVisible(true);
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    window.setFocusable(true);
                    window.requestFocusInWindow();
                    window.addKeyListener(InputManager.keys);
                }

                if (totalFrames%720 == 0) {
                    barrage = new BarrageManager();
                    barrage.generate();
                }
                GameScreen.coordinates = barrage.update((double)(totalFrames%720)/60);
                double[] playerCoordinate = PlayerMoving.move(playerX, playerY);
                playerX = playerCoordinate[0];
                playerY = playerCoordinate[1];
                InputManager.update();

                for (int i = 0; i < coordinates[0].length; i++) {
                    boolean hit = HitDetection.judgement(playerCoordinate[0], playerCoordinate[1], coordinates[0][i], coordinates[1][i]);
                    if (hit) {
                        player--;
                        break;
                    }
                }
                if (player < 0) {
                    System.exit(0);
                }

                screen.repaint();

                if (totalFrames%60 == 0) {
                    System.out.println(playerX);
                }

                    totalFrames++;
            }
        };
        new Timer(17, game).start();
    }
}
