package view.windows;

import javafx.scene.control.TextField;
import model.Main;
import model.Transaction;

import java.time.LocalDate;

public class NewTransactionCtrl {

    public TextField tb_day;
    public TextField tb_month;
    public TextField tb_year;
    public TextField tb_reason;
    public TextField tb_money;
    public TextField tb_cents;

    public void initialize() {
        LocalDate now = LocalDate.now();
        tb_day.setText(now.getDayOfMonth()+"");
        tb_month.setText(now.getMonthValue()+"");
        tb_year.setText(now.getYear()+"");
        tb_cents.setText("00");

        tb_day.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("(0|[012]?[0-9]|3[01])")) {
                tb_day.setText(oldValue);
            }
            else if(newValue.length()==2){
                tb_month.requestFocus();
            }
        });

        tb_month.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("(0?[1-9]|1[012]|0)")) {
                tb_month.setText(oldValue);
            }
            else if(newValue.length()==2){
                tb_year.requestFocus();
            }
        });

        tb_year.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                tb_year.setText(oldValue);
            }
            else if(newValue.length()==4){
                tb_reason.requestFocus();
            }
        });


        tb_money.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("(-?[0-9]*)")) {
                tb_money.setText(oldValue);
            }
        });


        tb_cents.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9][0-9]?")) {
                tb_cents.setText(oldValue);
            }
        });

    }

    public void ok(){
        try {
            int teYear = Integer.parseInt(tb_year.getText());
            int teMonth =Integer.parseInt(tb_month.getText());
            int teday = Integer.parseInt(tb_day.getText());
            LocalDate temp = LocalDate.of(teYear,teMonth,teday);
            String teReason = tb_reason.getText();
            int tebetrag = Integer.parseInt(tb_money.getText()+tb_cents.getText());
            Main.currentAccount.addTransaction(new Transaction(temp,teReason,tebetrag));
            Main.newStage.close();
            Main.currentAccount.reload();
        } catch (Exception e) {
            e.printStackTrace();
            tb_day.requestFocus();
        }
    }

    public void close(){
        Main.newStage.close();
    }
}
