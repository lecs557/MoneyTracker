package view.simple_panes;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.storeclasses.Group;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class SumTable extends TableView<Group> {

    public SumTable() {

        //Zweck
        TableColumn<Group, String> name =new TableColumn<>("Group");
        name.setMinWidth(120);
        name.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Group, String> sumIntegerCellDataFeatures) {
                return new SimpleObjectProperty<String>(sumIntegerCellDataFeatures.getValue().getGroupName());
            }
        });
        getColumns().add(name);

        setGroups(SampleClass.getSampleGroups());

    }

    public void setGroups(ArrayList<Group> groups){
        getItems().clear();
        computeYears(groups);
        getItems().addAll(groups);
    }

    private void computeYears(ArrayList<Group> groups){
        //Jahre
        for(int year:groups.get(0).getYearSumMap().keySet()){

            TableColumn<Group, Integer> date =new TableColumn<>(""+year);
            date.setMinWidth(120);
            date.setCellValueFactory(new Callback<>() {
                @Override
                public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Group, Integer> sumIntegerCellDataFeatures) {
                    return new SimpleObjectProperty<Integer>(sumIntegerCellDataFeatures.getValue().getYearSumMap().get(year));
                }
            });
            date.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Group, Integer> call(TableColumn<Group, Integer> transactionStringTableColumn) {
                    TableCell<Group, Integer> cell = new TableCell<>() {
                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                getTableRow().setStyle("-fx-background-color: "+getTableRow().getItem().getColor()+";" );
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

        //Gesamt
        TableColumn<Group, Integer> gesamt =new TableColumn<>("Gesamt");
        gesamt.setMinWidth(120);
        gesamt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Group, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Group, Integer> sumIntegerCellDataFeatures) {
                return new SimpleObjectProperty<Integer>(sumIntegerCellDataFeatures.getValue().computeSum());
            }
        });
        gesamt.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Group, Integer> call(TableColumn<Group, Integer> transactionStringTableColumn) {
                TableCell<Group, Integer> cell = new TableCell<>() {
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
        getColumns().add(gesamt);

        }
}
