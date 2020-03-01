package menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;

public class EditMenu {

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
//        textArea.appendText(clipboard.getString());
    }

    public void handleDelBtnAction(TextArea textArea) {
        textArea.replaceSelection("");
    }

    // to enable/disable menuitems under edit Tab.
    public void handleEditTabAction(Menu editMenuElem, TextArea textArea) {
        if(editMenuElem != null && !editMenuElem.getItems().isEmpty()) {
            for(MenuItem item : editMenuElem.getItems()) {
                if(item.getText() != null) {
                    if (item.getText().equals("Cut") || item.getText().equals("Copy") || item.getText().equals("Delete")) {
                        if (!textArea.getSelectedText().isEmpty()) {
                            item.setDisable(false);
                        } else {
                            item.setDisable(true);
                        }
                    } else if (item.getText().equals("Paste")) {
                        if (Clipboard.getSystemClipboard().hasString()) {
                            item.setDisable(false);
                        } else {
                            item.setDisable(true);
                        }
                    }
                }
            }
        }
    }
}
