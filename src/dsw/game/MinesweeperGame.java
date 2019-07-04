package dsw.game;

public interface MinesweeperGame {
    MapObject[][] start(Difficulty difficulty);
    boolean dig(int x, int y);
}
