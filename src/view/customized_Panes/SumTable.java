package view.customized_Panes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Main;
import model.Sum;
import model.Transaction;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class SumTable extends TableView<Sum> {

    public SumTable() {
        TableColumn<Sum, String> name =new TableColumn<>("Zweck");
        name.setMinWidth(120);
        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sum, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sum, String> sumIntegerCellDataFeatures) {
                return new SimpleObjectProperty<String>(sumIntegerCellDataFeatures.getValue().getReason());
            }
        });
        getColumns().add(name);
        ObservableList<ObservableList<Transaction>> years = Main.currentAccount.getYears_Transaction();
        for(int i=0;i<years.size();i++){
            int year = years.get(i).get(0).getDate().getYear();
            TableColumn<Sum, Integer> date =new TableColumn<>(""+year);
            date.setMinWidth(120);
            date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sum, Integer>, ObservableValue<Integer>>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Sum, Integer> sumIntegerCellDataFeatures) {
                    return new SimpleObjectProperty<Integer>(sumIntegerCellDataFeatures.getValue().getSum().get(year));
                }
            });
            date.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Sum, Integer> call(TableColumn<Sum, Integer> transactionStringTableColumn) {
                    TableCell<Sum, Integer> cell = new TableCell<>() {
                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                Label l = new Label(new DecimalFormat("#0.00").format((double)item/100)+" €");
                                if (item > 0){
                                    l.setStyle("-fx-text-fill: green;");
                                } else if (item < 0) {
                                    l.setStyle("-fx-text-fill: red;");
                                } else {
                                }
                                setGraphic(l);// TODO mit Scenebuilder
                            }
                        }
                    };
                    cell.setAlignment(Pos.CENTER);
                    return cell;
                }
            });
            getColumns().add(date);
        }
     getItems().setAll(Main.currentAccount.getSums());
    }
}
