package bg.sofia.uni.fmi.mjt.field;

import bg.sofia.uni.fmi.mjt.actors.Minion;
import bg.sofia.uni.fmi.mjt.actors.Player;

import java.util.Random;

public class MapImpl implements Map {
    private final static int MAX_ROWS = 15;
    private final static int MIN_INDEX = 0;
    private final static int MAX_COLUMNS = 15;
    private final static int NUMBER_OF_OBSTACLES = 55;
    private final static int MAX_INDEX = 9;

    private Cell mapArray[][];
    private Player arrayOfPlayers[];
    private Minion arrayOfMinions[];
    private Treasure arrayOfTreasures[];

    public Minion[] getArrayOfMinions() {
        return arrayOfMinions;
    }

    public Player[] getArrayOfPlayers() {
        return arrayOfPlayers;
    }

    public Treasure[] getArrayOfTreasures() {
        return arrayOfTreasures;
    }

    public Minion getMinion(int rawPosition, int columnPosition) {
        for (Minion minion : arrayOfMinions) {
            if (minion == null) {
                continue;
            }
            if (minion.getRawPosition() == rawPosition
                    && minion.getColumnPosition() == columnPosition) {
                return minion;
            }
        }
        return null;
    }

    public Treasure getTreasure(int rawPosition, int columnPosition) {
        for (Treasure treasure : arrayOfTreasures) {
            if (treasure == null) {
                continue;
            }
            if (treasure.getRawPosition() == rawPosition
                    && treasure.getColumnPosition() == columnPosition) {
                return treasure;
            }
        }
        return null;
    }

    public Cell[][] getMapArray() {
        return mapArray;
    }

    public MapImpl() {
        mapArray = new Cell[MAX_ROWS][MAX_COLUMNS];
        initializeMap();
        initializeObstacles();
        arrayOfPlayers = new Player[MAX_INDEX];
        arrayOfMinions = new Minion[MAX_INDEX];
        arrayOfTreasures = new Treasure[MAX_INDEX];


    }

