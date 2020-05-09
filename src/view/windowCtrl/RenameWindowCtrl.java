package view.windowCtrl;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Main;
import model.Sum;
import model.Transaction;

import java.util.ArrayList;


public class RenameWindowCtrl {

    public VBox vb_reasons;
    public ArrayList<HBox> replacements = new ArrayList<>();


    public void initialize() {
        for (Sum s: Main.currentAccount.getSums()){
            HBox h = new HBox();
            Label l = new Label(s.getReason());
            TextField tf = new TextField();
            h.getChildren().addAll(l,tf);
            vb_reasons.getChildren().add(h);
        }
    }

    public void ok() {
        for(Node h: vb_reasons.getChildren() ){
            if(  !((TextField)((HBox)h).getChildren().get(1)).getText().isEmpty() ){
                replacements.add( (HBox)h);
                System.out.println(h);
            }
        }

        for(Sum s: Main.currentAccount.getSums()){
            for(HBox h: replacements){
                if(s.getReason().equals(   ((Label)h.getChildren().get(0)).getText()      )    ){
                    s.setReason( ((TextField)h.getChildren().get(1)).getText()   );
                }
            }
        }



        for (ObservableList<Transaction> yt:Main.currentAccount.getYears_Transaction()){
            for(Transaction t: yt){
                for(HBox h: replacements){
                    if(t.getReason().equals(   ((Label)h.getChildren().get(0)).getText()      )    ){
                        t.setReason( ((TextField)h.getChildren().get(1)).getText()   );
                    }
                }
            }
        }
    }
}
