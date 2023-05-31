package gui;

import entities.AttackBar;
import entities.Opponent;
import entities.Player;
import items.Act;
import items.Item;
import utilities.KeyHandler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

public class DialogueBox extends JPanel {
    private boolean hasBackground;
    private AttackBar attackBar;

    Sprite bgImage;


    private int dmg;


    public AttackBar getAttackBar() {
        return attackBar;
    }

    public DialogueBox() {
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.white, 4));
        this.setLocation(400, 320);
        this.setSize(300, 300);

        dmg = 0;

        attackBar = new AttackBar();

        hasBackground = false;
        bgImage = new Sprite("/resources/sprites/attack.png", 0, 0);
    }

    public void setBoxToOppontent() {
        hasBackground = false;
        this.setBorder(new LineBorder(Color.white, 4));
        this.setLocation(375, 295);
        this.setSize(350, 350);



        // rimuovo gli elementi precedenti
        this.removeAll();
        this.revalidate();


    }

    public void setBoxToDialogue(Graphics2D g2, String message) {
        // tolgo lo sfondo
        hasBackground = false;
        // setto la grandezza e la posizione
        this.setSize(700, 300);
        this.setLocation(200, 320);



        // scrivo il testo
        Font font = new Font("Papyrus", Font.ITALIC, 24);
        g2.setFont(font);
        g2.setColor(Color.white);
        g2.drawString(message, 300, 400);


        this.revalidate();
        this.repaint();

    }
    public void update(Graphics g) {
        this.paintComponent(g);
    }

    public int attackMenu(Graphics2D g) {
        // metto sfondo grandezza e posizione della scatola
        hasBackground = true;
        this.setSize(700, 300);
        this.setLocation(200, 320);

        // aggiorno la posizione di attackBar
        attackBar.setX(attackBar.getX() + attackBar.getSpeed());

        // se attackBar è prima della metà aumenta il danno, se è dopo diminuisce
        if(attackBar.getX() <= this.getWidth() / 2) {
            dmg++;
        }
        else {
            dmg--;
        }


        return dmg;

    }

    public void actMenu(Graphics2D g, Player player) {
        hasBackground = false;
        this.setSize(700, 300);
        this.setLocation(200, 320);
        this.removeAll();
        // scrivo il testo
        Font font = new Font("Papyrus", Font.ITALIC, 24);
        g.setFont(font);
        g.setColor(Color.white);
        int x = 300;
        int y = 365;
        for(int i = 0; i < player.getActs().length; i++) {
            g.drawString(player.getActs()[i].getName(), x, y);
            y += 40;
            if(x > 620) {
                y = 365;
                x += 300;
            }
        }


    }

    public void itemMenu(Graphics2D g, Player player) {
        hasBackground = false;
        this.setSize(700, 300);
        this.setLocation(200, 320);
        this.removeAll();
        // scrivo il testo
        Font font = new Font("Papyrus", Font.ITALIC, 24);
        g.setFont(font);
        g.setColor(Color.white);
        int x = 300;
        int y = 365;
        for(int i = 0; i < player.getItems().size(); i++) {
            g.drawString(player.getItems().get(i).getName(), x, y);
            y += 40;
            if(x > 620) {
                y = 365;
                x += 300;
            }
        }

    }

    public void mercyMenu(Graphics2D g, Player player) {
        hasBackground = false;
        this.setSize(700, 300);
        this.setLocation(200, 320);

        // rimuovo gli elementi precedenti
        this.removeAll();

        Font font = new Font("Papyrus", Font.ITALIC, 24);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("spare", 240, 345);
    }


    // ACTS
    public void check(Graphics g, Opponent opponent) {
        setBoxToDialogue((Graphics2D) g, "Rolando, def: " + opponent.getDef() + " attack: " + opponent.getAtk());
    }

    public void agita(Graphics g, Opponent opponent, Act[] acts) {
        setBoxToDialogue((Graphics2D)g, "Ti sei agitato, l'attacco di Rolando è aumentato!");
        opponent.setAtk(opponent.getAtk() + acts[1].getEnemAtkChange());
    }

    public void piritoSort(Graphics g) {
        setBoxToDialogue((Graphics2D)g, "merda..");
        ArrayList<String> pipito = new ArrayList<>();
        for(;;) {
            pipito.add("Ciao mi chiamo pipito! The big dick is back in town!");
        }
    }

    public void parla(Graphics g, Opponent opponent, Act[] acts) {
        setBoxToDialogue((Graphics2D)g, "Provi a parlare con Rolando, ma spari solo cazzate. L'attacco di Rolly è aumentato");
        opponent.setAtk(opponent.getAtk() + acts[3].getEnemAtkChange());
    }

    // ITEMS
    public void use(Item item, Player player, Graphics g) {
        if(player.getItems().contains(item)) {
            player.heal(item.getHeal());
            player.boostDef(item.getDefBoost());
            player.boostAtk(item.getAtkBoost());
            setBoxToDialogue((Graphics2D) g, "hai usato: " + item.getName());
        }
        else {
            setBoxToDialogue((Graphics2D) g, "item not existe");
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(hasBackground) {
            g2.drawImage(this.bgImage.getSprite(), 0, 0, this.getWidth(), this.getHeight(), this);

            g2.drawImage(attackBar.getBar().sprite, attackBar.getX(), attackBar.getY(), attackBar.getWidth(), attackBar.getHeight(), this);
        }
        else {
            this.setBackground(new Color(0, 0, 0, 0));
        }

    }
}
