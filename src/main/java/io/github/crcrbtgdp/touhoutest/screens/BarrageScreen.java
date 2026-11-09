package io.github.crcrbtgdp.touhoutest.screens;

import io.github.crcrbtgdp.touhoutest.GameLoop;
import io.github.crcrbtgdp.touhoutest.PlayerData;
import io.github.crcrbtgdp.touhoutest.barrage.BossMoving;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BarrageScreen extends JPanel {

    ImageIcon fran;
    ImageIcon reimu;
    Font font;
    URL[] imageUrl = {new File(GameLoop.jarFile + "/fran.png").toURI().toURL(), new File(GameLoop.jarFile + "reimu.png").toURI().toURL()};
    ImageObserver imageObserver;
    double[] starX = {0, 0.58779, -0.58779, 0.95106, -0.95106};
    double[] starY = {1, -0.80902, -0.80902, 0.30902, 0.30902};
    int[] xPoints = new int[10];
    int[] yPoints = new int[10];

    public BarrageScreen() throws MalformedURLException {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        fran = new ImageIcon(imageUrl[0]);
        reimu = new ImageIcon(imageUrl[1]);
        imageObserver = (img, infoflags, x, y, width, height) -> false;
        font = new Font("", Font.BOLD, PlayerData.windowSize/36);

        for (int i = 0; i < 10; i++) {
            xPoints[i] = (int)(Math.sin(i*Math.PI/5)*PlayerData.windowRatio*15*(i%2==0?(3-Math.sqrt(5))/2:1));
            yPoints[i] = (int)(Math.cos(i*Math.PI/5)*PlayerData.windowRatio*15*(i%2==0?(3-Math.sqrt(5))/2:1));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        if (GameLoop.totalFrames- GameLoop.pichu < 100 && GameLoop.pichu != -1) {
            int r = (GameLoop.totalFrames- GameLoop.pichu) * PlayerData.windowSize /30;
            g.fillOval((int)PlayerData.playerX-(r/2), (int)PlayerData.playerY-(r/2), r, r);
            if (GameLoop.totalFrames- GameLoop.pichu >= 40) {
                g.setColor(Color.BLACK);
                int r2 = (GameLoop.totalFrames- GameLoop.pichu-40) * PlayerData.windowSize /30;
                g.fillOval((int)PlayerData.playerX-(r2/2), (int)PlayerData.playerY-(r2/2), r2, r2);
            }
            if (GameLoop.totalFrames- GameLoop.pichu == 99) {
                GameLoop.pichu = -1;
            }
        }

        g.setColor(Color.RED);
        for (int i = 0; i < PlayerData.coordinates[0].length; i++) {
            g.fillOval((int)PlayerData.coordinates[0][i]-(int)(10*PlayerData.windowRatio), (int)PlayerData.coordinates[1][i]-(int)(10*PlayerData.windowRatio), (int)(20*PlayerData.windowRatio),(int)(20*PlayerData.windowRatio));
        }

        g.setColor(Color.BLUE);
        if (GameLoop.playerBullets[0] != null) {
            for (int i = 0; i < GameLoop.playerBullets[0].length; i++) {
                g.fillOval((int) GameLoop.playerBullets[0][i], (int) GameLoop.playerBullets[1][i], (int) (10 * PlayerData.windowRatio), (int) (10 * PlayerData.windowRatio));
            }
        }

        g.setColor(Color.DARK_GRAY);
        g.drawImage(reimu.getImage(), (int)PlayerData.playerX-(int)(25*PlayerData.windowRatio), (int)PlayerData.playerY-(int)(25*PlayerData.windowRatio), (int) (50*PlayerData.windowRatio), (int) (50*PlayerData.windowRatio), imageObserver);

        g.drawImage(fran.getImage(), (int) ((int) BossMoving.getBossCoordinateX()-(40*PlayerData.windowRatio)), (int) BossMoving.getBossCoordinateY()-(int) (40*PlayerData.windowRatio), (int) (80*PlayerData.windowRatio), (int) (80*PlayerData.windowRatio), imageObserver);
        g.fillRect(PlayerData.windowSize, 0, (int)(PlayerData.windowSize*0.33), PlayerData.windowSize);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Player", (int)(PlayerData.windowSize*1.02), PlayerData.windowSize/5);
        g.setColor(Color.RED);

        int[] xPoints2 = new int[10];
        int[] yPoints2 = new int[10];

        for (int i = 0; i < GameLoop.playerMax; i++) {
            for (int j = 0; j < 10; j++) {
                xPoints2[j] = xPoints[j] + (int) (PlayerData.windowSize * 1.14 + ((double) (i * PlayerData.windowSize) /36));
                yPoints2[j] = yPoints[j] + (int) (PlayerData.windowSize*0.19);
            }
            if (i < GameLoop.player) {
                g.fillPolygon(xPoints2, yPoints2, 10);
            } else {
                g.drawPolygon(xPoints2, yPoints2, 10);
            }
        }

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, (int)(PlayerData.windowRatio* GameLoop.bullets.bossHP/4), 5);
    }
}
