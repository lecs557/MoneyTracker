package view.panes;

import javafx.fxml.FXMLLoader;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class MyNode  {

    private FXMLLoader loader;
    public Region node;

    public MyNode(String path)  {
        try {
            loader = new FXMLLoader(getClass().getResource("/view/panes/"+path+".fxml"));
            node = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FXMLLoader putInto(Pane container){
        node.setPrefHeight(container.getPrefHeight());
        node.setPrefWidth(container.getPrefWidth());
        container.getChildren().clear();
        container.getChildren().add(node);
        return loader;
    }
}
