package io.github.crcrbtgdp.touhoutest.screens;

import io.github.crcrbtgdp.touhoutest.GameLoop;
import io.github.crcrbtgdp.touhoutest.GameScreen;
import io.github.crcrbtgdp.touhoutest.barrage.BossMoving;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;

public class BarrageScreen extends JPanel {

    ImageIcon fran;
    ImageIcon reimu;
    Font font;
    URL[] imageUrl = {getClass().getResource("/フラン.png"), getClass().getResource("/霊夢.png")};
    ImageObserver imageObserver;
    double[] starX = {0, 0.58779, -0.58779, 0.95106, -0.95106};
    double[] starY = {1, -0.80902, -0.80902, 0.30902, 0.30902};
    int[] xPoints = new int[10];
    int[] yPoints = new int[10];

    public BarrageScreen() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        fran = new ImageIcon(imageUrl[0]);
        reimu = new ImageIcon((imageUrl[1]));
        imageObserver = (img, infoflags, x, y, width, height) -> false;
        font = new Font("", Font.BOLD, GameScreen.windowSize/36);

        for (int i = 0; i < 10; i++) {
            xPoints[i] = (int)(Math.sin(i*Math.PI/5)*GameScreen.windowRatio*15*(i%2==0?(3-Math.sqrt(5))/2:1));
            yPoints[i] = (int)(Math.cos(i*Math.PI/5)*GameScreen.windowRatio*15*(i%2==0?(3-Math.sqrt(5))/2:1));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        if (GameLoop.totalFrames-GameLoop.pichu < 100 && GameLoop.pichu != -1) {
            int r = (GameLoop.totalFrames-GameLoop.pichu) * GameScreen.windowSize /30;
            g.fillOval((int)GameScreen.playerX-(r/2), (int)GameScreen.playerY-(r/2), r, r);
            if (GameLoop.totalFrames-GameLoop.pichu >= 40) {
                g.setColor(Color.BLACK);
                int r2 = (GameLoop.totalFrames-GameLoop.pichu-40) * GameScreen.windowSize /30;
                g.fillOval((int)GameScreen.playerX-(r2/2), (int)GameScreen.playerY-(r2/2), r2, r2);
            }
            if (GameLoop.totalFrames-GameLoop.pichu == 99) {
                GameLoop.pichu = -1;
            }
        }

        g.setColor(Color.RED);
        for (int i = 0; i < GameScreen.coordinates[0].length; i++) {
            g.fillOval((int)GameScreen.coordinates[0][i]-(int)(10*GameScreen.windowRatio), (int)GameScreen.coordinates[1][i]-(int)(10*GameScreen.windowRatio), (int)(20*GameScreen.windowRatio),(int)(20*GameScreen.windowRatio));
        }

        g.setColor(Color.DARK_GRAY);
        g.drawImage(reimu.getImage(), (int)GameScreen.playerX-(int)(25*GameScreen.windowRatio), (int)GameScreen.playerY-(int)(25*GameScreen.windowRatio), (int) (50*GameScreen.windowRatio), (int) (50*GameScreen.windowRatio), imageObserver);

        g.drawImage(fran.getImage(), (int) ((int) BossMoving.getBossCoordinateX()-(25*GameScreen.windowRatio)), (int) BossMoving.getBossCoordinateY()-(int) (25*GameScreen.windowRatio), (int) (50*GameScreen.windowRatio), (int) (50*GameScreen.windowRatio), imageObserver);
        g.fillRect(GameScreen.windowSize, 0, (int)(GameScreen.windowSize*0.33), GameScreen.windowSize);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Player", (int)(GameScreen.windowSize*1.02), GameScreen.windowSize/5);
        g.setColor(Color.RED);

        int[] xPoints2 = new int[10];
        int[] yPoints2 = new int[10];

        for (int i = 0; i < GameLoop.player; i++) {
            for (int j = 0; j < 10; j++) {
                xPoints2[j] = xPoints[j] + (int) (GameScreen.windowSize * 1.14 + ((double) (i * GameScreen.windowSize) /36));
                yPoints2[j] = yPoints[j] + (int) (GameScreen.windowSize*0.19);
            }
            g.fillPolygon(xPoints2, yPoints2, 10);
        }


    }
}
