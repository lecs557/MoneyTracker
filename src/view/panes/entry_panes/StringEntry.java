package view.panes.entry_panes;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.PathStore;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;

public class StringEntry extends EntryPane {

    private TextField textField = new TextField();

    public StringEntry(String name, StoreClass storeClass) {
        super(name, storeClass);
        getChildren().add(textField);
    }

    @Override
    public String getContent() {
        return textField.getText();
    }

    @Override
    public void setContent(String content) {
        textField.setText(content);
    }
}
