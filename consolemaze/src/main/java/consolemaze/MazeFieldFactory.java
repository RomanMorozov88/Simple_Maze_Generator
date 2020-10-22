package consolemaze;

/**
 * Создание массива-поля лабиринта.
 * Ничего интересного- создаётся массив и заполняется ячейками.
 */
public class MazeFieldFactory {

    public static Cell[][] initMazeField(int x, int y) {
        Cell[][] mazeField = new Cell[y][x];
        for (int i = 0; i < mazeField.length; i++) {
            for (int j = 0; j < mazeField[0].length; j++) {
                mazeField[i][j] = new Cell(i, j);
            }
        }
        return mazeField;
    }
}