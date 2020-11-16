package model;

import java.util.HashMap;

public class Sum {

    private int number;
    private int dayOfMonth;
    private String reason;
    private int cents;
    private int allSum;
    private HashMap<Integer,Integer> sum;

    public Sum(Transaction transaction) {
        dayOfMonth = transaction.getDate().getDayOfMonth();
        reason = transaction.getPurpose();
        cents = transaction.getAmount();
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
        allSum+=transaction.getAmount();
        sum.put(year,prev+transaction.getAmount());
    }

    public void removeFromSum(Transaction transaction){
        int year = transaction.getDate().getYear();
        int prev=0;
        if(sum.containsKey(year)) {
            prev = sum.get(year);
            sum.remove(year);
        }
        number--;
        allSum-=transaction.getBalance();
        sum.put(year,prev - transaction.getAmount());
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
