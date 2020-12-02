package guimaze;


import basic.mazegenerator.MazeGenerator;
import basic.mazegenerator.MazeGeneratorFirstVersion;
import basic.model.Cell;
import basic.util.CheckInputData;
import basic.util.MazeFieldFactory;
import basic.util.PathWallSymbols;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Читает\получает данные из mainScene.fxml
 */
public class FXMLDocumentController {

    @FXML
    private TextField x_limit;
    @FXML
    private TextField y_limit;
    @FXML
    private GridPane grid;

    /**
     * Генерирует лабиринт и отправляет для отрисовки в GridPane
     */
    @FXML
    private void getMaze() {

        //очищает всё, что уже есть в GridPane
        grid.getChildren().removeAll(grid.getChildren());

        Integer widthX = 40;
        Integer heightY = 25;
        Cell[][] mazeField = MazeFieldFactory.initMazeField(heightY, widthX);

        widthX = CheckInputData.getDigit(x_limit.getCharacters().toString());
        heightY = CheckInputData.getDigit(y_limit.getCharacters().toString());
        if (widthX != null && heightY != null) {
            if ((heightY >= 10 && heightY <= 40) && (widthX >= 10 && widthX <= 100)) {
                mazeField = MazeFieldFactory.initMazeField(widthX, heightY);
            }
        }

        MazeGenerator mazeGenerator = new MazeGeneratorFirstVersion();
        mazeGenerator.setMazeField(mazeField);
        mazeGenerator.walkOverMazeFiled();

        for (int i = 0; i < mazeField.length; i++) {
            for (int j = 0; j < mazeField[0].length; j++) {
                if (PathWallSymbols.STRING_WALL.getValue().equals(mazeField[i][j].getValue())) {
                    Rectangle rectangle = new Rectangle(20, 20);
                    rectangle.setFill(Color.BLACK);
                    grid.add(rectangle, i, j);
                }
            }
        }
    }

}