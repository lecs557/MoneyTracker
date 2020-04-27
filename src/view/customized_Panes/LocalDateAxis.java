package view.customized_Panes;

import javafx.scene.chart.Axis;
import javafx.scene.chart.ValueAxis;
import model.MyDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocalDateAxis extends ValueAxis<MyDate> {

    @Override
    protected List<MyDate> calculateMinorTickMarks() {
        ArrayList<MyDate> myDates = new ArrayList<>();
        myDates.add(new MyDate(LocalDate.of(2020,1,15)));
        myDates.add(new MyDate(LocalDate.of(2020,2,15)));
        myDates.add(new MyDate(LocalDate.of(2020,3,15)));
        myDates.add(new MyDate(LocalDate.of(2020,4,15)));
        return myDates;

    }

    @Override
    protected void setRange(Object o, boolean b) {
        getRange();
    }

    @Override
    protected Object getRange() {
        return autoRange(0,300,8,10);
    }

    @Override
    protected List<MyDate> calculateTickValues(double v, Object o) {
        ArrayList<MyDate> myDates = new ArrayList<>();
        myDates.add(new MyDate(LocalDate.of(2020,1,1)));
        myDates.add(new MyDate(LocalDate.of(2020,2,1)));
        myDates.add(new MyDate(LocalDate.of(2020,3,1)));
        myDates.add(new MyDate(LocalDate.of(2020,4,1)));
        return myDates;
    }

    @Override
    protected String getTickMarkLabel(MyDate myDate) {
        return myDate.getDate().toString();
    }
}
