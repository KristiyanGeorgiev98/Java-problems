package Field;

import bg.sofia.uni.fmi.mjt.actors.Player;
import bg.sofia.uni.fmi.mjt.field.MapImpl;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MapImplTest {
    @Test
    public void testPutMinionOnMap() {
        MapImpl testMap = new MapImpl();
        testMap.putMinionOnMap(0);
        int rawPosition = testMap.getArrayOfMinions()[0].getRawPosition();
        int columnPosition = testMap.getArrayOfMinions()[0].getColumnPosition();
        char markMinion = testMap.getMapArray()[rawPosition][columnPosition].getSign();
        assertEquals(markMinion, 'M');

    }

    @Test
    public void testPutPlayerOnMap() {
        Player player = new Player();
        MapImpl testMap = new MapImpl();
        testMap.putPlayerOnMap(player);
        int rawPosition = testMap.getArrayOfPlayers()[0].getRawPosition();
        int columnPosition = testMap.getArrayOfPlayers()[0].getColumnPosition();
        char markPlayer = testMap.getMapArray()[rawPosition][columnPosition].getSign();
        assertEquals(markPlayer, '0');

    }

    @Test
    public void testPutTreasureOnMap() {
        MapImpl testMap = new MapImpl();
        testMap.putTreasureOnMap(0);
        int rawPosition = testMap.getArrayOfTreasures()[0].getRawPosition();
        int columnPosition = testMap.getArrayOfTreasures()[0].getColumnPosition();
        char markTreasure = testMap.getMapArray()[rawPosition][columnPosition].getSign();
        assertEquals(markTreasure, 'T');

    }

    @Test
    public void testMoveRight() {
        MapImpl map = new MapImpl();
        Player player = new Player();
        map.putPlayerOnMap(player);
        map.move(6, player);
        if (map.isPossibleMove(player)) {
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
            assertEquals(map.getMapArray()[player.getRawPosition() - 1][player.getColumnPosition()].getSign(), '.');
        } else {
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
        }
    }

    @Test
    public void testMoveLeft() {
        MapImpl map = new MapImpl();
        Player player = new Player();
        map.putPlayerOnMap(player);
        if (map.isPossibleMove(player)) {
            map.move(4, player);
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
            assertEquals(map.getMapArray()[player.getRawPosition() + 1][player.getColumnPosition()].getSign(), '.');
        } else {
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
        }
    }

    @Test
    public void testMoveUp() {
        MapImpl map = new MapImpl();
        Player player = new Player();
        map.putPlayerOnMap(player);
        if (map.isPossibleMove(player)) {
            map.move(2, player);
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition() - 1].getSign(), '.');
        } else {
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
        }
    }

    @Test
    public void testMoveDown() {
        MapImpl map = new MapImpl();
        Player player = new Player();
        map.putPlayerOnMap(player);
        if (map.isPossibleMove(player)) {
            map.move(8, player);
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition() + 1].getSign(), '.');
        } else {
            assertEquals(map.getMapArray()[player.getRawPosition()][player.getColumnPosition()].getSign(), '0');
        }
    }
}
