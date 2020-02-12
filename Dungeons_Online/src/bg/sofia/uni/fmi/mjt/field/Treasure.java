package bg.sofia.uni.fmi.mjt.field;

public class Treasure {

    //required parameters
    private int rawPosition;
    private int columnPosition;

    //optional parameters
    private int health;
    private int mana;
    private int manaCost;
    private int level;
    private int attack;
    private String type;

    public static TreasureBuilder builder(int rawPosition, int columnPosition) {
        return new TreasureBuilder(rawPosition, columnPosition);
    }

    private Treasure(TreasureBuilder builder) {
        this.health = builder.health;
        this.manaCost = builder.mana;
        this.level = builder.level;
        this.attack = builder.attack;
        this.mana = builder.mana;
        type = " ";
    }


    public Treasure() {
        this.rawPosition = 0;
        this.columnPosition = 0;
        this.health = 0;
        this.manaCost = 0;
        this.level = 0;
        this.attack = 0;
        this.mana = 0;

    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public int getRawPosition() {
        return rawPosition;
    }

    public void setRawPosition(int rawPosition) {
        this.rawPosition = rawPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

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

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //builder class
    public static class TreasureBuilder {
        //required parameters
        private int rawPosition;
        private int columnPosition;

        //optional parameters
        private int health;
        private int mana;
        private int level;
        private int attack;
        private int manaCost;

        private TreasureBuilder(int rawPosition, int columnPosition) {
            this.rawPosition = rawPosition;
            this.columnPosition = columnPosition;
        }

        public Treasure build() {
            return new Treasure(this);
        }


        public void setHealth(int health) {
            this.health = health;
        }

        public void setMana(int mana) {
            this.mana = mana;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setManaCost(int manaCost) {
            this.manaCost = manaCost;
        }

        public void setAttack(int attack) {
            this.attack = attack;
        }
    }

}
