package view.simple_panes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Region;

import java.lang.reflect.InvocationTargetException;

public class MyClassNode extends Region {

    private Region region;
    private SimpleStringProperty className = new SimpleStringProperty("view.simple_panes.PDFViewer");

    public MyClassNode(String path){

    }


    public MyClassNode() {

        try {
            region = (Region) Class.forName(classNameProperty().get()).getDeclaredConstructor().newInstance();
            getChildren().clear();
            getChildren().add(region);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        className.addListener((observableValue, s, t1) -> {
            try {
                region = (Region) Class.forName(classNameProperty().get()).getDeclaredConstructor().newInstance();
                getChildren().clear();
                getChildren().add(region);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }


    public SimpleStringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public String getClassName() {
        return className.get();
    }

    public Region getRegion() {
        return region;
    }
}
