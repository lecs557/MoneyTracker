package view.simple_panes;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import model.MyDate;
import model.storeclasses.Transaction;

import java.util.ArrayList;

public class TransactionChart extends LineChart<MyDate,Number> {

    private  Series<MyDate,Number> series;

    public TransactionChart() {
        super(new LocalDateAxis(), new NumberAxis());
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
        int endYear = transactions.get(transactions.size()-1).getLocalDate().getYear();;
        for (Transaction t:transactions){
            Data<MyDate,Number> data = new Data<>(new MyDate(t.getLocalDate()),(double)t.getBalance()/100);
            series.getData().add(data);
        }
        ((LocalDateAxis)getXAxis()).setYears(startYear,endYear);
    }
}
