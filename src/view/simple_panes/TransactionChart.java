package view.simple_panes;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tab;
import javafx.scene.control.TableRow;
import javafx.scene.control.Tooltip;
import model.MyDate;
import model.storeclasses.Transaction;

import java.util.ArrayList;

public class TransactionChart extends LineChart<MyDate,Number> {

    private  Series<MyDate,Number> series;

    public TransactionChart() {
        super(new LocalDateAxis(), new NumberAxis());
        getStylesheets().add(getClass().getResource("/view/style/tachart.css").toString());
        getXAxis().setAutoRanging(false);
        series = new Series<>();
        getData().add(series);
        series.setName("Mein Konto");
        applyTransactions(SampleClass.getSampleTransactions());
    }

    public void applyTransactions(ArrayList<Transaction> transactions){
        series.getData().clear();
        if(transactions.isEmpty()) return;
        int startYear = transactions.get(0).getLocalDate().getYear();
        int endYear = transactions.get(transactions.size()-1).getLocalDate().getYear();
        for (Transaction t:transactions){
            Data<MyDate,Number> data = new Data<>(new MyDate(t.getLocalDate()),(double)t.getBalance()/100);
            series.getData().add(data);
            Tooltip.install(data.getNode(),new Tooltip(t.getPurpose()));
        }
        ((LocalDateAxis)getXAxis()).setYears(startYear,endYear);
    }

    public void connect(TransactionTabPane tabPane){
        // every data point of chart has a hover listener
        series.getData().forEach(data -> {
            data.getNode().hoverProperty().addListener((observableValue, aBoolean, t1) -> {
               if(t1){
                   data.getNode().getStyleClass().add("marked");
               } else{
                   data.getNode().getStyleClass().remove("marked");
               }

               // the hovered data selects the tab
                tabPane.getTabs().forEach(tab -> {
                    if(tab.getText().equals(""+data.getXValue().getDate().getYear())) {
                        tabPane.getSelectionModel().select(tab);
                        TransactionTable table = ((TransactionTable)tab.getContent());
                        boolean found = false;
                        for(TableRow<Transaction> row:table.getRows()){
                            if (row.getItem() != null) {
                                if (t1 && data.getXValue().getDate().equals(row.getItem().getLocalDate()) &&
                                        data.getYValue().doubleValue() == (double) row.getItem().getBalance() / 100) {
                                    found = true;
                                    row.getStyleClass().add("marked");
                                } else if(!t1) {
                                    row.getStyleClass().remove("marked");
                                }
                            }
                        }
                        if(!found){
                            Transaction target=null;
                            for (Transaction t:table.getItems()){
                                if (t1 && data.getXValue().getDate().equals(t.getLocalDate()) &&
                                        data.getYValue().doubleValue() == (double) t.getBalance() / 100) {
                                    target = t;
                                    break;
                                }
                            }
                            table.scrollTo(target);
                            table.getRows().get(0).getStyleClass().add("marked");
                        }
                    }
                });
            });
        });
    }
}
