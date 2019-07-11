package dsw.game;

public class Bomb extends MapObject {
    private boolean bombExploded = false;

    public void setBombExploded(boolean exploded) {
        this.bombExploded = exploded;
    }

    public boolean getBombExploded() {
        return this.bombExploded;
    }
}
