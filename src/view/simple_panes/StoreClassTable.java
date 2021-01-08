package view.simple_panes;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.storeclasses.FieldName;
import model.storeclasses.StoreClass;

import java.util.ArrayList;

public class StoreClassTable extends TableView {

    public <T extends StoreClass> StoreClassTable(ArrayList<T> list, T dummyObject) {
        super();
        for (FieldName name : dummyObject.getFieldNames()) {
            TableColumn<StoreClass, String> temp =new TableColumn<>(name.getProgramName());
            temp.setCellValueFactory(new PropertyValueFactory<>(name.getProgramName()));
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
        getItems().setAll(list);
    }
}
