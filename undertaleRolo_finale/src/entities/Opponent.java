package entities;

import gui.Sprite;

public class Opponent extends Entity {
    String name;
    Sprite sprite;
    int atk;
    int def;
    private int hp;

    public Opponent(String name) {
        this.name = name;
        sprite = new Sprite("/resources/sprites/rolo-sprite.png", 465, 30);
        atk = 10;
        def = 10;
        hp = 10000;
    }
    public void hit(int dmg) {
        hp -= dmg;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getHp() {
        return hp;
    }
}
