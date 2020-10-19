package mazegenerator;

import mazegenerator.mazegenerator.MazeGenerator;
import mazegenerator.mazegenerator.MazeGeneratorFirstVersion;
import mazegenerator.util.LookAtMazeField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Запуск программы.
 */
public class MainClass {

    /**
     * Проверка, что входящую строку можно преобразовать в Integer.
     *
     * @param s
     * @return
     * @throws NumberFormatException
     */
    private static Integer getDigit(String s) throws NumberFormatException {
        Integer result = null;
        try {
            result = Integer.parseInt(s);
            return result;
        } catch (NumberFormatException e) {
            return result;
        }
    }

    public static void main(String[] args) {

        Integer widthX = 30;
        Integer heightY = 20;
        Cell[][] mazeField = MazeFieldFactory.initMazeField(widthX, heightY);

        if (args.length == 2) {
            widthX = getDigit(args[0]);
            heightY = getDigit(args[1]);
            if (widthX != null && heightY != null) {
                if ((heightY >= 10 && heightY <= 50) && (widthX >= 10 && widthX <= 50)) {
                    mazeField = MazeFieldFactory.initMazeField(widthX, heightY);
                }
            }
        }
        MazeGenerator mazeGenerator = new MazeGeneratorFirstVersion();
        mazeGenerator.setMazeField(mazeField);
        mazeGenerator.walkOverMazeFiled();

        //Выводим всё это дело в консоль.
        LookAtMazeField.readMazeField(System.out::print, mazeField);

        //Создаём фаил в текущей директории и пишем в него.
        String resultFileName = String.format("\\maze_result%dx%d.txt", widthX, heightY);
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