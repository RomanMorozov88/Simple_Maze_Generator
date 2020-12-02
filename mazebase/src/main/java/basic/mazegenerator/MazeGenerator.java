package basic.mazegenerator;

import basic.model.Cell;

/**
 * Базовый интерфейс- на случай, если надо будет добавить
 * новый генератор с использованием другого алгоритма.
 */
public interface MazeGenerator {

    void setMazeField(Cell[][] mazeField);

    void walkOverMazeFiled();

}