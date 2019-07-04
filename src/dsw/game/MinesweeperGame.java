package dsw.game;

public interface MinesweeperGame {
    MapObject[][] start(Difficulty difficulty);
    int getBombsAmount();
}
