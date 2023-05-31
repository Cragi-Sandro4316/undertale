package entities;

import gui.Sprite;

public class AttackBar extends Entity {
    private int height;
    private int width;

    Sprite bar;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Sprite getBar() {
        return bar;
    }

    public AttackBar() {
        x = 10000;
        y = 0;
        speed = 7;
        height = 300;
        width = 30;

        bar = new Sprite("/resources/sprites/figlioDiPuttana.png", 0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void update() {
        x += speed;
    }



}
