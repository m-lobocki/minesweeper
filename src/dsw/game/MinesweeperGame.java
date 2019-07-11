package dsw.game;


public class MinesweeperGame implements Minesweeper {
    private Table table;
    private boolean isLost;

    public MapObject[][] start(Difficulty difficulty){
        switch(difficulty){
            case Easy: {
                table = new Table(8,8,15);
                break;
            }
            case Medium: {
                table = new Table(15,15,40);
                break;
            }
            case Hard: {
                table = new Table(30,20,150);
                break;
            }
        }
        return table.getMap();
    }

    public void dig(int x, int y){
        if(this.table.getMap()[x][y] instanceof Bomb) {
            this.gameOver(x,y);
        }
        else {
            this.table.getMap()[x][y].setVisible(true);
            Field field = (Field)this.table.getMap()[x][y];
            if(field.getSurroundingBombs() == 0){
                this.table.revealEmptyFields(x,y);
            }
        }
    }

    private void gameOver(int x, int y){
        isLost = true;
        Bomb bomb = (Bomb)this.table.getMap()[x][y];
        bomb.setBombExploded(true);
        for(int i = 0; i < this.table.getWidth(); i++){
            for(int j = 0; j<this.table.getHeight(); j++) {
                this.table.getMap()[i][j].setVisible(true);
            }
        }
    }

    public boolean checkWin(){
        int tableSize = this.table.getHeight() * this.table.getWidth();
        int visibleFieldsAmount = 0;
        for(int i = 0; i < this.table.getWidth(); i++){
            for(int j = 0; j < this.table.getHeight(); j++) {
                if(this.table.getMap()[i][j].getVisible()) visibleFieldsAmount++;
            }
        }
        if(visibleFieldsAmount == (tableSize - this.table.getBombsAmount())) return true;
        return false;
    }

    public boolean checkLose() {
        return isLost;
    }
}
