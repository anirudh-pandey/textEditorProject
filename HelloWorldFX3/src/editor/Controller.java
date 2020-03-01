package editor;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import menus.EditMenu;
import menus.FileMenu;

import java.io.IOException;

public class Controller {
    private FileMenu fileMenu = new FileMenu();
    private EditMenu editMenu = new EditMenu();
    @FXML private TextArea textArea;
    @FXML private Menu editMenuElem;
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


    // ----------Events related to FileMenu.
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

    // ----------Events related to EditMenu.
    @FXML
    public void handleEditTabAction(Event event) {
//        event.getSource().getClass().getFields().
        System.out.println("________");
        editMenu.handleEditTabAction(editMenuElem, textArea);
    }

    @FXML
    public void handleSelectAllBtnAction(ActionEvent actionEvent) {
        editMenu.handleSelectAllBtnAction(textArea);
    }

    @FXML
    public void handleCutBtnAction(ActionEvent actionEvent) {
        editMenu.handleCutBtnAction(textArea);
    }

    @FXML
    public void handleCopyBtnAction(ActionEvent actionEvent) {
        editMenu.handleCopyBtnAction(textArea);
    }

    @FXML
    public void handlePasteBtnAction(ActionEvent actionEvent) {
        editMenu.handlePasteBtnAction(textArea);
    }

    @FXML
    public void handleDelBtnAction(ActionEvent actionEvent) {
        editMenu.handleDelBtnAction(textArea);
    }

//    public void handleEditTabAction() {
//    }


//    protected void handleCloseBtnAction() {
//        Stage currentStage = (Stage)textArea.getScene().getWindow();
//        currentStage.setOnCloseRequest(e -> fileMenu.handleExitBtnAction(textArea));
//    }




}
