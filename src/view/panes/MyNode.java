package view.panes;

import javafx.fxml.FXMLLoader;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MyNode  {

    public Control node;

    public MyNode(String path)  {
        try {
            node = FXMLLoader.load(getClass().getResource("/view/panes"+path+".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putInto(Pane container){
        node.setPrefHeight(container.getPrefHeight());
        node.setPrefWidth(container.getPrefWidth());
        container.getChildren().clear();
        container.getChildren().add(node);
    }
}
