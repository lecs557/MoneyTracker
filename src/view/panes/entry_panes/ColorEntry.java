package view.panes.entry_panes;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;
import view.panes.MyNode;

public class ColorEntry extends EntryPane {

    private ColorPicker colorPicker = new ColorPicker();

    public ColorEntry(String name, StoreClass storeClass) {
        super(name, storeClass);
        colorPicker.getStyleClass().add("color");
        getChildren().add(colorPicker);
    }

    @Override
    public String getContent() {
       return String.format( "#%02X%02X%02X",
                (int)( colorPicker.getValue().getRed() * 255 ),
                (int)( colorPicker.getValue().getGreen() * 255 ),
                (int)( colorPicker.getValue().getBlue() * 255 ) );
    }

    @Override
    public void setContent(String content) {
        colorPicker.setValue(Color.web(content));
    }
}
