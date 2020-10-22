package consolemaze.mazegenerator;

import consolemaze.util.PathWallSymbols;
import consolemaze.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Генерация лабиринта.
 */
public class MazeGeneratorFirstVersion implements MazeGenerator {

    private Cell[][] mazeField;
    //Лист посещённых ячеек для дальнейшего их переиспользования.
    private List<Cell> visited = new ArrayList<>();

    @Override
    public void setMazeField(Cell[][] mazeField) {
        this.mazeField = mazeField;
    }

    /**
     * Внешний метод для входа в рекурсию.
     *
     * @return
     */
    @Override
    public void walkOverMazeFiled() {
        Cell current = null;
        int startY = (int) (Math.random() * (mazeField.length - 1));
        int startX = (int) (Math.random() * (mazeField[0].length - 1));
        if (startY % 2 == 0 && startX != 0) {
            startY = 0;
        } else {
            startX = 0;
        }
        //Если вдруг оба значения будут нулевыми- то присваиваем startX единицу.
        if (startY == 0 && startX == 0) {
            startX = 1;
        }
        current = mazeField[startY][startX];
        this.step(current);
    }

    /**
     * Один шаг на следующую ячейку.
     *
     * @param current
     */
    private void step(Cell current) {
        current.setValue(PathWallSymbols.STRING_PATH.getValue());
        List<Cell> candidates = getCandidates(current);
        Cell next = null;
        int rndIndex;
        if (candidates.size() > 0) {
            //т.к. используется не set- то делаем проверку, что бы не плодить дубликаты.
            if (!this.visited.contains(current)) {
                this.visited.add(current);
            }
            rndIndex = (int) (Math.random() * candidates.size());
            next = candidates.get(rndIndex);
            this.step(next);
        } else {
            //Если вариантов для движения нет- удаляем текущую ячейку из листа посещённых,
            //что бы в дальнейшем эта ячейка не выпадала как вариант для старта новой рекурсии.
            this.visited.remove(current);
        }
        //Если пришли сюда- выбираем случайно следующую
        //ячейку для старта новой рекурсии и, соответственно, проклалдки пути.
        while (this.visited.size() > 0) {
            rndIndex = (int) (Math.random() * this.visited.size());
            this.step(this.visited.get(rndIndex));
        }
    }

    /**
     * Возвращает список доступных для следующего шага ячеек.
     * Ход возможен только по вертикали\горизонтали
     * и не по ячейкам с нулевыми координатами ( Cell[0][x] или Cell[y][0] )
     *
     * @param current
     * @return
     */
    private List<Cell> getCandidates(Cell current) {
        List<Cell> result = new ArrayList<>();
        Cell candidateForNext;
        //Нижнее направление.
        if (
                current.getY() + 1 < mazeField.length - 1
                        && current.getX() > 0
        ) {
            candidateForNext = mazeField[current.getY() + 1][current.getX()];
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        //Верхнее направление.
        if (
                current.getY() - 1 > 0
                        && current.getX() > 0
        ) {
            candidateForNext = mazeField[current.getY() - 1][current.getX()];
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }

        //Правое направление.
        if (
                current.getX() + 1 < mazeField[0].length - 1
                        && current.getY() > 0
        ) {
            candidateForNext = mazeField[current.getY()][current.getX() + 1];
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        //Левое направление.
        if (
                current.getX() - 1 > 0
                        && current.getY() > 0
        ) {
            candidateForNext = mazeField[current.getY()][current.getX() - 1];
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        return result;
    }

    /**
     * Проверка кандидата.
     * Если в смежных(соседних) ячейках(кроме той, из которой пришли) уже есть проход- вернёт false.
     * Значит, этот кандидат не подходит.
     *
     * @param candidate
     * @param from
     * @return
     */
    private boolean checkCandidateCell(Cell candidate, Cell from) {
        boolean result = true;
        Integer[] xLimits = getCuteLimits(candidate.getX(), from.getX(), mazeField[0].length);
        Integer[] yLimits = getCuteLimits(candidate.getY(), from.getY(), mazeField.length);

        for (int i = yLimits[0]; i < yLimits[1]; i++) {
            //Что бы не делать множественный return.
            if (!result) {
                break;
            }
            for (int j = xLimits[0]; j < xLimits[1]; j++) {
                Cell bufferCell = mazeField[i][j];
                if (bufferCell.getValue().equals(PathWallSymbols.STRING_PATH.getValue())) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Возвращает готовые для работы диапазоны по X и Y для проверки соседних ячеек
     * (обрезает диапазоны из функции getAbsoluteLimits для того, что бы не выпасть за пределы лабиринта.
     *
     * @param left
     * @param right
     * @param checkedLine - длина массива или подмассива.
     * @return
     */
    private Integer[] getCuteLimits(int left, int right, int checkedLine) {
        Integer[] result = getAbsoluteLimits.apply(left, right);
        //Что бы проход не зашёл за 0(нулевые) координаты.
        if (result[0] <= 0) {
            result[0] = 0;
        }
        //Что бы проход не шёл по граничным координатам.
        if (result[1] > checkedLine) {
            result[1] = checkedLine;
        }
        return result;
    }

    /**
     * Возвращает полный диапазон по X и Y для проверки соседних ячеек
     * с отсечением ячеек в той же линии, что и ячейка, из которой планируется шаг.
     * nextCoordinate - координата проверяемой ячейки.
     * currentCoordinate - координата ячейки из которой планируется шаг.
     */
    private BiFunction<Integer, Integer, Integer[]> getAbsoluteLimits = (nextCoordinate, currentCoordinate) -> {
        Integer[] result = new Integer[2];
        int check = Integer.compare(nextCoordinate, currentCoordinate);
        if (check < 0) {
            result[0] = nextCoordinate - 1;
            result[1] = currentCoordinate;
        } else if (check > 0) {
            result[0] = nextCoordinate;
            result[1] = nextCoordinate + 2;
        } else {
            result[0] = nextCoordinate - 1;
            result[1] = nextCoordinate + 2;
        }
        return result;
    };
}