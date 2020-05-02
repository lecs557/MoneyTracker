package view.customized_Panes;

import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.Main;
import model.MyDate;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionChart extends LineChart<MyDate,Number> {

    public TransactionChart() {
        super(new LocalDateAxis(0,1200), new NumberAxis());
        getXAxis().setAutoRanging(false);
        Series<MyDate,Number> series = new Series<>();
        for (ObservableList<Data<MyDate,Number>> t:Main.currentAccount.getYears_data()){
            series.getData().addAll(t);
        }
        getData().add(series);
        series.setName("Mein Konto");
    }
}
