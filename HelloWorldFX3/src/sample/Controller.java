package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {
    @FXML private TextArea textArea;

    protected Stage primaryStage;
    protected String fileName = "untitled";
    protected File currentFile;

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
            System.out.println("SAving.....");
        } else {
            handleSaveAsBtnAction();
            System.out.println("SAve Asing.....");
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
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("CSS", "*.css"),
                new FileChooser.ExtensionFilter("Java", "*.java"),
                new FileChooser.ExtensionFilter("C", "*.c"),
                new FileChooser.ExtensionFilter("Py", "*.py"),
                new FileChooser.ExtensionFilter("Js", "*.js"),
                new FileChooser.ExtensionFilter("Json", "*.json"),
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Based Files", "*.txt"
//                , "*.html", "*.css", "*.js", "*.java", "*.py", "*.c"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file != null) {
            setFileName(file.getName());
            setCurrentFile(file);
            saveFile(file);
        }
    }

    // method to write contents of textArea into the file.
    private void saveFile(File file) {
        if(file != null) {
            Charset charset = Charset.forName("UTF-8");

            try(BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
                writer.write(textArea.getText());
                primaryStage.setTitle(getFileName() + " - World's Greatest IDE");
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleNewBtnAction(ActionEvent actionEvent) {

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
    protected void handleOpenBtnAction(ActionEvent actionEvent) throws IOException {
        // code to open "file selector dialog"
        if((!isFileSaved() && notSavedDialog()) || isFileSaved()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt"),
                    new FileChooser.ExtensionFilter("HTML", "*.html"),
                    new FileChooser.ExtensionFilter("CSS", "*.css"),
                    new FileChooser.ExtensionFilter("Java", "*.java"),
                    new FileChooser.ExtensionFilter("C", "*.c"),
                    new FileChooser.ExtensionFilter("Py", "*.py"),
                    new FileChooser.ExtensionFilter("Js", "*.js"),
                    new FileChooser.ExtensionFilter("Json", "*.json"),
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File file = fileChooser.showOpenDialog(primaryStage);

            if(file != null) {
                setFileName(file.getName());
                setCurrentFile(file);
                System.out.println("====================" + file.toPath());
                String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                if(content != null) {
                    textArea.setText(content);
                    primaryStage.setTitle(getFileName() + " - World's Greatest IDE");
                }
//                Charset charset = Charset.forName("US-ASCII");
//                BufferedReader reader = Files.newBufferedReader(file.toPath(), charset);
//                String line = null;
//                while((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
            }

//            if(file != null) {
//                // code to display the contents of file.
//                setFileName(file.getName());
//                setCurrentFile(file);
//                String content = Files.readString(Paths.get(file.getAbsolutePath()));
//                if(content != null) {
//                    textArea.setText(content);
//                    primaryStage.setTitle(getFileName() + " - World's Greatest IDE");
//                }
//            }
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

    protected boolean isFileSaved() {
        try {
            if(getCurrentFile() == null && !textArea.getText().isEmpty()) {
                System.out.println("1");
              return false;
            } else if(getCurrentFile() != null) {
//                String content = Files.readString(Paths.get(getCurrentFile().getAbsolutePath()));
                String content = Files.readString(getCurrentFile().toPath(), StandardCharsets.UTF_8);
//                System.out.println(content);
                if(content != null) {
                    System.out.println(content.equals(textArea.getText()) + "--" + getCurrentFile().getAbsolutePath());
                }
                return content.equals(textArea.getText());
//                textArea.setText(textArea.getText() + "Dont need to save");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("3");
        return true;

    }

}
