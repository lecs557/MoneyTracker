package view.simple_panes;

import javafx.scene.chart.ValueAxis;
import model.Main;
import model.MyDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LocalDateAxis extends ValueAxis<MyDate> {

    int start = 2020;
    int end = 2021;

    public LocalDateAxis() {
    }

    public LocalDateAxis(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
        setLowerBound(start());
        setUpperBound(end());
        setMinorTickVisible(true);

    }

    @Override
    protected List<MyDate> calculateMinorTickMarks() {
        ArrayList<MyDate> myDates = new ArrayList<>();
        for(int i=0;i<(end-start)*12;i++){
            myDates.add(new MyDate(LocalDate.of(start+i/12,(i%12)+1,1)));
        }
        return myDates;

    }

    @Override
    protected void setRange(Object o, boolean b) {
        getRange();
    }

    @Override
    protected Object getRange() {
        return null;
    }

    @Override
    protected List<MyDate> calculateTickValues(double v, Object o) {
        ArrayList<MyDate> myDates = new ArrayList<>();
        for(int i=0;i<(end-start)*12;i++){
            myDates.add(new MyDate(LocalDate.of(start+i/4,((i%4)*3)+1,1)));
        }
        return myDates;
    }

    @Override
    protected String getTickMarkLabel(MyDate myDate) {
        return myDate.getDate().format(DateTimeFormatter.ofPattern("LLL yyyy"));
    }

    private int start(){
        if(!Main.currentAccount.getYears_Transaction().isEmpty()){
            start=Main.currentAccount.getYears_Transaction().get(0).get(0).getDate().getYear();
            return new MyDate(LocalDate.of(start,1,1)).intValue();
        }
        return 0;
    }

    private int end() {
        if (!Main.currentAccount.getYears_Transaction().isEmpty()) {
            int size = Main.currentAccount.getYears_Transaction().size();
            end = Main.currentAccount.getYears_Transaction().get(size - 1).get(0).getDate().getYear();
            return new MyDate(LocalDate.of(end, 12, 31)).intValue();
        }
        return 365;
    }
}
