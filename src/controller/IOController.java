package controller;

import javafx.beans.property.SimpleBooleanProperty;
import model.threads.Loader;
import model.threads.PDFImporter;
import model.threads.Saver;

import java.io.File;
import java.util.List;

public class IOController {

    private Loader loader;
    private SimpleBooleanProperty loadRunning = new SimpleBooleanProperty();
    private Saver saver;
    private SimpleBooleanProperty saveRunning = new SimpleBooleanProperty();
    private PDFImporter pdfLoader;
    private SimpleBooleanProperty pdfLoadRunning = new SimpleBooleanProperty();


    public void startSave()  {
       saver = new Saver();
       saver.start();
       saveRunning.unbind();
       saveRunning.bind(saver.runningProperty());
    }

    public void startLoad(String name, String path) {
        loader =new Loader(name, path);
        loader.start();
        loadRunning.unbind();
        loadRunning.bind(loader.isRunningProperty());
    }

    public void startPDFImport(List<File> files){
        pdfLoader = new PDFImporter(files);
        pdfLoader.start();
        pdfLoadRunning.unbind();
        pdfLoadRunning.bind(pdfLoader.runningProperty());
    }

    public Saver getSaver() {
        return saver;
    }

    public PDFImporter getPdfLoader() {
        return pdfLoader;
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

