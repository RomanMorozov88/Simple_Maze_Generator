package mazegenerator;

/**
 * Само поле лабиринта.
 * Массив с ячейками.
 * Ничего интересного- создаётся массив и заполняется ячейками.
 */
public class MazeArray {

    private Cell[][] mazeField;
    private int size;

    public MazeArray(int y, int x) {
        this.mazeField = new Cell[y][x];
        this.size = y * x;
        this.initMazeField();
    }

    private void initMazeField() {
        for (int i = 0; i < mazeField.length; i++) {
            for (int j = 0; j < mazeField[0].length; j++) {
                mazeField[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getMazeField() {
        return mazeField;
    }

    public Cell getCell(int y, int x) {
        return this.mazeField[y][x];
    }

    public int getSize() {
        return size;
    }
}