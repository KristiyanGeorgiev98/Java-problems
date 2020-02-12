package bg.sofia.uni.fmi.mjt.field;

import java.util.Random;

public class HealthPotion extends Treasure {
    private int health;
    private String type;

    int randomNumberGenerator() {
        Random random = new Random();
        int randomNumber = random.nextInt(70);
        return randomNumber;
    }

    HealthPotion() {
        this.type = "HealthPotion";
        this.health = randomNumberGenerator();
        super.setType(type);
        super.setMana(health);
    }

    public int useManaPotion() {
        return health;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
