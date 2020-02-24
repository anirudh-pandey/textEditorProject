package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {
    @FXML private TextArea textArea;


    protected Stage primaryStage;
    protected String fileName = "untitled";
    protected File currentFile;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    protected void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected String getFileName() {
        return this.fileName;
    }

    protected void setCurrentFile(File file) {
        this.currentFile = file;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    @FXML
    protected void handleSaveBtnAction(ActionEvent actionEvent) {
        handleSaveBtnAction();
    }

    protected void handleSaveBtnAction() {
        if(getCurrentFile() != null) {
            saveFile(getCurrentFile());
        } else {
            handleSaveAsBtnAction();
        }
    }

    @FXML
    protected void handleSaveAsBtnAction(ActionEvent actionEvent) {
        handleSaveAsBtnAction();
    }

    protected void handleSaveAsBtnAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("*.txt");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null) {
            setFileName(file.getName());
            setCurrentFile(file);
            saveFile(file);
            setTextEditorContents(file);
        }
    }

    // method to write contents of textArea into the file.
    private void saveFile(File file) {
        if(file != null) {
            try {
                System.out.println("||||--- "+file.getAbsolutePath()+" ---||||");
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()));
                writer.write(textArea.getText());
                primaryStage.setTitle(getCurrentFile().getName() + " - World's Greatest IDE");
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleNewBtnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        setFileName("untitled");
        setCurrentFile(null);
        this.primaryStage.setTitle(getFileName() + " - World's Greatest IDE");
        this.primaryStage.setScene(new Scene(root, 500, 375));
        this.primaryStage.show();
    }
   
    @FXML
    protected void handleNewWindowBtnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("untitled - World's Greatest IDE");
        secondaryStage.setScene(new Scene(root, 500, 375));
        secondaryStage.show();
    }

    @FXML
    protected void handleExitBtnAction(ActionEvent actionEvent) {
        if(!isFileSaved() && notSavedDialog() || isFileSaved()) {
            Platform.exit();
        }
    }

    @FXML
    protected void handleOpenBtnAction(ActionEvent actionEvent) {
        // code to open "file selector dialog"
        if((!isFileSaved() && notSavedDialog()) || isFileSaved()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fileChooser.showOpenDialog(primaryStage);

            setTextEditorContents(file);
        }

    }

    protected void setTextEditorContents(File file) {
        if(file != null) {
            setFileName(file.getName());
            setCurrentFile(file);
            String content = null;
            try {
                content = Files.readString(Paths.get(file.getAbsolutePath()));
                if(content != null) {
                    textArea.setText(content);
                    System.out.println("---||---   "+getFileCharset(getCurrentFile())+"   ---||---");
                    primaryStage.setTitle(getCurrentFile().getName() + " - World's Greatest IDE");
                }
            } catch (IOException e) {
                System.out.println("SET TEXTAREA, FILE");
                e.printStackTrace();
            }
        }
    }

    protected boolean notSavedDialog() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("World's Greatest IDE");
        alert.setContentText("Do you want to save changes to " + getFileName());
        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.LEFT);
        ButtonType dontSaveBtn = new ButtonType("Don't Save");
        alert.getButtonTypes().addAll(saveBtn, dontSaveBtn, ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult() == saveBtn) {
            handleSaveBtnAction();
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
    protected boolean isFileSaved() {
        try {
            if(getCurrentFile() == null && !textArea.getText().isEmpty()) {
              return false;
            } else if(getCurrentFile() != null) {
                System.out.println("------   "+getFileCharset(getCurrentFile())+"   ------");
//                String content = Files.readString(Paths.get(getCurrentFile().getAbsolutePath()));
                String content = Files.readString(getCurrentFile().toPath());
//                System.out.println(content);
                if(content != null) {
                    System.out.println(content.equals(textArea.getText()) + "--" + getCurrentFile().getAbsolutePath());
                    return content.equals(textArea.getText());
                }
//                textArea.setText(textArea.getText() + "Dont need to save");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public String getFileCharset(File file) throws IOException {
        byte[] buf = new byte[4096];
        java.io.FileInputStream fis = new java.io.FileInputStream(file.getAbsolutePath());
        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        if (encoding != null) {
            System.out.println("Detected encoding = " + encoding);
        } else {
            System.out.println("No encoding detected.");
        }
        detector.reset();
        return encoding;
    }

}
