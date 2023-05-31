package entities;

import gui.Sprite;

import java.awt.*;
import java.net.URL;

public class Projectile extends Entity {
    private Sprite sprite;
    private Rectangle hitbox;

    private float moveDecision1;
    private float moveDecision2;

    private boolean movesLeft, movesUp;

    public Projectile(String spritePath, int speed) {
        this.speed = speed;
        this.sprite = new Sprite(spritePath, 400, 320);
        x = 400;
        y = 320;
        hitbox = new Rectangle();
        hitbox.setBounds(x, y, sprite.getSprite().getWidth(), sprite.getSprite().getHeight());

        moveDecision1 = (float)Math.random();
        // decide casualmente se muoversi a sinistra o destra
        if(moveDecision1 <= 0.5) {
            movesLeft = false;
        }
        else {
            movesLeft = true;
        }

        moveDecision2 = (float)Math.random();
        // decide casualmente se muoversi a sinistra o destra
        if(moveDecision2 <= 0.5) {
            movesUp = false;
        }
        else {
            movesUp = true;
        }



    }

    public Sprite getSprite() {
        return sprite;
    }


    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }

    public void move() {

        if(movesLeft) {
            if(x > 380) {
                x -= speed;
                if(Math.random() < 0.025)
                    movesLeft = !movesLeft;
            }
            else {
                x += speed;
                movesLeft = false;
            }
        }
        else {
            if (x < 660) {
                x += speed;
                if(Math.random() < 0.025)
                    movesLeft = !movesLeft;

            }
            else {
                x -= speed;
                movesLeft = true;
            }
        }

        if(movesUp) {
            if(y > 300)
                y -= speed;
            else {
                y += speed;
                movesUp = false;

                if(Math.random() < 0.025)
                    movesUp = !movesUp;
            }
        }
        else {
            if(y < 590)
                y += speed;
            else {
                y -= speed;
                movesUp = true;

                if(Math.random() < 0.025)
                    movesUp = !movesUp;
            }
        }





        hitbox.setBounds(x, y, sprite.getSprite().getWidth(), sprite.getSprite().getHeight());

    }

}
