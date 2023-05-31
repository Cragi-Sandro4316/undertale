package items;

public class Act {
    private String name;

    private int enemAtkChange;
    private int enemDefChange;

    public String getName() {
        return name;
    }

    public int getEnemAtkChange() {
        return enemAtkChange;
    }

    public int getEnemDefChange() {
        return enemDefChange;
    }

    public Act(String name, int enemAtkChange, int enemDefChange) {
        this.name = name;
        this.enemAtkChange = enemAtkChange;
        this.enemDefChange = enemDefChange;
    }
}
