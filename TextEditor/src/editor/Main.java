package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import menus.FileMenu;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        final String appName = "Notebook1";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../editor/sample.fxml"));
        Parent root = loader.load();
        FileMenu fileMenu = new FileMenu();
        loader.getController();

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../notebookIcon.png")));
        primaryStage.setTitle(fileMenu.getFileName() + " - " + appName);
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
