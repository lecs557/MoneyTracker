package controller;

import javafx.beans.property.SimpleBooleanProperty;
import model.threads.Loader;
import model.threads.PDFLoader;
import model.threads.Saver;

import java.io.File;
import java.util.List;

public class IOController {

    private Loader load;
    private SimpleBooleanProperty loadRunning = new SimpleBooleanProperty();
    private Saver save;
    private SimpleBooleanProperty saveRunning = new SimpleBooleanProperty();
    private PDFLoader pdfLoad;
    private SimpleBooleanProperty pdfLoadRunning = new SimpleBooleanProperty();


    public void save()  {
       save = new Saver();
       save.start();
       saveRunning.unbind();
       saveRunning.bind(save.runningProperty());
    }

    public void load(String name, String path) {
        load =new Loader(name, path);
        load.start();
        loadRunning.unbind();
        loadRunning.bind(load.isRunningProperty());
    }

    public void pdfLoad(List<File> files){
        pdfLoad = new PDFLoader(files);
        pdfLoad.start();
        pdfLoadRunning.unbind();
        pdfLoadRunning.bind(pdfLoad.runningProperty());
    }

    public Loader getLoad() {
        return load;
    }

    public Saver getSave() {
        return save;
    }

    public PDFLoader getPdfLoad() {
        return pdfLoad;
    }

    public SimpleBooleanProperty loadRunningProperty() {
        return loadRunning;
    }

    public SimpleBooleanProperty saveRunningProperty() {
        return saveRunning;
    }

    public SimpleBooleanProperty pdfLoadRunningProperty() {
        return pdfLoadRunning;
    }
}

