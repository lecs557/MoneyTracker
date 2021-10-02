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
        getStylesheets().add("view/style/tachart.css");
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
            data.getNode().setStyle("-fx-background-color:green");
            Tooltip.install(data.getNode(),new Tooltip(t.getPurpose()));
        }
        ((LocalDateAxis)getXAxis()).setYears(startYear,endYear);
    }

    public void connect(TransactionTabPane tabPane){
        series.getData().forEach(data -> {
            data.getNode().hoverProperty().addListener((observableValue, aBoolean, t1) -> {
               if(t1){
                   data.getNode().setStyle("-fx-background-color:yellow;");
               } else{
                   data.getNode().setStyle("-fx-background-color:green;");
               }

                tabPane.getTabs().forEach(tab -> {
                    if(tab.getText().equals(""+data.getXValue().getDate().getYear())) {
                        tabPane.getSelectionModel().select(tab);
                        TransactionTable table = ((TransactionTable)tab.getContent());
                        table.getRows().forEach(row -> {
                            if (row.getItem() != null) {
                                if (t1 && data.getXValue().getDate().equals(row.getItem().getLocalDate()) &&
                                        data.getYValue().doubleValue() == (double) row.getItem().getBalance() / 100) {
                                    table.scrollTo(row.getItem());
                                    row.setStyle("-fx-background-color:green;");
                                } else if(!t1) {
                                    row.setStyle("-fx-background-color:white;");
                                    table.scrollTo(0);
                                }
                            }
                        });
                    }
                });
            });
        });
    }
}
