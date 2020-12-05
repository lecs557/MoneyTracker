package view.panes;

import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DateEntryWindowCtrl {

    public TextField tf_day;
    public TextField tf_month;
    public TextField tf_year;

    public void initialize() {
        LocalDate now = LocalDate.now();
        tf_day.setText(now.getDayOfMonth() + "");
        tf_month.setText(now.getMonthValue() + "");
        tf_year.setText(now.getYear() + "");

        tf_day.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("(0|[012]?[0-9]|3[01])")) {
                tf_day.setText(oldValue);
            } else if (newValue.length() == 2) {
                tf_month.requestFocus();
            }
        });

        tf_month.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("(0?[1-9]|1[012]|0)")) {
                tf_month.setText(oldValue);
            } else if (newValue.length() == 2) {
                tf_year.requestFocus();
            }
        });

        tf_year.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                tf_year.setText(oldValue);
            }
        });
    }
}
