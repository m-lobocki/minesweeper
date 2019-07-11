package dsw.gui;

public class MapObjectData {
    private int column;
    private int row;

    public MapObjectData(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
