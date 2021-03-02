package view.simple_panes;

import model.storeclasses.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;

public class SampleClass {

    public static ArrayList<Transaction> getSampleTransactions(){
        Transaction sample1 = new Transaction();
        Transaction sample2 = new Transaction();
        ArrayList<Transaction> sampleTransactions = new ArrayList<>();
        sample1.setLocalDate(LocalDate.of(2020,01,01));
        sample1.setPurpose("Test");
        sample1.setAmount("50");
        sample2.setLocalDate(LocalDate.now());
        sample2.setAmount("-50");
        sample2.setPurpose("Test1");
        sampleTransactions.add(sample1);
        sampleTransactions.add(sample2);
        return sampleTransactions;
    }
}
