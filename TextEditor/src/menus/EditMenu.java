package menus;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditMenu {

    private List<int[]> wordMatchList = new ArrayList<>();
    private int currentIndex = -1;
    private String lastSearchedWord = "";
    private Matcher matcher;
    private final String appName = "Notebook";

    public void handleUndoBtnAction(TextArea textArea) {
        textArea.undo();
    }

    public void handleSelectAllBtnAction(TextArea textArea) {
        textArea.selectAll();
    }

    public void handleCutBtnAction(TextArea textArea) {
        textArea.cut();
//        handleCopyBtnAction(textArea);
//        handleDelBtnAction(textArea);
    }

    public void handleCopyBtnAction(TextArea textArea) {
        textArea.copy();
//        Clipboard clipboard = Clipboard.getSystemClipboard();
//        ClipboardContent cContent = new ClipboardContent();
//        cContent.putString(textArea.getSelectedText());
//        clipboard.setContent(cContent);
    }

    public void handlePasteBtnAction(TextArea textArea) {
        textArea.paste();
//        Clipboard clipboard = Clipboard.getSystemClipboard();
//        textArea.insertText(textArea.getCaretPosition(), clipboard.getString());
    }

    public void handleDelBtnAction(TextArea textArea) {
        textArea.replaceSelection("");
    }

    public void handleTimeBtnAction(TextArea textArea) {
        textArea.insertText(textArea.getCaretPosition(), LocalDateTime.now().toString());
    }

    // to enable/disable menuitems under edit Tab.
    public void handleEditTabAction(Menu editMenuElem, TextArea textArea) {
        if(editMenuElem != null && !editMenuElem.getItems().isEmpty()) {
            for(MenuItem item : editMenuElem.getItems()) {
                if(item != null && item.getId() != null) {
                    switch (item.getId()) {
                        case "cutMenuItem":
                        case "copyMenuItem":
                        case "deleteMenuItem":
                            if (!textArea.getSelectedText().isEmpty()) {
                                item.setDisable(false);
                            } else {
                                item.setDisable(true);
                            }
                            break;
                        case "pasteMenuItem":
                            if (Clipboard.getSystemClipboard().hasString()) {
                                item.setDisable(false);
                            } else {
                                item.setDisable(true);
                            }
                            break;
                        case "undoMenuItem":
                            if (textArea.isUndoable()) {
                                item.setDisable(false);
                            } else {
                                item.setDisable(true);
                            }
                            break;
                        case "findMenuItem":
                        case "findNextMenuItem":
                        case "findPrevMenuItem":
                        case "replaceMenuItem":
                            item.setDisable(textArea.getText().isEmpty());
                            break;
                        case "gotoMenuItem":
                            item.setDisable(textArea.isWrapText());
                            break;
                    }
                }
            }
        }
    }
    public void handleFindBtnAction(TextArea textArea) {
        Dialog<ButtonType> findDialog = new Dialog<>();
        findDialog.setTitle("Find");

        TextField searchTextField = new TextField();
        searchTextField.setText(this.lastSearchedWord);
        CheckBox matchCaseBox = new CheckBox("Match Case");
        CheckBox wrapTextBox = new CheckBox("Wrap Text");
        wrapTextBox.selectedProperty().addListener((ov, old_val, new_val) -> textArea.setWrapText(new_val));
        wrapTextBox.setSelected(textArea.isWrapText());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));
        grid.add(new Label("Find What:"), 0, 0);
        grid.add(searchTextField, 1, 0);
        grid.add(matchCaseBox, 0, 1);
        grid.add(wrapTextBox, 1, 1);

        findDialog.getDialogPane().setContent(grid);

        ButtonType applyBtn = new ButtonType("Find", ButtonBar.ButtonData.APPLY);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        findDialog.getDialogPane().getButtonTypes().addAll(applyBtn, cancelBtn);
        findDialog.initOwner(textArea.getScene().getWindow());

        Optional<ButtonType> result = findDialog.showAndWait();
        if(result.isPresent() && result.get().getText().equals(applyBtn.getText())) {
            if(!searchTextField.getText().isEmpty()) {
                this.lastSearchedWord = searchTextField.getText();
                this.findString(this.lastSearchedWord, matchCaseBox.isSelected(), textArea);
                this.selectString(textArea);
            }
        }
    }


    private void findString(String searchText, boolean isMatchCase, TextArea textArea)
    {
        Pattern pattern = Pattern.compile(isMatchCase ? searchText : searchText.toLowerCase());
        matcher = pattern.matcher(isMatchCase ? textArea.getText() : textArea.getText().toLowerCase());
        while(matcher.find()) {
            wordMatchList.add(new int[]{matcher.start(), matcher.end()});
        }
    }

    private void showMessageDialog(TextArea textArea, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(this.appName);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().addAll(ButtonType.CANCEL);
        alert.initOwner(textArea.getScene().getWindow());
        alert.showAndWait();
    }

    private void findString(String searchText, TextArea textArea) {
        this.findString(searchText, false, textArea);
    }

    private void selectString(TextArea textArea) {
        for(int i=0; i<wordMatchList.size(); i++) {
            if (textArea.getCaretPosition() >= wordMatchList.get(i)[0]) {
                this.currentIndex = (i + 1) % wordMatchList.size();
            }
        }

        if(this.currentIndex >= 0)
            textArea.selectRange(wordMatchList.get(this.currentIndex)[0], wordMatchList.get(this.currentIndex)[1]);
        else
            this.showMessageDialog(textArea, "cannot find word \"" + this.lastSearchedWord + "\"");
    }

    public void handleFindNextBtnAction(TextArea textArea) {
        if(currentIndex > -1) {
            currentIndex = (currentIndex+1)%wordMatchList.size();
            int[] position = wordMatchList.get(currentIndex);
            textArea.selectRange(position[0], position[1]);
        } else if(!this.lastSearchedWord.isEmpty()) {
            findString(this.lastSearchedWord, textArea);
            this.selectString(textArea);
        }  else if(this.lastSearchedWord.isEmpty()) {
            handleFindBtnAction(textArea);
        }
    }

    public void handleFindPrevBtnAction(TextArea textArea) {
        if(currentIndex > -1) {
            currentIndex = currentIndex == 0 ? wordMatchList.size() -1 : --currentIndex;
            int[] position = wordMatchList.get(currentIndex);
            textArea.selectRange(position[0], position[1]);
        } else if(!this.lastSearchedWord.isEmpty()) {
            findString(this.lastSearchedWord, textArea);
            this.selectString(textArea);
        } else if(this.lastSearchedWord.isEmpty()) {
            handleFindBtnAction(textArea);
        }
    }

    // If textArea gets changed clear the positions from the searchList.
    public void handleSearchListAction() {
        if(wordMatchList != null) {
            wordMatchList.clear();
            currentIndex = -1;
        }
    }

    public void handleGoToBtnAction(TextArea textArea) {
        // It would work only if wrap text is off
        if(!textArea.isWrapText()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Go To Line");

            TextField gotoTextField = new TextField("1");
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 50, 10, 10));
            grid.add(new Label("Line Number: "), 0, 0);
            grid.add(gotoTextField, 1, 0);
            dialog.getDialogPane().setContent(grid);

            ButtonType applyBtn = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(applyBtn, cancelBtn);
            dialog.initOwner(textArea.getScene().getWindow());

            // Traditional way to get the response value.
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get().getText().equals(applyBtn.getText())){
                if (!gotoTextField.getText().isEmpty() && isInteger(gotoTextField.getText())) {
                    int count = 0, position = 0;
                    for (String line : textArea.getText().split("\\n")) {
                        if (++count == Integer.parseInt(gotoTextField.getText())) {
                            textArea.positionCaret(position);
                            return;
                        }
                        position += line.length() + 1;
                    }
                    this.showMessageDialog(textArea, "The line number is beyond the total number of lines");
                } else {
                    this.showMessageDialog(textArea, "Please enter a valid input");
                }
            }
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public void handleReplaceBtnAction(TextArea textArea) {
        Dialog<ButtonType> findDialog = new Dialog<>();
        findDialog.setTitle("Replace All");

        TextField findTextField = new TextField();
        findTextField.setText(this.lastSearchedWord);
        TextField replaceTextField = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));

        grid.add(new Label("Find What: "), 0, 0);
        grid.add(findTextField, 1, 0);
        grid.add(new Label("Replace with: "), 0, 1);
        grid.add(replaceTextField, 1, 1);

        findDialog.getDialogPane().setContent(grid);

        ButtonType replaceBtn = new ButtonType("Replace", ButtonBar.ButtonData.APPLY);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        findDialog.getDialogPane().getButtonTypes().addAll(replaceBtn, cancelBtn);
        findDialog.initOwner(textArea.getScene().getWindow());

        Optional<ButtonType> result = findDialog.showAndWait();
        if(result.isPresent() && result.get().getText().equals(replaceBtn.getText())) {
            if(!findTextField.getText().isEmpty() && !replaceTextField.getText().isEmpty()) {
                if(!findTextField.getText().equals(this.lastSearchedWord)) {
                    findString(findTextField.getText(), textArea);
                }
                if(matcher != null)
                    textArea.setText(matcher.replaceAll(replaceTextField.getText()));
                    this.handleSearchListAction();
            }
        }
    }

}
