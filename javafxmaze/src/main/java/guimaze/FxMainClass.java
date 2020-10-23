package guimaze;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;

/**
 * Запуск оконной версии.
 */
public class FxMainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        InputStream iconStream = getClass().getResourceAsStream("/maze_icon.png");
        Image icon = new Image(iconStream);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Maze GUI");

        Scene primaryScene = new Scene(root);
        primaryStage.setScene(primaryScene);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
