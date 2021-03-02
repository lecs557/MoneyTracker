package view.windows;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Main;
import model.Renames;


public class RenameWindowCtrl {

    public VBox vb_reasons;
 //   public ArrayList<Renames> renames = new ArrayList<>();
    public Renames rename;
    private TextField tf;
    private TextField tf1;


    public void initialize() {
      tf = new TextField();
      tf1 = new TextField();
      vb_reasons.getChildren().addAll(tf,tf1);
    }

    public void ok() {

    }
}
