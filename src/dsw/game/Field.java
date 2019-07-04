package dsw.game;

public class Field implements MapObject {
    private int surroundingBombs;

    public Field(int surroundingBombs) {
        this.surroundingBombs = surroundingBombs;
    }

    public int getSurroundingBombs() {
        return surroundingBombs;
    }

    public void setSurroundingBombs(int surroundingBombs) {
        this.surroundingBombs = surroundingBombs;
    }
}
