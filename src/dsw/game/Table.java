package dsw.game;
import java.lang.Math;
public class Table {
    private int width, height, bombsAmount;
    /////////////////////////WROK IN PROGRESS/////////////////////////
    public int[][] draftedTable;
    public MapObject[][] fields = null;

    public Table(int width, int height, int bombsAmount){
        this.height = height;
        this.width = width;
        this.bombsAmount = bombsAmount;

        draftedTable = this.getRandomTable();
        this.draftTable();
    }

    void draftTable(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++) {
                this.fields[i][j] = draftedTable[i+1][j+1] == 1 ? new Bomb() : new Field();
            }
        }

        this.setFieldsValues();
    }

    int[][] getRandomTable(){
        int[][] table = new int[this.width + 2][this.height + 2];
        int fieldsAmount = this.height * this.width;
        for (int i = 0; i < this.bombsAmount; i++) {
            boolean unique = false;
            while (!unique) {
                int randX = (int) (Math.random() * this.width + 1);
                int randY = (int) (Math.random() * this.height + 1);
                if (table[randX][randY] == 0) {
                    table[randX][randY] = 1;
                    unique = true;
                }
            }
        }
        return table;
    }

    void setFieldsValues() {
        for(int i = 1; i <= this.width; i++){
            for(int j = 1; j<=this.height; j++) {
                if (this.fields[i - 1][j - 1] instanceof Field) {
                    int count = 0;
                    if (this.draftedTable[i + 1][j] == 1) count++;
                    if (this.draftedTable[i + 1][j + 1] == 1) count++;
                    if (this.draftedTable[i + 1][j - 1] == 1) count++;
                    if (this.draftedTable[i - 1][j]  == 1) count++;
                    if (this.draftedTable[i - 1][j + 1]  == 1) count++;
                    if (this.draftedTable[i - 1][j - 1]  == 1) count++;
                    if (this.draftedTable[i][j + 1]  == 1) count++;
                    if (this.draftedTable[i][j - 1]  == 1) count++;
                    Field field = (Field)this.fields[i-1][j-1];
                    field.setSurroundingBombs(count);
                }
            }
        }
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
}