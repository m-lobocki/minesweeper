package dsw.game;

public interface Minesweeper {
    MapObject[][] start(Difficulty difficulty);
    void dig(int x, int y);
    boolean checkWin();
    boolean checkLose();
}
