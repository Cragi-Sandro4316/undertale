package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Sprite {
    BufferedImage sprite;
    int xPos, yPos;


    public Sprite(String path, int xPos, int yPos) {
        try {
            URL url = getClass().getResource(path);
            sprite = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.xPos = xPos;
        this.yPos = yPos;

    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void draw(Graphics2D g) {
        g.drawImage(sprite, xPos, yPos, sprite.getWidth(null), sprite.getHeight(null), null);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }
}
