package view.windows;

import javafx.scene.layout.Pane;
import model.Main;
import view.panes.StringEntryWindowCtrl;

public class NewTransactionCtrl {

   private StringEntryWindowCtrl c;
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
