package menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
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

    public void handleNewBtnAction(TextArea textArea) throws IOException {
        setFileName("untitled");
        setCurrentFile(null);
        Stage currentStage = (Stage) textArea.getScene().getWindow();
        currentStage.setTitle(getFileName() + " - World's Greatest IDE");
        textArea.setText("");
    }

    public void handleNewWindowBtnAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/sample.fxml"));
        Parent root = loader.load();
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("untitled - World's Greatest IDE");
        secondaryStage.setScene(new Scene(root, 500, 375));
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
            File file = fileChooser.showOpenDialog((Stage)textArea.getScene().getWindow());
            setTextEditorContents(file, textArea);
        }
    }

    public void setTextEditorContents(File file, TextArea textArea) {
        if(file != null) {
            setFileName(file.getName());
            setCurrentFile(file);
            String content = null;
            try {
                content = Files.readString(Paths.get(file.getAbsolutePath()));
                if(content != null) {
                    textArea.setText(content);
                    Stage currentStage = (Stage) textArea.getScene().getWindow();
                    currentStage.setTitle(getCurrentFile().getName() + " - World's Greatest IDE");
                }
            } catch (IOException e) {
                System.out.println("SET TEXTAREA, FILE");
                e.printStackTrace();
            }
        }
    }

    public boolean notSavedDialog(TextArea textArea) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle("World's Greatest IDE");
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
            System.out.println("IS FILE SAVED___________");
            if(getCurrentFile() == null && !textArea.getText().isEmpty()) {
                System.out.println("___________1___________");
                return false;
            } else if(getCurrentFile() != null) {
                String content = Files.readString(getCurrentFile().toPath());
                if(content != null) {
                    System.out.println(content.equals(textArea.getText()) + "--" + getCurrentFile().getAbsolutePath());
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
        File file = fileChooser.showSaveDialog((Stage)textArea.getScene().getWindow());
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
                System.out.println("||||--- "+file.getAbsolutePath()+" ---||||");
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()));
                writer.write(textArea.getText());

                Stage currentStage = (Stage) textArea.getScene().getWindow();
                currentStage.setTitle(getCurrentFile().getName() + " - World's Greatest IDE");
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

//    public String getFileCharset(File file) throws IOException {
//        byte[] buf = new byte[4096];
//        java.io.FileInputStream fis = new java.io.FileInputStream(file.getAbsolutePath());
//        UniversalDetector detector = new UniversalDetector(null);
//
//        int nread;
//        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
//            detector.handleData(buf, 0, nread);
//        }
//        detector.dataEnd();
//        String encoding = detector.getDetectedCharset();
//        if (encoding != null) {
//            System.out.println("Detected encoding = " + encoding);
//        } else {
//            System.out.println("No encoding detected.");
//        }
//        detector.reset();
//        return encoding;
//    }

}
