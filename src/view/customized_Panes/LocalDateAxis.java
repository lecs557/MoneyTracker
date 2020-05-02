package view.customized_Panes;

import javafx.scene.chart.Axis;
import javafx.scene.chart.ValueAxis;
import model.MyDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocalDateAxis extends ValueAxis<MyDate> {

    public LocalDateAxis() {
    }

    public LocalDateAxis(double lowerBound, double upperBound) {
        super(lowerBound, upperBound);
    }

    @Override
    protected List<MyDate> calculateMinorTickMarks() {
        ArrayList<MyDate> myDates = new ArrayList<>();
        for(int i=0;i<24;i++){
            myDates.add(new MyDate(LocalDate.of(2018+i/12,(i%11)+1,1)));
        }
        return myDates;

    }

    @Override
    protected void setRange(Object o, boolean b) {
        getRange();
    }

    @Override
    protected Object getRange() {
        return autoRange(0,6,60,10);
    }

    @Override
    protected List<MyDate> calculateTickValues(double v, Object o) {
        ArrayList<MyDate> myDates = new ArrayList<>();
        for(int i=0;i<13;i++){
            myDates.add(new MyDate(LocalDate.of(2018+i/4,((i%4)*3)+1,1)));
        }
        return myDates;
    }

    @Override
    protected String getTickMarkLabel(MyDate myDate) {
        return myDate.getDate().toString();
    }
}
