package view.simple_panes;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import model.Main;
import model.MyDate;

import java.util.ArrayList;

public class TransactionChart extends LineChart<MyDate,Number> {

    public TransactionChart() {
        super(new LocalDateAxis(0,1200), new NumberAxis());
        getXAxis().setAutoRanging(false);
        Series<MyDate,Number> series = new Series<>();
//        for (ArrayList<Data<MyDate,Number>> t:Main.currentAccount.getYears_data()){
//            series.getData().addAll(t);
//        }
        getData().add(series);
        series.setName("Mein Konto");
    }

    public void putInto(Pane container){
        setPrefHeight(container.getPrefHeight());
        setPrefWidth(container.getPrefWidth());
        container.getChildren().clear();
        container.getChildren().add(this);
    }
}
