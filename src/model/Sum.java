package model;

import java.util.HashMap;
import java.util.function.Function;

public class Sum {

    private int number;
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
        number=0;
        sum = new HashMap<>();
    }

    public void mergesum(Sum sumtem){
        for( int key:sumtem.getSum().keySet()){
            sum.put(key ,sum.getOrDefault(key,0)+sumtem.getSum().get(key));
        }
        number+=sumtem.getNumber();
    }

    public void addToSum(Transaction transaction) {
        int year = transaction.getDate().getYear();
        int prev=0;
        if(sum.containsKey(year)) {
            prev = sum.get(year);
            sum.remove(year);
        }
        number++;
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
        number--;
        allSum-=transaction.getKonto();
        sum.put(year,prev - transaction.getBetrag());
    }

    public String getReason() {
        return reason;
    }

    public HashMap<Integer, Integer> getSum() {
        return sum;
    }

    public int getAllSum() {
        return allSum;
    }

    public int getNumber() {
        return number;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
