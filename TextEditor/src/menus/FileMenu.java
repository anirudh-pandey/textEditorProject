package menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMenu {
    private String fileName;
    private File currentFile;
    private final String appName = "Notebook";

    public FileMenu() {
        fileName = "untitled";
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setCurrentFile(File file) {
        this.currentFile = file;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void handleNewBtnAction(TextArea textArea) {
        setFileName("untitled");
        setCurrentFile(null);
        Stage currentStage = (Stage) textArea.getScene().getWindow();
        currentStage.setTitle(this.getFileName() + " - " + this.appName);
        currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/notebookIcon.png")));
        textArea.setText("");
    }

    public void handleNewWindowBtnAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/sample.fxml"));
        Parent root = loader.load();
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle(this.getFileName() + " - " + this.appName);
        secondaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/notebookIcon.png")));
        secondaryStage.setScene(new Scene(root, 600, 450));
        secondaryStage.show();
    }

    public void handleOpenBtnAction(TextArea textArea) {
        // code to open "file selector dialog"
        if((!isFileSaved(textArea) && notSavedDialog(textArea)) || isFileSaved(textArea)) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fileChooser.showOpenDialog(textArea.getScene().getWindow());
            setTextEditorContents(file, textArea);
        }
    }

    public void setTextEditorContents(File file, TextArea textArea) {
        if(file != null) {
            setFileName(file.getName());
            setCurrentFile(file);
            try {
                String content = Files.readString(Paths.get(file.getAbsolutePath()));
                if(content != null) {
                    textArea.setText(content);
                    Stage currentStage = (Stage) textArea.getScene().getWindow();
                    currentStage.setTitle(getCurrentFile().getName() + " - " + this.appName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean notSavedDialog(TextArea textArea) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle(this.appName);
        alert.setContentText("Do you want to save changes to " + getFileName());
        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.LEFT);
        ButtonType dontSaveBtn = new ButtonType("Don't Save");
        alert.getButtonTypes().addAll(saveBtn, dontSaveBtn, ButtonType.CANCEL);
        alert.showAndWait();

        if(alert.getResult() == saveBtn) {
            handleSaveBtnAction(textArea);
            return true;
        } else if(alert.getResult() == dontSaveBtn) {
            return true;
        } else if(alert.getResult() == ButtonType.CANCEL) {
            alert.close();
            return false;
        }
        return false;
    }

    // different language text files not supported
    public boolean isFileSaved(TextArea textArea) {
        try {
            if(getCurrentFile() == null && !textArea.getText().isEmpty()) {
                return false;
            } else if(getCurrentFile() != null) {
                String content = Files.readString(getCurrentFile().toPath());
                if(content != null) {
                    return content.equals(textArea.getText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void handleSaveBtnAction(TextArea textArea) {
        if(getCurrentFile() != null) {
            saveFile(getCurrentFile(), textArea);
        } else {
            handleSaveAsBtnAction(textArea);
        }
    }

    public void handleSaveAsBtnAction(TextArea textArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("*.txt");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File file = fileChooser.showSaveDialog(textArea.getScene().getWindow());
        if(file != null) {
            setFileName(file.getName());
            setCurrentFile(file);
            saveFile(file, textArea);
            setTextEditorContents(file, textArea);
        }
    }

    // method to write contents of textArea into the file.
    private void saveFile(File file, TextArea textArea) {
        if(file != null) {
            try {
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()));
                writer.write(textArea.getText());

                Stage currentStage = (Stage) textArea.getScene().getWindow();
                currentStage.setTitle(getCurrentFile().getName() + " - " + this.appName);
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleExitBtnAction(TextArea textArea) {
        if(!isFileSaved(textArea) && notSavedDialog(textArea) || isFileSaved(textArea)) {
            Stage currentStage = (Stage) textArea.getScene().getWindow();
            currentStage.close();
        }
    }

}
