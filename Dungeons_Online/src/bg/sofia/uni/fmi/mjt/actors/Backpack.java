package bg.sofia.uni.fmi.mjt.actors;

import bg.sofia.uni.fmi.mjt.field.Treasure;

public class Backpack {
    private final static int MAX_CAPACITY = 9;
    private Treasure backpack[];
    private int capacity;

    Backpack() {
        capacity = 0;
        backpack = new Treasure[MAX_CAPACITY];
    }

    public Treasure[] getBackpack() {
        return backpack;
    }

    public void setBackpack(Treasure[] backpack) {
        this.backpack = backpack;
    }

    public void setTreasureInBackpack(Treasure treasure) {
        if (capacity < MAX_CAPACITY) {
            backpack[capacity] = treasure;
            ++capacity;
        }
    }

    public boolean isEmpty() {
        return capacity == 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
