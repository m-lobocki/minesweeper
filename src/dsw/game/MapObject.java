package dsw.game;

public abstract class MapObject {
    private boolean visible = false;

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return this.visible;
    }
}
