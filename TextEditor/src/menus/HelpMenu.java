package menus;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelpMenu {
    private final String appName = "Notebook";

    public void handleAboutAction(TextArea textArea) {
        Dialog aboutDialog = new Dialog();
        aboutDialog.setTitle(this.appName);
        GridPane grid = new GridPane();
        Text message = new Text("\"" + this.appName + "\" brought to you by Anirudh " + "\uD83D\uDE00");
        message.setFont(Font.font("Great Vibes", 22));
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(20, 10, 10, 10));
        grid.add(message, 0, 0);

        aboutDialog.initOwner(textArea.getScene().getWindow());
        aboutDialog.getDialogPane().setContent(grid);
        ButtonType cancelBtn = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        aboutDialog.getDialogPane().getButtonTypes().add(cancelBtn);
        aboutDialog.showAndWait();
    }
}
