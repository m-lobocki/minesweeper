package dsw.game;

public interface Minesweeper {
    void start(Difficulty difficulty);
    void dig(int x, int y);
    int getBombsAmount();
}
