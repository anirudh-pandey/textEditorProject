package editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import menus.EditMenu;
import menus.FileMenu;
import menus.FormatMenu;
import menus.HelpMenu;

import java.io.IOException;

public class Controller {
    private FileMenu fileMenu = new FileMenu();
    private EditMenu editMenu = new EditMenu();
    private FormatMenu formatMenu = new FormatMenu();
    private HelpMenu helpMenu = new HelpMenu();
    @FXML private TextArea textArea;
    @FXML private Menu editMenuElem;
    @FXML private CheckBox wrapTextBox;

    // ----------Events related to FileMenu.
    @FXML
    protected void handleNewAction() {
        fileMenu.handleNewBtnAction(textArea);
    }

    @FXML
    protected void handleNewWindowAction() throws IOException {
        fileMenu.handleNewWindowBtnAction();
    }

    @FXML
    protected void handleOpenAction() {
        fileMenu.handleOpenBtnAction(textArea);
    }

    @FXML
    protected void handleSaveAction() {
        fileMenu.handleSaveBtnAction(textArea);
    }

    @FXML
    protected void handleSaveAsAction() {
        fileMenu.handleSaveAsBtnAction(textArea);
    }

    @FXML
    protected void handleExitAction() {
        fileMenu.handleExitBtnAction(textArea);
    }

    // ----------Events related to EditMenu.
    @FXML
    public void handleEditTabAction() {
        editMenu.handleEditTabAction(editMenuElem, textArea);
    }

    @FXML
    public void handleUndoAction() {
        editMenu.handleUndoBtnAction(textArea);
    }

    @FXML
    public void handleCutAction() {
        editMenu.handleCutBtnAction(textArea);
    }

    @FXML
    public void handleCopyAction() {
        editMenu.handleCopyBtnAction(textArea);
    }

    @FXML
    public void handlePasteAction() {
        editMenu.handlePasteBtnAction(textArea);
    }

    @FXML
    public void handleDelAction() {
        editMenu.handleDelBtnAction(textArea);
    }

    @FXML
    public void handleFindAction() {
        editMenu.handleFindBtnAction(textArea);
    }

    //---------Searching, Replacing functions
    @FXML
    public void initialize() {
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                editMenu.handleSearchListAction();
            }
        });
    }

    @FXML
    public void handleFindNextAction() {
        editMenu.handleFindNextBtnAction(textArea);
    }

    @FXML
    public void handleFindPrevAction() {
        editMenu.handleFindPrevBtnAction(textArea);
    }

    @FXML
    public void handleGoToAction() {
        editMenu.handleGoToBtnAction(textArea);
    }

    @FXML
    public void handleReplaceAction() {
        editMenu.handleReplaceBtnAction(textArea);
    }

    @FXML
    public void handleSelectAllAction() {
        editMenu.handleSelectAllBtnAction(textArea);
    }

    @FXML
    public void handleTimeAction() {
        editMenu.handleTimeBtnAction(textArea);
    }

    // ----------------- Settings Menu

    @FXML
    public void handleWrapTextAction() {
        formatMenu.handleWrapTextAction(wrapTextBox, textArea);
    }

    @FXML
    public void handleFontAction() {
        formatMenu.handleFontAction(textArea);
    }

    // ------------------ Help Menu
    @FXML
    public void handleAboutAction() {
        helpMenu.handleAboutAction(textArea);
    }

}
