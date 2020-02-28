package editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import menus.FileMenu;

import java.io.IOException;

public class Controller {
    @FXML private FileMenu fileMenu = new FileMenu();
    @FXML private TextArea textArea;
    private Stage primaryStage;

    public TextArea getTextArea() {
        return textArea;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    //Events related to FileMenu.
    @FXML
    protected void handleNewBtnAction(ActionEvent actionEvent) throws IOException {
        fileMenu.handleNewBtnAction(textArea);
    }

    @FXML
    protected void handleNewWindowBtnAction(ActionEvent actionEvent) throws IOException {
        fileMenu.handleNewWindowBtnAction();
    }

    @FXML
    protected void handleOpenBtnAction(ActionEvent actionEvent) {
        fileMenu.handleOpenBtnAction(textArea);
    }

    @FXML
    protected void handleSaveBtnAction(ActionEvent actionEvent) {
        fileMenu.handleSaveBtnAction(textArea);
    }

    @FXML
    protected void handleSaveAsBtnAction(ActionEvent actionEvent) {
        fileMenu.handleSaveAsBtnAction(textArea);
    }

    @FXML
    protected void handleExitBtnAction(ActionEvent actionEvent) {
        fileMenu.handleExitBtnAction(textArea);
    }

//    protected void handleCloseBtnAction() {
//        Stage currentStage = (Stage)textArea.getScene().getWindow();
//        currentStage.setOnCloseRequest(e -> fileMenu.handleExitBtnAction(textArea));
//    }




}
