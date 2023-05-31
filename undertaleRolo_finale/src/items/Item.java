package items;

public class Item {
    private String name;
    private int heal;
    private int defBoost;
    private int atkBoost;

    public Item(String name, int heal, int defBoost, int atkBoost) {
        this.name = name;
        this.heal = heal;
        this.defBoost = defBoost;
        this.atkBoost = atkBoost;
        if (atkBoost==666) {
            gameover();
        }

    }


    static Item catenina=new Item("Catenina",0,20,0);
    static Item penna=new Item("Penna",20,0,0);
    static Item palla=new Item("Palla da Basket",0,0,20);
    static Item lgbt=new Item("Bandiera Lgbt",0,-20,20);
    static Item cappello=new Item("Cappello",0,20,-20);
    static Item ragazzina=new Item("Ragazzina",666,666,666);

    public static Item getCatenina() {
        return catenina;
    }
    public static Item getPenna() {
        return penna;
    }
    public static Item getPalla() {
        return palla;
    }
    public static Item getBandiera() {
        return lgbt;
    }
    public static Item getCappello() {
        return cappello;
    }
    public static Item getRagazzina() {
        return ragazzina;
    }

    public String getName() {
        return name;
    }

    public int getHeal() {
        return heal;
    }

    public int getDefBoost() {
        return defBoost;
    }

    public int getAtkBoost() {
        return atkBoost;
    }

    public static void gameover() {
        //game over
    }
}
