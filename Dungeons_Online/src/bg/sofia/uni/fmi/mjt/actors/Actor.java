package bg.sofia.uni.fmi.mjt.actors;

public interface Actor {

    boolean isAlive();

    int getColumnPosition();

    void setColumnPosition(int columnPosition);

    int getRawPosition();

    void setRawPosition(int rawPosition);

    int getLevel();

    void setLevel(int level);

    int getMana();

    void setMana(int mana);

    int getAttack();

    void setAttack(int attack);

    int getDefense();

    void setDefense(int defense);

    int getHealth();

    void setHealth(int health);


}
