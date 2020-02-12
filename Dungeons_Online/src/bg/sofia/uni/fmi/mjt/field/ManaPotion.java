package bg.sofia.uni.fmi.mjt.field;

import java.util.Random;

public class ManaPotion extends Treasure {
    private int mana;
    private String type;

    int randomNumberGenerator() {
        Random random = new Random();
        int randomNumber = random.nextInt(70);
        return randomNumber;
    }

    ManaPotion() {
        this.type = "ManaPotion";
        this.mana = randomNumberGenerator();
        super.setType(type);
        super.setMana(mana);
    }

    public int useManaPotion() {
        return mana;
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
