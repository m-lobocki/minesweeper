package dsw.game;
import java.lang.Math;
public class Table {
    private int width, height, bombsAmount;
    private int[][] draftedTable;
    private MapObject[][] map;
    private int[][] vectors = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public Table(int width, int height, int bombsAmount){
        this.height = height;
        this.width = width;
        this.bombsAmount = bombsAmount;
        this.map = new MapObject[width][height];

        draftedTable = this.getRandomTable();
        this.draftTable();
    }

    void draftTable(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++) {
                this.map[i][j] = draftedTable[i+1][j+1] == 1 ? new Bomb() : new Field();
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
                if (this.map[i - 1][j - 1] instanceof Field) {
                    int count = 0;
                    for (int[] vector : vectors) {
                        if (this.draftedTable[i + vector[0]][j + vector[1]] == 1) {
                            count++;
                        }
                    }
                    Field field = (Field)this.map[i-1][j-1];
                    field.setSurroundingBombs(count);
                }
            }
        }
    }

    public void revealEmptyFields(int x, int y){
        for (int[] vector : vectors) {
            if (x + vector[0] >= 0 && x + vector[0] < this.width && y + vector[1] >= 0 && y + vector[1] < this.height) {
                Field field = (Field) this.map[x + vector[0]][y + vector[1]];
                if (field.getSurroundingBombs() == 0 && !field.getVisible()) {

                    this.map[x + vector[0]][y + vector[1]].setVisible(true);
                    this.revealEmptyFields(x + vector[0], y + vector[1]);

                } else {
                    this.map[x + vector[0]][y + vector[1]].setVisible(true);
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

    public MapObject[][] getMap() {
        return this.map;
    }

    public int getBombsAmount(){
        return this.bombsAmount;
    }
}