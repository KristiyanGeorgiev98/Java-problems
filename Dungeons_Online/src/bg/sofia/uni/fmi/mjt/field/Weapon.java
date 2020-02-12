package bg.sofia.uni.fmi.mjt.field;

import java.util.Random;

public class Weapon extends Treasure {
    private static final int MULTIPLIER = 10;
    private int level;
    private int attack;
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
        int randomNumber = random.nextInt(25);
        return randomNumber;
    }

    Weapon() {
        this.type = "Weapon";
        this.level = 1;
        this.attack = randomNumberGenerator();
        super.setLevel(level);
        super.setType(type);
        super.setAttack(attack);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}
