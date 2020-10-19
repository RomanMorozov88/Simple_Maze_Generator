package mazegenerator.mazegenerator;

import mazegenerator.Cell;
import mazegenerator.util.PathWallSymbols;

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
        int startY = (int) (Math.random() * mazeField.length);
        int startX = 0;
        if (startY == 0) {
            startX = (int) (Math.random() * mazeField[0].length);
        }
        current = mazeField[startY][startX];
        current.setValue(PathWallSymbols.STRING_PATH.getValue());
        this.step(current);
    }

    /**
     * Один шаг на следующую ячейку.
     *
     * @param current
     */
    private void step(Cell current) {
        this.visited.add(current);
        List<Cell> candidates = getCandidates(current);
        Cell next = null;
        if (candidates.size() > 0) {
            int rndIndex = (int) (Math.random() * candidates.size());
            next = candidates.get(rndIndex);
            next.setValue(PathWallSymbols.STRING_PATH.getValue());
            this.step(next);
            //Если вернулись сюда после step- выбираем случайно следующую
            //ячейку для старта новой рекурсии и, соответственно, проклалдки пути.
            if (this.visited.size() > 0) {
                rndIndex = (int) (Math.random() * this.visited.size());
                this.step(this.visited.get(rndIndex));
            }
        } else {
            //Если вариантов для движения нет- удаляем текущую ячейку из листа посещённых,
            //что бы в дальнейшем эта ячейка не выпадала как вариант для старта новой рекурсии.
            this.visited.remove(current);
        }
    }

    /**
     * Возвращает список доступных для следующего шага ячеек.
     * Ход возможен только по вертикали\горизонтали.
     *
     * @param current
     * @return
     */
    private List<Cell> getCandidates(Cell current) {
        List<Cell> result = new ArrayList<>();
        Cell candidateForNext;
        //Нижнее направление.
        if (current.getY() + 1 < mazeField.length - 1) {
            //candidateForNext = mazeArrayInitializer.getCell(current.getY() + 1, current.getX());
            candidateForNext = mazeField[current.getY() + 1][current.getX()];
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        //Верхнее направление.
        if (current.getY() - 1 > 0) {
            //candidateForNext = mazeArrayInitializer.getCell(current.getY() - 1, current.getX());
            candidateForNext = mazeField[current.getY() - 1][current.getX()];
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }

        //Правое направление.
        if (current.getX() + 1 < mazeField[0].length - 1) {
            //candidateForNext = mazeArrayInitializer.getCell(current.getY(), current.getX() + 1);
            candidateForNext = mazeField[current.getY()][current.getX() + 1];
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        //Левое направление.
        if (current.getX() - 1 > 0) {
            //candidateForNext = mazeArrayInitializer.getCell(current.getY(), current.getX() - 1);
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
            if (!result) {
                break;
            }
            for (int j = xLimits[0]; j < xLimits[1]; j++) {
                Cell bufferCell = mazeField[i][j];
                if (bufferCell.getValue().equals(PathWallSymbols.STRING_PATH.getValue())
                        && !bufferCell.equals(from)) {
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
     * @param x
     * @param y
     * @param checkedLine
     * @return
     */
    private Integer[] getCuteLimits(int x, int y, int checkedLine) {
        Integer[] result = getAbsoluteLimits.apply(x, y);
        if (result[0] < 0) {
            result[0] = 0;
        }
        if (result[1] > checkedLine) {
            result[1] = checkedLine;
        }
        return result;
    }

    /**
     * Возвращает полный диапазон по X и Y для проверки соседних ячеек.
     * xC - координата проверяемой ячейки.
     * xF - координата ячейки из которой планируется шаг.
     */
    private BiFunction<Integer, Integer, Integer[]> getAbsoluteLimits = (xC, xF) -> {
        Integer[] result = new Integer[2];
        int check = Integer.compare(xC, xF);
        if (check < 0) {
            result[0] = xC - 1;
            result[1] = xF;
        } else if (check > 0) {
            result[0] = xC;
            result[1] = xC + 2;
        } else {
            result[0] = xC - 1;
            result[1] = xC + 2;
        }
        return result;
    };
}