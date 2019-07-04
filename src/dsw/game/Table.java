package dsw.game;
import java.lang.Math;
public class Table implements MapObject {
    private int width, height, bombsAmount;
/////////////////////////WROK IN PROGRESS/////////////////////////
    public Table(int width, int height, int bombsAmount){
        this.height = height;
        this.width = width;
        this.bombsAmount = bombsAmount;

        public MapObject[][] fields[width][height] = null;
        this.draftTable();
    }

    void draftTable(){
        int draftTab[][] = this.getRandomTable();

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++) {
                this.fields[i][j] = draftTab[i+1][j+1] === 1 ? new Bomb() : new Field();
            }
        }

        this.setFieldsValues()
    }

    int[][] getRandomTable(){
        int table[this.width + 2][this.height + 2] = 0;
        int fieldsAmount = this.height * this.width;
        for (int i = 0; i < this.bombsAmount; i++) {
            boolean unique = false;
            while (!unique) {
                int randX = (int) (Math.random() * this.width + 1);
                int randY = (int) (Math.random() * this.height + 1);
                if (table[randX][randY] === 0) {
                    table[randX][randY] = 1;
                    unique = true;
                }

            }
        }
        return table;
    }

    void setFieldsValues() {
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j<this.height; j++) {
                if(this.table.fields[i][j] instanceof Field)
                if(i === 0){
                    if(j === 0){
                        int count = 0;
                        if(this.table.fields[i+1][j] instanceof Bomb) count++;
                        if(this.table.fields[i+1][j+1] instanceof Bomb) count++;
                        if(this.table.fields[i][j+1] instanceof Bomb) count++;
                    }else if(j === this.width - 1)
                }
            }
        }
    }
}