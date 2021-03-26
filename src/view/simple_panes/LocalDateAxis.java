package view.simple_panes;

import javafx.scene.chart.ValueAxis;
import model.Main;
import model.MyDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LocalDateAxis extends ValueAxis<MyDate> {

    private int start = 2020;
    private int end = 2021;

    public LocalDateAxis() {
    }

    public void setYears(int lowerYear, int upperYear){
        start=lowerYear;
        end=upperYear;
        setLowerBound(new MyDate(LocalDate.of(lowerYear, 1, 1)).intValue());
        setUpperBound(new MyDate(LocalDate.of(upperYear, 12, 31)).intValue());
        calculateMinorTickMarks();
    }

    @Override
    protected List<MyDate> calculateMinorTickMarks() {
        ArrayList<MyDate> myDates = new ArrayList<>();
        for(int i=0;i<(end-start+1)*12;i++){
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
}