    // Creates a map with only free positions
    void initializeMap() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {

                mapArray[i][j] = new Cell();
                mapArray[i][j].setRawPosition(i);
                mapArray[i][j].setColumnPosition(j);

            }
        }
    }

    //Creates obstacles on the map
    private void initializeObstacles() {
        for (int i = 0; i < NUMBER_OF_OBSTACLES; i++) {
            int randomRaw = randomNumberGenerator();
            int randomColumn = randomNumberGenerator();
            mapArray[randomRaw][randomColumn].setSign('#');
            mapArray[randomRaw][randomColumn].setMinionInTheCell(false);
            mapArray[randomRaw][randomColumn].setTreasureInTheCell(false);
        }
    }

    public StringBuilder convertToStringBuilder() {
        StringBuilder outputString = new StringBuilder();
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {

                outputString.append(mapArray[i][j].getSign());
                outputString.append(" ");

            }
            outputString.append("\n");
        }
        return outputString;
    }

    public void printMap() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {

                System.out.print(mapArray[i][j].getSign());
                System.out.print(' ');

            }
            System.out.println();
        }
    }

    private int randomNumberGenerator() {
        Random random = new Random();
        int randomNumber = random.nextInt(15);
        return randomNumber;
    }

    private int randomNumberGeneratorForTreasures() {
        Random random = new Random();
        int randomNumber = random.nextInt(4);
        return randomNumber;
    }

    public Treasure setRandomTreasure() {
        int randomNumber = randomNumberGeneratorForTreasures();
        Treasure treasure = new Treasure();
        switch (randomNumber) {
            case 0:
                treasure = new Spell();
                break;
            case 1:
                treasure = new Weapon();
                break;
            case 2:
                treasure = new ManaPotion();
                break;
            case 3:
                treasure = new HealthPotion();
                break;
            default:
                treasure = new Treasure();
                break;
        }
        return treasure;
    }

    public void putMinionOnMap(int uniqueNumber) {
        Minion minion = new Minion();

        while (true) {
            int randomRaw = randomNumberGenerator();
            int randomColumn = randomNumberGenerator();
            if (mapArray[randomRaw][randomColumn].getSign() == '.') {
                mapArray[randomRaw][randomColumn].setMinionInTheCell(true);
                mapArray[randomRaw][randomColumn].setSign('M');
                minion.setRawPosition(randomRaw);
                minion.setColumnPosition(randomColumn);
                arrayOfMinions[uniqueNumber] = minion;
                break;
            }
        }
    }

    public void putPlayerOnMap(Player player) {
        arrayOfPlayers[player.getUniqueNumber()] = player;
        while (true) {
            int randomRaw = randomNumberGenerator();
            int randomColumn = randomNumberGenerator();
            if (mapArray[randomRaw][randomColumn].getSign() == '.') {
                mapArray[randomRaw][randomColumn].setSign((char) ('0' + player.getUniqueNumber()));
                player.setRawPosition(randomRaw);
                player.setColumnPosition(randomColumn);
                break;
            }
        }
    }

    public void putTreasureOnMap(int uniqueNumber) {
        while (true) {
            Treasure treasure = setRandomTreasure();
            arrayOfTreasures[uniqueNumber] = treasure;
            int randomRaw = randomNumberGenerator();
            int randomColumn = randomNumberGenerator();
            if (mapArray[randomRaw][randomColumn].getSign() == '.') {
                mapArray[randomRaw][randomColumn].setTreasureInTheCell(true);
                mapArray[randomRaw][randomColumn].setSign('T');
                treasure.setRawPosition(randomRaw);
                treasure.setColumnPosition(randomColumn);
                break;
            }
        }
    }

    public void update() {
        for (int i = 0; i < MAX_ROWS; ++i) {
            for (int j = 0; j < MAX_COLUMNS; ++j) {
                if (mapArray[i][j].isMinionInTheCell()) {
                    mapArray[i][j].setSign('M');
                }
                if (mapArray[i][j].isTreasureInTheCell()) {
                    mapArray[i][j].setSign('T');

                }
            }
        }
    }

    public boolean isPossibleMove(Player player) {
        int rawPosition = player.getRawPosition();
        int columnPosition = player.getColumnPosition();
        boolean isPossible = (rawPosition >= MIN_INDEX && rawPosition < MAX_ROWS
                && columnPosition >= MIN_INDEX && columnPosition < MAX_COLUMNS
                && mapArray[rawPosition][columnPosition].getSign() != '#');
        return isPossible;
    }

    public void move(int direction, Player player) {
        int rawPosition = player.getRawPosition();
        int columnPosition = player.getColumnPosition();
        int uniqueNumber = player.getUniqueNumber();
        switch (direction) {
            case 4:
                player.setColumnPosition(--columnPosition); //4 - left
                if (isPossibleMove(player) == false) {
                    player.setColumnPosition(++columnPosition);
                    break;
                }
                mapArray[rawPosition][columnPosition].setSign((char) ('0' + uniqueNumber));
                mapArray[rawPosition][++columnPosition].setSign('.');

                break;
            case 2:
                player.setRawPosition(++rawPosition); // 2 - down
                if (isPossibleMove(player) == false) {
                    player.setRawPosition(--rawPosition);
                    break;
                }
                mapArray[rawPosition][columnPosition].setSign((char) ('0' + uniqueNumber));
                mapArray[--rawPosition][columnPosition].setSign('.');
                break;
            case 6:
                player.setColumnPosition(++columnPosition); // 6 - right
                if (isPossibleMove(player) == false) {
                    player.setColumnPosition(--columnPosition);
                    break;
                }
                mapArray[rawPosition][columnPosition].setSign((char) ('0' + uniqueNumber));
                mapArray[rawPosition][--columnPosition].setSign('.');
                break;
            case 8:
                player.setRawPosition(--rawPosition); // 8 - up
                if (isPossibleMove(player) == false) {
                    player.setRawPosition(++rawPosition);
                    break;
                }
                mapArray[rawPosition][columnPosition].setSign((char) ('0' + uniqueNumber));
                mapArray[++rawPosition][columnPosition].setSign('.');
                break;
            default:
                break;
        }
    }
}
