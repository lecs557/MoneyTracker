package view.panes;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import model.PathStore;

import java.io.IOException;

public class MyNode extends Region {

    private FXMLLoader loader;
    private SimpleStringProperty path = new SimpleStringProperty(PathStore.ENTRYPANE+"StringEntry");

    public MyNode()  {
        construct();
        path.addListener((observableValue, s, t1) -> construct());
    }

    public MyNode(String path){
        this.path.set(path);
        construct();
    }

    private void construct(){
        try {
            loader = new FXMLLoader(getClass().getResource( pathProperty().get()+".fxml"));
            getChildren().clear();
            getChildren().add(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContent(String content) {
        if (loader.getController() instanceof contentable) {
            ((contentable) loader.getController()).setContent(content);
        }
    }

    public String getContent(){
        if(loader.getController() instanceof contentable){
            return ((contentable) loader.getController()).getContent();
        }
        return "";
    }

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path){
        this.path.set(path);
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
