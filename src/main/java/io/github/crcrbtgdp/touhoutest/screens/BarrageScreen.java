package io.github.crcrbtgdp.touhoutest.screens;

import io.github.crcrbtgdp.touhoutest.GameLoop;
import io.github.crcrbtgdp.touhoutest.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;

public class BarrageScreen extends JPanel {

    ImageIcon fran;
    ImageIcon reimu;
    URL[] imageUrl = {getClass().getResource("/フラン.png"), getClass().getResource("/霊夢.png")};
    ImageObserver imageObserver;

    public BarrageScreen() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        fran = new ImageIcon(imageUrl[0]);
        reimu = new ImageIcon((imageUrl[1]));
        imageObserver = (img, infoflags, x, y, width, height) -> false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.RED);
        for (int i = 0; i < GameScreen.coordinates[0].length; i++) {
            g.fillOval((int)GameScreen.coordinates[0][i]-10, (int)GameScreen.coordinates[1][i]-10, 20,20);
        }

        g.setColor(Color.WHITE);
        g.drawImage(reimu.getImage(), (int)GameScreen.playerX, (int)GameScreen.playerY, (int) (50*GameScreen.windowRatio), (int) (50*GameScreen.windowRatio), imageObserver);
        g.setColor(Color.RED);
        g.drawImage(fran.getImage(), (int) GameLoop.barrage.getFirst().bm.getBossCoordinateX()-25, (int) GameLoop.barrage.getFirst().bm.getBossCoordinateY()-25, 50, 50, imageObserver);
    }
}
