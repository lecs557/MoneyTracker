package view.windows;

import javafx.scene.layout.Pane;
import model.Main;
import view.panes.EnterTransactionCtrl;
import view.panes.MyNode;

public class NewTransactionCtrl {

   private EnterTransactionCtrl c;
   public Pane enterTransactionContainer;

    public void initialize() {
         //c = new MyNode("EnterTransaction").putInto(enterTransactionContainer).getController();
    }

    public void ok(){
//        Main.currentAccount.addTransaction(c.getTransaction());
//        Main.secStage.close();
//        Main.currentAccount.reload();
    }

    public void close(){
        Main.secStage.close();
    }
}
