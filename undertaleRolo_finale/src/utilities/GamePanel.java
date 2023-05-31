package utilities;

import entities.Opponent;
import entities.Player;
import entities.Projectile;
import gui.DialogueBox;
import gui.Sprite;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GamePanel extends JPanel implements Runnable {
    // screen settings
    private final int SCREEN_WIDTH = 1100;
    private final int SCREEN_HEIGHT = 800;

    int FPS = 60; // frame al secondo
    Thread gameThread;

    KeyHandler keyH = new KeyHandler();

    // variabile da passare per i draw
    Graphics2D g2;

    // ui elements
    DialogueBox box;

    // bottoni
    Sprite fight;
    Sprite act;
    Sprite items;
    Sprite mercy;
    // bottoni
    Sprite fightSelected;
    Sprite actSelected;
    Sprite itemsSelected;
    Sprite mercySelected;

    Player player;
    Projectile prog1;
    Projectile prog2;
    private boolean drawOppHP = false;
    private boolean attacked = false;
    private long hitTime = 0;
    private long turnTime = 0;


    Opponent opponent;

    // vero se turno del player, falso se turno dell'avversario
    boolean turn;


    private boolean selected;


    private int menuSelection;
    private int buttonMenuSelection;
    private boolean menuSelected;


    private boolean upState, downState, leftState, rightState, zState, xState;
    private boolean prevUpState, prevDownState, prevLeftState, prevRightState, prevZState, prevXState;


    public GamePanel() {

        // setto il gamePanel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setLayout(null);

        // turno dell'avversario
        turn = true;

        menuSelection = 0;

        // aggiungo la scatola
        box = new DialogueBox();
        this.add(box);

        // setto i bottoni

        fight = new Sprite("/resources/sprites/fight.png", 40, 700);
        act = new Sprite("/resources/sprites/act.png", 300, 700);
        items = new Sprite("/resources/sprites/item.png",560, 700);
        mercy = new Sprite("/resources/sprites/mercy.png", 820, 700);

        fightSelected = new Sprite("/resources/sprites/fightSelected.png", 40, 700);
        actSelected = new Sprite("/resources/sprites/actSelected.png", 300, 700);
        itemsSelected = new Sprite("/resources/sprites/itemSelected.png",560, 700);
        mercySelected = new Sprite("/resources/sprites/mercySelected.png", 820, 700);


        // aggiungo il player
        player = new Player();

        prog1 = new Projectile("/resources/sprites/java.png", 5);
        prog2 = new Projectile("/resources/sprites/java.png", 7);


        // aggiungo il nemico
        opponent = new Opponent("Rolando");


        box.getAttackBar().setX(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime; //64 bit + lungo


        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1) {
                // prendo stato precedente e attuale dei tasti
                prevUpState = upState;
                upState = keyH.isUp();

                prevDownState = downState;
                downState = keyH.isDown();

                prevLeftState = leftState;
                leftState = keyH.isLeft();

                prevRightState = rightState;
                rightState = keyH.isRight();

                prevZState = zState;
                zState = keyH.isZ();

                prevXState = xState;
                xState = keyH.isX();

                // chiamo update e ridisegno tutto 
                update();
                repaint();
                delta--;

            }


        }


    }

    // funzione chiamata nel loop per aggiornare le cose sullo schermo prima di ridisegnarle
    public void update() {

        // se il turno è del giocatore il cuore si muove in tutta la scatola, altrimenti si muove solo nel menù
        if(turn == false) {
            if (keyH.isUp() == true) {
                if(player.getY() > 300)
                    player.setY(player.getY() - player.getSpeed());
            }
            else if (keyH.isDown() == true) {
                if(player.getY() < 595)
                    player.setY(player.getY() + player.getSpeed());

            }
            if(keyH.isRight() == true) {
                if(player.getX() < 673)
                    player.setX(player.getX() + player.getSpeed());

            }
            else if (keyH.isLeft() == true) {
                if(player.getX() > 380)
                    player.setX(player.getX() - player.getSpeed());

            }



            // se il player è ancora vivo può essere colpito e i proiettili continuano a muoversi
            if(player.getHp() > 0) {
                prog1.move();
                prog2.move();
                // se la hurtbox di pritio si interseca con l'hitbox di test stampa qualcosa
                if(player.getHitbox().intersects(prog1.getHitbox()) || player.getHitbox().intersects(prog2.getHitbox())) {
                    // se sei già stato colpito aspetta un numero molto carino prima di ricolpirti
                    if(System.currentTimeMillis() - player.getLastHit() > 420) {
                        player.damage(5);
                    }
                }
            }

            if(System.currentTimeMillis() > turnTime + 10000) {
                turn = true;
                selected = false;
                attacked = false;

            }

        }
        else {
            // se non è selezionata nessuna opzione può spostare il cursore
            if(!selected) {
                if(rightState == true && prevRightState != rightState) {
                    menuSelection++;
                    if(menuSelection > 3)
                        menuSelection = 0;

                }
                else if (leftState == true && prevLeftState != leftState) {
                    menuSelection--;
                    if(menuSelection < 0)
                        menuSelection = 3;

                }

                if(prevZState != zState && zState == true) {
                    selected = true;
                }

            }
            else {
                switch(menuSelection) {
                    // fight
                    case 0:
                        if(!attacked) {
                            // prendo il danno inflitto
                            int dmgInflicted = box.attackMenu(g2) * player.getAtk() - opponent.getDef();
                            // se la barra raggiunge la fine il danno sarà 0, resetto selected e do il turno all'avversario
                            if (box.getAttackBar().getX() == 700) {
                                box.getAttackBar().setX(0);
                                opponent.hit(dmgInflicted);
                                drawOppHP = true;
                                hitTime = System.currentTimeMillis();
                                turnTime = System.currentTimeMillis();
                                attacked = true;
                            }
                            // se premo z durante l'attacco (metodo fronte di salita del cuneo) infliggo il danno, resetto selected
                            // e do il turno all'avversario
                            else if (prevZState != zState && zState == true) {
                                box.getAttackBar().setX(0);
                                opponent.hit(dmgInflicted);
                                drawOppHP = true;
                                hitTime = System.currentTimeMillis();

                                turnTime = System.currentTimeMillis();
                                attacked = true;

                            }
                        }
                        break;
                    // act
                    case 1:
                        if(!menuSelected) {
                            if(upState == true && prevUpState != upState) {
                                buttonMenuSelection--;
                                if(buttonMenuSelection < 0)
                                    buttonMenuSelection = 0;

                            }
                            else if (downState == true && prevDownState != downState) {
                                buttonMenuSelection++;
                                if(buttonMenuSelection > player.getActs().length - 1)
                                    buttonMenuSelection = player.getActs().length - 1;

                            }
                            if(prevZState != zState && zState == true) {
                                menuSelected = true;
                                turnTime = System.currentTimeMillis();
                            }
                            // se premo x (annulla) torno indietro
                            else if(prevXState != xState && xState == true) {
                                selected = false;
                            }
                        }


                        break;
                    // item
                    case 2:
                        if(!menuSelected) {
                            if(upState == true && prevUpState != upState) {
                                buttonMenuSelection--;
                                if(buttonMenuSelection < 0)
                                    buttonMenuSelection = 0;

                            }
                            else if (downState == true && prevDownState != downState) {
                                buttonMenuSelection++;
                                if(buttonMenuSelection > player.getItems().size() - 1)
                                    buttonMenuSelection = player.getItems().size() - 1;

                            }
                            if(prevZState != zState && zState == true) {
                                menuSelected = true;
                                turnTime = System.currentTimeMillis();
                            }
                            // se premo x (annulla) torno indietro
                            else if(prevXState != xState && xState == true) {
                                selected = false;
                            }
                        }
                        break;
                    // mercy
                    case 3:
                        if(!menuSelected) {
                            if(prevZState != zState && zState == true) {
                                menuSelected = true;
                                turnTime = System.currentTimeMillis();
                            }
                            // se premo x (annulla) torno indietro
                            else if(prevXState != xState && xState == true) {
                                selected = false;
                            }
                        }
                        break;

                }
            }

        }




    }

    // funzione che va effettivamente a disegnare tutto
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        // qua sotto si disegna tutto

        // nemico
        opponent.getSprite().draw(g2);

        // disegno i bottoni
        g2.drawImage(fight.getSprite(), 40, 700, 250, 90, this);
        g2.drawImage(act.getSprite(), 300, 700, 250, 80, this);
        g2.drawImage(items.getSprite(), 560, 700, 250, 80, this);
        g2.drawImage(mercy.getSprite(), 820, 700, 250, 80, this);

        // disegno la barra rossa sotto la barra della vita
        g2.setColor(Color.red);
        g2.fillRect(400, 650, 104 * 3, 30);

        // disegno la barra della vita
        g2.setColor(Color.YELLOW);
        g2.fillRect(400, 650, player.getHp() * 3, 30);

        // disegno le stats
        g2.setColor(Color.white);
        g2.setFont(new Font("", Font.BOLD, 26));
        g2.drawString("" + player.getName() + " Lv: " + player.getLv() + " Hp: " + player.getHp(), 90, 672);


        if (!turn) {
            box.setBoxToOppontent();
            // disegno il player
            g2.drawImage(player.getSprite().getSprite(), player.getX(), player.getY(), 48, 48, this);

            // disegno i proiettili
            g2.drawImage(prog1.getSprite().getSprite(), prog1.getX(), prog1.getY(), prog1.getSprite().getSprite().getWidth(), prog1.getSprite().getSprite().getHeight(), this);
            g2.drawImage(prog2.getSprite().getSprite(), prog2.getX(), prog2.getY(), prog2.getSprite().getSprite().getWidth(), prog2.getSprite().getSprite().getHeight(), this);

        } else {
            player.setX(450);
            player.setY(450);

            // disegno pirito sui pulsanti a seconda del pulsante selezionato
            switch (menuSelection) {
                case 0:
                    g2.drawImage(fightSelected.getSprite(), 40, 700, 250, 90, this);
                    g2.drawImage(player.getSprite().getSprite(), 50, 715, 48, 48, this);
                    break;
                case 1:
                    g2.drawImage(actSelected.getSprite(), 300, 700, 250, 80, this);
                    g2.drawImage(player.getSprite().getSprite(), 310, 715, 48, 48, this);
                    break;
                case 2:
                    g2.drawImage(itemsSelected.getSprite(), 560, 700, 250, 80, this);
                    g2.drawImage(player.getSprite().getSprite(), 570, 715, 48, 48, this);
                    break;
                case 3:
                    g2.drawImage(mercySelected.getSprite(), 820, 700, 250, 80, this);
                    g2.drawImage(player.getSprite().getSprite(), 830, 715, 48, 48, this);
                    break;


            }

            if (menuSelected) {
                switch (menuSelection) {
                    case 0:
                        break;
                    case 1:
                        switch (buttonMenuSelection) {
                            case 0:
                                g2.setColor(Color.black);
                                g2.fillRect(200, 320, 700, 300);
                                box.check(g2, opponent);
                                break;
                            case 1:
                                g2.setColor(Color.black);
                                g2.fillRect(200, 320, 700, 300);
                                box.agita(g2, opponent, player.getActs());
                                break;
                            case 2:
                                g2.setColor(Color.black);
                                g2.fillRect(200, 320, 700, 300);
                                box.piritoSort(g2);
                                break;
                            case 3:
                                g2.setColor(Color.black);
                                g2.fillRect(200, 320, 700, 300);
                                box.parla(g2, opponent, player.getActs());
                                break;

                        }
                        break;
                    case 2:
                        box.use(player.getItems().get(buttonMenuSelection), player, g2);
                        break;
                    case 4:
                        break;
                }
                if (System.currentTimeMillis() > turnTime + 1000) {
                    turn = false;
                    menuSelected = false;
                }
            }

            if (selected && !menuSelected) {
                switch (menuSelection) {
                    case 0:

                        break;
                    case 1:
                        switch (buttonMenuSelection) {
                            case 0:
                                g2.drawImage(player.getSprite().getSprite(), 240, 345, 48, 48, this);
                                break;
                            case 1:
                                g2.drawImage(player.getSprite().getSprite(), 240, 375, 48, 48, this);
                                break;
                            case 2:
                                g2.drawImage(player.getSprite().getSprite(), 240, 405, 48, 48, this);
                                break;
                            case 3:
                                g2.drawImage(player.getSprite().getSprite(), 240, 445, 48, 48, this);
                                break;
                        }
                        box.actMenu(g2, player);
                        break;
                    case 2:
                        switch (buttonMenuSelection) {
                            case 0:
                                g2.drawImage(player.getSprite().getSprite(), 240, 345, 48, 48, this);
                                break;
                            case 1:
                                g2.drawImage(player.getSprite().getSprite(), 240, 375, 48, 48, this);
                                break;
                            case 2:
                                g2.drawImage(player.getSprite().getSprite(), 240, 405, 48, 48, this);
                                break;
                            case 3:
                                g2.drawImage(player.getSprite().getSprite(), 240, 445, 48, 48, this);
                                break;
                            case 4:
                                g2.drawImage(player.getSprite().getSprite(), 240, 485, 48, 48, this);
                                break;
                            case 5:
                                g2.drawImage(player.getSprite().getSprite(), 240, 525, 48, 48, this);
                                break;
                        }
                        box.itemMenu(g2, player);
                        break;
                    case 3:
                        box.mercyMenu(g2, player);
                        break;
                }

                // premuto z, se non è selezionato niente, seleziona il pulsante su quale è pirito
                if (prevZState != zState && zState == true) {
                    if (!selected) {
                        switch (menuSelection) {
                            case 0:
                                selected = true;
                                break;
                            case 1:
                                selected = true;
                                box.actMenu(g2, player);
                                break;
                            case 2:
                                selected = true;
                                box.itemMenu(g2, player);
                                break;
                            case 3:
                                selected = true;
                                box.mercyMenu(g2, player);
                                break;

                        }
                    }

                }
                // se si ha attaccato il nemico, disegna la barra degli hp del nemico
                if (drawOppHP) {
                    g2.setColor(Color.GRAY);
                    g2.fillRect(opponent.getSprite().getxPos(), opponent.getSprite().getyPos() + 100, 250, 30);
                    g2.setColor(Color.CYAN);
                    g2.fillRect(opponent.getSprite().getxPos(), opponent.getSprite().getyPos() + 100, opponent.getHp() / 40, 30);

                    // viusalizzo la barra per mezzo secondo e poi do il turno al nemico
                    if (System.currentTimeMillis() > hitTime + 500) {
                        turn = false;
                        drawOppHP = false;
                        selected = false;
                        attacked = true;

                    }

                }


            }


            // se il player è morto fa partire il game over
            if (player.getHp() <= 0) {
                g2.setColor(Color.black);
                g2.fillRect(player.getX(), player.getY(), 48, 48);
                Image image;
                try {
                    image = new ImageIcon(new URL("https://media.discordapp.net/attachments/1113511396148252813/1113511425621643336/pipituxDeath.gif")).getImage();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                g2.drawImage(image, player.getX() - 5, player.getY() - 5, 81, 86, this);


                if (System.currentTimeMillis() > player.getLastHit() + 2000) {
                    g2.setColor(Color.red);
                    g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                    this.removeAll();
                    g2.setColor(Color.yellow);
                    g2.setFont(new Font("times", Font.BOLD, 50));
                    g2.drawString("GAME OVER", (SCREEN_WIDTH / 2) - 150, SCREEN_HEIGHT / 2);
                    try {
                        Image logo = new ImageIcon(new URL("https://thumbs.dreamstime.com/b/vector-illustration-flat-design-template-communist-logo-formed-hammer-sickle-editable-colors-pixels-perfect-230475762.jpg")).getImage();
                        g2.drawImage(logo, (SCREEN_WIDTH / 2) - 100, 150, 200, 150, this);
                    } catch (MalformedURLException e) {}
                    g2.drawImage(opponent.getSprite().getSprite(), 200, 500, 600, 200, this);
                }
                gameThread = null;
            }

            // se il nemico è morto fa partire la win screen
            if (opponent.getHp() <= 0) {
                g2.setColor(Color.black);
                g2.fillRect(player.getX(), player.getY(), 48, 48);

                g2.setColor(Color.red);
                g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                this.removeAll();
                g2.setColor(Color.yellow);
                g2.setFont(new Font("times", Font.BOLD, 50));
                g2.drawString("YOU WIN", (SCREEN_WIDTH / 2) - 150, SCREEN_HEIGHT / 2);
                try {
                    Image logo = new ImageIcon(new URL("https://media.istockphoto.com/id/1254557637/photo/concept-of-lgbt-tolerance-burning-rainbow-flag-of-lgbt-on-fire-flames-background-blackened.jpg?s=1024x1024&w=is&k=20&c=MYnEQGa90TKObcD4m5svn4sIpswCkJG8NUzNk6Tqveo=")).getImage();
                    g2.drawImage(logo, (SCREEN_WIDTH / 2) - 250, 100, 500, 250, this);

                } catch (MalformedURLException e) {
                }
                g2.drawImage(player.getSprite().getSprite(), 200, 500, 600, 200, this);

                gameThread = null;
            }

        }
    }

}
