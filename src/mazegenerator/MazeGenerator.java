package mazegenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Генерация лабиринта.
 */
public class MazeGenerator {

    private MazeArray mazeArray;
    //Лист посещённых ячеек для дальнейшего их переиспользования.
    private List<Cell> visited = new ArrayList<>();

    public MazeGenerator(MazeArray mazeArray) {
        this.mazeArray = mazeArray;
    }

    /**
     * Внешний метод для входа в рекурсию.
     *
     * @return
     */
    public void walkFromRandomCell() {
        Cell current = null;
        int startY = (int) (Math.random() * mazeArray.getMazeField().length);
        int startX = 0;
        if (startY == 0) {
            startX = (int) (Math.random() * mazeArray.getMazeField()[0].length);
        }
        current = mazeArray.getCell(startY, startX);
        current.setValue(PathWallSymbols.STRING_PATH.getValue());
        this.step(current);
    }

    /**
     * Один шаг на следующую ячейку.
     *
     * @param current
     */
    public void step(Cell current) {
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
        if (current.getY() + 1 < mazeArray.getMazeField().length - 1) {
            candidateForNext = mazeArray.getCell(current.getY() + 1, current.getX());
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        //Верхнее направление.
        if (current.getY() - 1 > 0) {
            candidateForNext = mazeArray.getCell(current.getY() - 1, current.getX());
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        //Правое направление.
        if (current.getX() + 1 < mazeArray.getMazeField()[0].length - 1) {
            candidateForNext = mazeArray.getCell(current.getY(), current.getX() + 1);
            if (checkCandidateCell(candidateForNext, current)) {
                result.add(candidateForNext);
            }
        }
        //Левое направление.
        if (current.getX() - 1 > 0) {
            candidateForNext = mazeArray.getCell(current.getY(), current.getX() - 1);
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
        Integer[] xLimits = getCuteLimits(candidate.getX(), from.getX(), mazeArray.getMazeField()[0].length);
        Integer[] yLimits = getCuteLimits(candidate.getY(), from.getY(), mazeArray.getMazeField().length);

        for (int i = yLimits[0]; i < yLimits[1]; i++) {
            if (!result) {
                break;
            }
            for (int j = xLimits[0]; j < xLimits[1]; j++) {
                Cell bufferCell = mazeArray.getCell(i, j);
                if (bufferCell.getValue().equals(PathWallSymbols.STRING_PATH.getValue())
                        && !mazeArray.getCell(i, j).equals(from)) {
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