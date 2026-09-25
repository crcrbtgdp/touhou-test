package io.github.crcrbtgdp.touhou_test.screens;

import io.github.crcrbtgdp.touhou_test.GameLoop;
import io.github.crcrbtgdp.touhou_test.GameScreen;
import io.github.crcrbtgdp.touhou_test.barrage.BarrageManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
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
        imageObserver = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };
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
        g.drawImage(fran.getImage(), (int) GameLoop.barrage.bm.getBossCoordinateX()-25, (int) GameLoop.barrage.bm.getBossCoordinateY()-25, 50, 50, imageObserver);
        //g.fillRect((int) GameLoop.barrage.bm.getBossCoordinateX(), (int) GameLoop.barrage.bm.getBossCoordinateY(),30, 30);
    }
}
