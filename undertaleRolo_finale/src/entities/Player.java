package entities;

import gui.Sprite;
import items.Act;
import items.Item;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {
    private int hp;
    private int lv;
    private String name;
    Sprite sprite;
    private int atk;
    private int def;

    Act[] acts = new Act[4];
    ArrayList<Item> items = new ArrayList<Item>();

    Rectangle hitbox;
    private long lastHit;
    private long nextHit;
    public Act[] getActs() {
        return acts;
    }

    public ArrayList<Item> getItems() {
        return items;
    }



    public Player() {
        hp = 104;
        lv = 10;
        y = 400;
        x = 450;
        atk = 20;
        def = 3;
        name = "Pipito";
        speed = 5;

        lastHit = 0;


        sprite = new Sprite("/resources/sprites/pitux.jpg", 465, 30);

        hitbox = new Rectangle();
        hitbox.setBounds(x, y, 48, 48);

        acts = new Act[4];
        acts[0] = new Act("CHECK", 5, 0);
        acts[1] = new Act("AGITA", 5, 0);
        acts[2] = new Act("ESEGUI PIRITOSORT();", 5, 0);
        acts[3] = new Act("PARLA", 5, 0);

        items.add(Item.getCatenina());
        items.add(Item.getPenna());
        items.add(Item.getPalla());
        items.add(Item.getBandiera());
        items.add(Item.getCappello());
        items.add(Item.getRagazzina());

    }

    public int getLv() {
        return lv;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void setX(int x) {
        this.x = x;
        hitbox.setBounds(x, y, 48, 48);
    }

    public int getAtk() {
        return atk;
    }

    @Override
    public void setY(int y) {
        this.y = y;
        hitbox.setBounds(x, y, 48, 48);
    }

    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }

    public void damage(int damage) {
        if(hp - damage > 0) {
            hp -= damage;
        }
        else {
            hp = 0;
        }
        lastHit = System.currentTimeMillis();

    }


    public long getLastHit() {
        return lastHit;
    }

    public void heal(int heal) {
        if(this.hp + heal <= 104) {
            hp += heal;
        }
        else {
            hp = 104;
        }
    }
    public void boostAtk(int boost) {
        atk += boost;
    }

    public void boostDef(int boost) {
        def += boost;
    }
}
