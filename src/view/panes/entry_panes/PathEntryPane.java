package view.panes.entry_panes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.storeclasses.StoreClass;
import view.simple_panes.PaneUtils;

public class PathEntryPane extends EntryPane {

    private HBox pane = new HBox();
    private Label fileName;

    public PathEntryPane(String name, StoreClass storeClass) {
        super(name, storeClass);
        fileName = new Label();
        Button browse = new Button("...");
        getChildren().addAll(fileName, browse);

        browse.setOnMouseClicked(mouseEvent -> fileName.setText(PaneUtils.browseOnePdfFile().getPath()));
    }

    @Override
    public String getContent() {
        return fileName.getText();
    }

    @Override
    public void setContent(String content) {
        fileName.setText(content);
    }
}
