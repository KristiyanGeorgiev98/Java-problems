package bg.sofia.uni.fmi.mjt.field;

import java.util.Random;

public class Spell extends Treasure {
    private static final int MULTIPLIER = 10;
    private int level;
    private int attack;
    private int manaCost;
    private String type;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    private int randomNumberGenerator() {
        Random random = new Random();
        int randomNumber = random.nextInt(15);
        return randomNumber;
    }

    Spell() {
        this.type = "Spell";
        this.attack = randomNumberGenerator();
        this.level = 1;
        this.manaCost = randomNumberGenerator();
        super.setLevel(1);
        super.setType(type);
        super.setAttack(attack);
        super.setManaCost(manaCost);
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
