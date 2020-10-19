package mazegenerator;

import mazegenerator.util.PathWallSymbols;

import java.util.Objects;

/**
 * Класс, описывающий ячейку лабиринта.
 * x, y - координаты в массиве.
 * value - String описание- стена или проход (см. enum PathWallSymbols)
 */
public class Cell {

    private int y;
    private int x;
    private String value = PathWallSymbols.STRING_WALL.getValue();

    public Cell(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return y == cell.y &&
                x == cell.x &&
                Objects.equals(value, cell.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x, value);
    }
}