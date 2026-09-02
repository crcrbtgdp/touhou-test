package io.github.crcrbtgdp.touhou_test;

import javax.swing.JPanel;
import java.awt.*;

public class Screen extends JPanel {

    Screen() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        for (int i = 0; i < GameScreen.coordinates[0].length; i++) {
            g.fillOval((int)GameScreen.coordinates[0][i], (int)GameScreen.coordinates[1][i], 20,20);
        }

        g.setColor(Color.WHITE);
        g.fillOval((int)GameScreen.playerX, (int)GameScreen.playerY, (int) (50*GameScreen.windowRatio), (int) (50*GameScreen.windowRatio));
    }
}
