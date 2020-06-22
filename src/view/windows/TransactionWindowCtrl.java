package view.windows;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Main;
import model.Renames;
import model.Transaction;
import view.panes.EnterTransactionCtrl;
import view.panes.MyNode;

import java.time.format.DateTimeFormatter;
import java.util.EventListener;

public class TransactionWindowCtrl {

    private EnterTransactionCtrl c;
    public Pane enterTransactionContainer;
    private EnterTransactionCtrl c1;
    public Pane enterTransactionContainer1;
    public RadioButton rb_see, rb_edit, rb_rename, rb_reason;

    public void initialize() {
        c = new MyNode("EnterTransaction").putInto(enterTransactionContainer).getController();
        c.readOnly();
        c1 = new MyNode("EnterTransaction").putInto(enterTransactionContainer1).getController();

        rb_see.setOnMouseClicked(mouseEvent -> {
            c.all();
            c1.all();
            enterTransactionContainer1.setVisible(false);
        });
        rb_edit.setOnMouseClicked(mouseEvent -> {
            enterTransactionContainer1.setVisible(true);
            c.all();
            c1.all();
        });

        rb_reason.setOnMouseClicked(mouseEvent -> {
            c.reasonOnly();
            c1.reasonOnly();
        });
        rb_rename.setOnMouseClicked(mouseEvent -> {
            c.reasonOnly();
            c1.reasonOnly();
            c.tb_reason.setEditable(true);
        });
    }

    public void ok(){
        Transaction edit = Main.editController.getEditTransaction();
        Transaction temp = c1.getTransaction();
        if (temp != null) {
            if(rb_reason.isSelected()){
               edit.setThis(temp);
            }else if(rb_see.isSelected()){
                Main.currentAccount.deleteTransaction(edit);
                Main.currentAccount.addTransaction(temp);
            } else if(rb_rename.isSelected()){
                Renames ren = new Renames(c.tb_reason.getText(), c1.tb_reason.getText());
                Main.editController.rename(ren);
            }
        }
        Main.newStage.close();
        Main.currentAccount.reload();

    }

    public void edit(){
        Transaction edit = Main.editController.getEditTransaction();
        Main.currentAccount.deleteTransaction(edit);
        Main.newStage.close();
        Main.currentAccount.reload();
    }
}

