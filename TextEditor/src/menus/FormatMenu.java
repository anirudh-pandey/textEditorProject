package menus;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.util.*;

public class FormatMenu {
    public void handleWrapTextAction(CheckBox wrapTextBox, TextArea textArea) {
        textArea.setWrapText(!textArea.isWrapText());
        wrapTextBox.setSelected(textArea.isWrapText());
    }

    public void handleFontAction(TextArea textArea) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Fonts");
        final ComboBox<String> fontNamesBox = new ComboBox<>();
        fontNamesBox.getItems().addAll(Font.getFamilies());
        fontNamesBox.getSelectionModel().select(textArea.getFont().getFamily());
        fontNamesBox.setCellFactory(
                new Callback<>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        return new ListCell<>() {
                            {
                                super.setPrefWidth(100);
                            }

                            @Override
                            public void updateItem(String item,
                                                   boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    setFont(Font.font(item));
                                } else {
                                    setText(null);
                                }
                            }
                        };
                    }
                });

        final ComboBox<Double> fontSizeBox = new ComboBox<>();
        fontSizeBox.getItems().addAll(8.0,9.0,10.0,11.0,12.0,14.0,16.0,18.0,22.0,26.0,36.0,48.0,72.0);
        fontSizeBox.getSelectionModel().select(textArea.getFont().getSize());

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        grid.add(new Label("Font Names: "), 0, 0);
        grid.add(fontNamesBox, 1, 0);
        grid.add(new Label("Font Size: "), 0, 1);
        grid.add(fontSizeBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType applyBtn = new ButtonType("Apply", ButtonBar.ButtonData.APPLY);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(applyBtn, cancelBtn);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get().getText().equals(applyBtn.getText())) {
                textArea.setFont(Font.font(fontNamesBox.getSelectionModel().getSelectedItem(),
                        fontSizeBox.getSelectionModel().getSelectedItem()));
        }
    }
}

