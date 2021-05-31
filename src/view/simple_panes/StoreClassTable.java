package view.simple_panes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.storeclasses.FieldName;
import model.storeclasses.ForeignKey;
import model.storeclasses.StoreClass;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Die Tabelle, wie sie auch in der DB gespeichert ist
 */
public class StoreClassTable extends TableView {

    public <T extends StoreClass> StoreClassTable(ArrayList<T> list, T dummyObject) {
        super();
        for (Field field : dummyObject.getClass().getClasses()[1].getFields()) {
            FieldName name = null;
            try {
                name = (FieldName) field.get(dummyObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            TableColumn<StoreClass, String> temp = new TableColumn<>(name.getProgramName());

            if(name.getProgramName().equals("Id")){
                temp.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue().getId()+""));
            } else{
                temp.setCellValueFactory(new PropertyValueFactory<>(name.getProgramName()));
            }
            temp.setCellFactory(new Callback<TableColumn<StoreClass, String>, TableCell<StoreClass, String>>() {
                public TableCell<StoreClass, String> call(TableColumn<StoreClass, String> tStringTableColumn) {
                    TableCell<StoreClass, String> cell = new TableCell<>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                setText(item);
                            }
                        }
                    };
                    return cell;
                }
            });
            getColumns().add(temp);
        }
        for(Field field: dummyObject.getClass().getClasses()[0].getFields()){
            ForeignKey<? extends StoreClass> foreignKey = null;
            try {
                foreignKey = (ForeignKey<? extends StoreClass>) field.get(dummyObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            TableColumn<StoreClass, Integer> temp = new TableColumn<>(foreignKey.getProgramName());
            temp.setCellValueFactory(new PropertyValueFactory<>(foreignKey.getProgramName()));
            temp.setCellFactory(new Callback<TableColumn<StoreClass, Integer>, TableCell<StoreClass, Integer>>() {
                public TableCell<StoreClass, Integer> call(TableColumn<StoreClass, Integer> tStringTableColumn) {
                    TableCell<StoreClass, Integer> cell = new TableCell<>() {
                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                setText(item.toString());
                            }
                        }
                    };
                    return cell;
                }
            });
            getColumns().add(temp);
        }
        getItems().setAll(list);
    }
}
