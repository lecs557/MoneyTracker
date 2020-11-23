package controller;

import javafx.application.Platform;
import view.windows.StartWindowCtrl;

public class ViewController {

    private StartWindowCtrl startWindowCtrl;

    public void showError(String string){
        if (startWindowCtrl != null) {
            Platform.runLater(() -> startWindowCtrl.lbl_error.setText(string));
        }
    }

    public void setStartWindowCtrl(StartWindowCtrl startWindowCtrl) {
        this.startWindowCtrl = startWindowCtrl;
    }
}
