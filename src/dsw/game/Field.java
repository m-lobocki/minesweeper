package dsw.game;

public class Field extends MapObject {
    private int surroundingBombs;

    public int getSurroundingBombs() {
        return surroundingBombs;
    }

    public void setSurroundingBombs(int surroundingBombs) {
        this.surroundingBombs = surroundingBombs;
    }
}
