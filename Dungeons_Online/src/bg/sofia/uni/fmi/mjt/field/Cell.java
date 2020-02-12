package bg.sofia.uni.fmi.mjt.field;

public class Cell {
    private char sign;
    private int rawPosition;
    private int columnPosition;
    private boolean isMinionInTheCell;
    private boolean isTreasureInTheCell;

    public int getRawPosition() {
        return rawPosition;
    }

    public void setRawPosition(int rawPosition) {
        this.rawPosition = rawPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }


    public boolean isMinionInTheCell() {
        return isMinionInTheCell;
    }

    public void setMinionInTheCell(boolean minionInTheCell) {
        isMinionInTheCell = minionInTheCell;
    }

    public boolean isTreasureInTheCell() {
        return isTreasureInTheCell;
    }

    public void setTreasureInTheCell(boolean treasureInTheCell) {
        isTreasureInTheCell = treasureInTheCell;
    }

    Cell() {
        sign = '.';
        rawPosition = 0;
        columnPosition = 0;
        isMinionInTheCell = false;
        isTreasureInTheCell = false;
    }

    Cell(int rawPosition, int columnPosition) {
        sign = '.';
        this.rawPosition = rawPosition;
        this.columnPosition = columnPosition;
        isMinionInTheCell = false;
        isTreasureInTheCell = false;
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

}
