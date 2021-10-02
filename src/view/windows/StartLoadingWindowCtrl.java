package view.windows;
import controller.WindowManager;
import javafx.scene.control.ProgressBar;
import model.AppStart;


public class StartLoadingWindowCtrl {

    public ProgressBar pb_load;

    public void initialize() {
        pb_load.progressProperty().bind(AppStart.loader.progressProperty());
    }
}
