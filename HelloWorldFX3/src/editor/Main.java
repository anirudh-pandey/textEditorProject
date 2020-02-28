package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menus.FileMenu;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        FileMenu fileMenu = new FileMenu();
        Controller controller = loader.getController();

        primaryStage.setTitle(fileMenu.getFileName() + " - World's Greatest IDE");
        primaryStage.setScene(new Scene(root, 500, 375));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
