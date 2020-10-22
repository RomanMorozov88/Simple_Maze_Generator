package consolemaze;

import consolemaze.mazegenerator.MazeGenerator;
import consolemaze.mazegenerator.MazeGeneratorFirstVersion;
import consolemaze.util.CheckInputData;
import consolemaze.util.LookAtMazeField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Запуск программы.
 */
public class MainClass {

    public static void main(String[] args) {

        Integer widthX = 30;
        Integer heightY = 20;
        Cell[][] mazeField = MazeFieldFactory.initMazeField(widthX, heightY);
        String resultFileName = String.format("\\maze_result%dx%d.txt", widthX, heightY);

        if (args.length == 2) {
            widthX = CheckInputData.getDigit(args[0]);
            heightY = CheckInputData.getDigit(args[1]);
            if (widthX != null && heightY != null) {
                if ((heightY >= 10 && heightY <= 50) && (widthX >= 10 && widthX <= 50)) {
                    mazeField = MazeFieldFactory.initMazeField(widthX, heightY);
                    resultFileName = String.format("\\maze_result%dx%d.txt", widthX, heightY);
                }
            }
        }
        MazeGenerator mazeGenerator = new MazeGeneratorFirstVersion();
        mazeGenerator.setMazeField(mazeField);
        mazeGenerator.walkOverMazeFiled();

        //Выводим всё это дело в консоль.
        LookAtMazeField.readMazeField(System.out::print, mazeField);

        //Создаём фаил в текущей директории и пишем в него.
        String filePath = Paths.get("").toAbsolutePath().toString() + resultFileName;
        try (FileWriter writer = new FileWriter(filePath, false)) {
            File file = new File(filePath);
            file.createNewFile();

            LookAtMazeField.readMazeField(
                    str -> {
                        try {
                            writer.append(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    },
                    mazeField
            );

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}