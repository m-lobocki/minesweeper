package dsw.game;

import Minesweeper.java;

public class MinesweeperGame implements Minesweeper {

    private Table table;

    void start(Difficulty difficulty){
        switch(difficulty){
            case Easy: {
                table = new Table(8,8,15);
            }
            case Medium: {
                table = new Table(15,15,40);
            }
            case Hard: {
                table = new Table(30,20,150);
            }
        }
    }

    void dig(int x, int y){
        if(this.table.fields[x][y] instanceof Bomb) this.gameOver(x,y);
        else {
            this.table.fields[x][y].visible = true;
            if(this.table.fields[x][y].getSurroundingBombs() === 0){
                this.setNeighborsVisible(x,y)
            }
            this.checkForEmptyFields(x,y);
        }
    }

    void checkForEmptyFields(int x, int y){

    }
    // TODO
    void setNeighborsVisible(int x, int y){
        if(this.table.draftedTable[x][y]  && this.table.draftedTable[x][y])
    }

    void gameOver(int x, int y){
        this.table.fields[x][y].bombExploded = true;
        for(int i = 0; i < this.table.width; i++){
            for(int j = 0; j<this.table.height; j++) {
                this.table.fields[i][j].visible = true;
            }
        }
    }
}

