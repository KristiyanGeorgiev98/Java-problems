package bg.sofia.uni.fmi.mjt.actors;

import bg.sofia.uni.fmi.mjt.field.Treasure;

public class Player implements Actor {
    private int level;
    private int health;
    private int mana;
    private int attack;
    private int defense;
    private int experience;
    private int uniqueNumber;
    private Backpack playerBackpack;


    private int rawPosition;
    private int columnPosition;

    public Player() {
        this.level = 1;
        this.health = 100;
        this.mana = 100;
        this.attack = 50;
        this.defense = 50;
        this.experience = 0;
        this.uniqueNumber = 0;
        this.playerBackpack = new Backpack();
    }

    public void levelUp() {
        while (true) {
            if (experience > 100 * level) {
                ++level;
                health += 10;
                mana += 10;
                attack += 5;
                defense += 5;
                experience = 0;
            } else {
                break;
            }
        }
    }

    public int getUniqueNumber() {
        return uniqueNumber;
    }

    @Override
    public boolean isAlive() {
        return (health > 0);
    }

    public int getRawPosition() {
        return rawPosition;
    }

    public void setRawPosition(int rawPosition) {
        this.rawPosition = rawPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public void setUniqueNumber(int uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
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

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Backpack getPlayerBackpack() {
        return playerBackpack;
    }

    public void setTreasureInPlayerBackpack(Treasure treasure) {
        playerBackpack.setTreasureInBackpack(treasure);
    }
}
