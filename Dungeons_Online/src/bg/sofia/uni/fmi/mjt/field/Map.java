package bg.sofia.uni.fmi.mjt.field;

import bg.sofia.uni.fmi.mjt.actors.Minion;
import bg.sofia.uni.fmi.mjt.actors.Player;

public interface Map {

    //get minion by his coordinates on the map
    Minion getMinion(int rawPosition, int columnPosition);

    //get treasure by his coordinates on the map
    Treasure getTreasure(int rawPosition, int columnPosition);

    //get mapArray
    Cell[][] getMapArray();

    //get array of minions
    Minion[] getArrayOfMinions();

    //get array of players
    Player[] getArrayOfPlayers();

    //get array of treasures
    Treasure[] getArrayOfTreasures();

    //convert Map to StringBuilder
    StringBuilder convertToStringBuilder();

    //put minion on the map
    void putMinionOnMap(int uniqueNumber);

    //put player on the map
    void putPlayerOnMap(Player player);

    ////pick random type of treasure(weapon,spell,potion)
    Treasure setRandomTreasure();

    ////put treasure on the map
    void putTreasureOnMap(int uniqueNumber);

    //a player moves on the map
    void move(int direction, Player player);


}
