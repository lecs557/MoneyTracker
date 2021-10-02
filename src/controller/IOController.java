package controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.storeclasses.BankAccount;
import model.threads.Loader;
import model.threads.PDFImporter;
import model.threads.Saver;

import java.io.File;
import java.util.List;

public class IOController {

    private static IOController singleton;

    private IOController(){}

    public static void initialize(){
        singleton = new IOController();
    }

    public static IOController getInstance(){
        if (singleton == null) {
            initialize();
        }
        return singleton;
    }


    private PDFImporter pdfLoader;

    private final SimpleDoubleProperty pdfProgress = new SimpleDoubleProperty();
    private final SimpleBooleanProperty pdfRunning = new SimpleBooleanProperty();

    public void startPDFImport(List<File> files) {
        if (files == null) return;
        pdfLoader = new PDFImporter(files);
        pdfLoader.start();
        pdfRunning.unbind();
        pdfProgress.unbind();
        pdfRunning.bind(pdfLoader.runningProperty());
        pdfProgress.bind(pdfLoader.progressProperty());
    }

    public PDFImporter getPdfLoader() {
        return pdfLoader;
    }

    public SimpleDoubleProperty pdfPogressProperty() {
        return pdfProgress;
    }

    public SimpleBooleanProperty pdfRunningProperty() {
        return pdfRunning;
    }
}

