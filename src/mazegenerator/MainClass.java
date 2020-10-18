package mazegenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

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

        MazeArray mazeArray = new MazeArray(20, 20);

        if (args.length == 2) {
            Integer x = getDigit(args[0]);
            Integer y = getDigit(args[1]);
            if (x != null && y != null) {
                if ((y >= 10 && y <= 50) && (x >= 10 && x <= 50)) {
                    mazeArray = new MazeArray(y, x);
                }
            }
        }

        MazeGenerator mazeGenerator = new MazeGenerator(mazeArray);
        mazeGenerator.walkFromRandomCell();

        //Выводим всё это дело в консоль.
        for (int i = -1; i <= mazeArray.getMazeField().length; i++) {
            System.out.println();
            for (int j = -1; j <= mazeArray.getMazeField()[0].length; j++) {
                if (i < 0 || i >= mazeArray.getMazeField().length) {
                    System.out.print(PathWallSymbols.STRING_HORIZON_BORDER.getValue());
                } else if (j < 0 || j >= mazeArray.getMazeField()[0].length) {
                    System.out.print(PathWallSymbols.STRING_VERTICAL_BORDER.getValue());
                } else {
                    System.out.print(mazeArray.getCell(i, j).getValue());
                }
            }
        }
        System.out.println();

        //Создаём фаил в текущей директории.
        String filePath = Paths.get("").toAbsolutePath().toString() + "\\maze_result.txt";
        try (FileWriter writer = new FileWriter(filePath, false)) {
            File file = new File(filePath);
            file.createNewFile();
            for (int i = -1; i <= mazeArray.getMazeField().length; i++) {
                writer.append('\n');
                for (int j = -1; j <= mazeArray.getMazeField()[0].length; j++) {
                    if (i < 0 || i >= mazeArray.getMazeField().length) {
                        writer.append(PathWallSymbols.STRING_HORIZON_BORDER.getValue());
                    } else if (j < 0 || j >= mazeArray.getMazeField()[0].length) {
                        writer.append(PathWallSymbols.STRING_VERTICAL_BORDER.getValue());
                    } else {
                        writer.append(mazeArray.getCell(i, j).getValue());
                    }
                }
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}