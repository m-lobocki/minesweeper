package dsw.game;

import Minesweeper.java;

public class MinesweeperGame implements Minesweeper {

    Table table;

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
        else this.table.fields[x][y].visible = true;
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

