package view.customized_Panes;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.Main;
import model.MyDate;

import java.time.LocalDate;

public class TransactionChart extends LineChart<MyDate,Number> {

    public TransactionChart() {
        super(new LocalDateAxis(), new NumberAxis());
        getXAxis().setAutoRanging(false);
        Series<MyDate,Number> series = new XYChart.Series<>(Main.currentAccount.getData());
        getData().add(series);
        series.setName("Mein Konto");
    }
}
