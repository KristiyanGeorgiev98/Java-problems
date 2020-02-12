package bg.sofia.uni.fmi.mjt.actors;

public class Minion implements Actor {
    private int level;
    private int health;
    private int mana;
    private int attack;
    private int defense;

    private int rawPosition;
    private int columnPosition;

    public Minion() {
        this.level = 1;
        this.health = 100;
        this.mana = 100;
        this.attack = 10;
        this.defense = 10;
        this.rawPosition = 0;
        this.columnPosition = 0;
    }

    public Minion(int level) {
        this.level = level;
        this.health = 50 * level;
        this.mana = 25 * level;
        this.attack = 10 * level;
        this.defense = 10 * level;
    }

    @Override
    public boolean isAlive() {
        return (health > 0);
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public int getRawPosition() {
        return rawPosition;
    }

    public void setRawPosition(int rawPosition) {
        this.rawPosition = rawPosition;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
