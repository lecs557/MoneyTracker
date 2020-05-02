package model;

import java.util.HashMap;
import java.util.function.Function;

public class Sum {

    private int dayOfMonth;
    private String reason;
    private int cents;
    private int allSum;
    private HashMap<Integer,Integer> sum;

    public Sum(Transaction transaction) {
        dayOfMonth = transaction.getDate().getDayOfMonth();
        reason = transaction.getReason();
        cents = transaction.getBetrag();
        allSum=0;
        sum = new HashMap<>();
    }

    public void addToSum(Transaction transaction) {
        int year = transaction.getDate().getYear();
        int prev=0;
        if(sum.containsKey(year)) {
            prev = sum.get(year);
            sum.remove(year);
        }
        allSum+=transaction.getBetrag();
        sum.put(year,prev+transaction.getBetrag());
    }

    public void removeFromSum(Transaction transaction){
        int year = transaction.getDate().getYear();
        int prev=0;
        if(sum.containsKey(year)) {
            prev = sum.get(year);
            sum.remove(year);
        }
        allSum-=transaction.getKonto();
        sum.put(year,prev - transaction.getBetrag());
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getReason() {
        return reason;
    }

    public int getCents() {
        return cents;
    }

    public HashMap<Integer, Integer> getSum() {
        return sum;
    }

    public int getAllSum() {
        return allSum;
    }
}
