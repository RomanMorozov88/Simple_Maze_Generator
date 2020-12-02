package basic.util;

import basic.model.Cell;

import java.util.function.Consumer;

/**
 * Содержит метод для чтения массива-лабиринта.
 */
public class LookAtMazeField {

    /**
     *
     * @param consumer - каким образом будет читать\записывать
     * @param mazeField - что будет читать\записывать
     */
    public static void readMazeField(Consumer<String> consumer, Cell[][] mazeField) {
        for (int i = -1; i <= mazeField.length; i++) {
            consumer.accept("\n");
            for (int j = -1; j <= mazeField[0].length; j++) {
                if (i < 0 || i >= mazeField.length) {
                    consumer.accept(PathWallSymbols.STRING_HORIZON_BORDER.getValue());
                } else if (j < 0 || j >= mazeField[0].length) {
                    consumer.accept(PathWallSymbols.STRING_VERTICAL_BORDER.getValue());
                } else {
                    consumer.accept(mazeField[i][j].getValue());
                }
            }
        }
        consumer.accept("\n");
    }

}