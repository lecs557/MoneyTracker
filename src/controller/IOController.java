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
    
    private static PDFImporter pdfLoader;

    private static final SimpleDoubleProperty progress = new SimpleDoubleProperty();
    private static final SimpleBooleanProperty running = new SimpleBooleanProperty();

    public static void startPDFImport(List<File> files) {
        if (files == null) return;
        pdfLoader = new PDFImporter(files);
        pdfLoader.start();
        running.unbind();
        progress.unbind();
        running.bind(pdfLoader.runningProperty());
        progress.bind(pdfLoader.progressProperty());
    }

    public static PDFImporter getPdfLoader() {
        return pdfLoader;
    }

    public static double getProgress() {
        return progress.get();
    }

    public static SimpleDoubleProperty progressProperty() {
        return progress;
    }

    public static boolean isRunning() {
        return running.get();
    }

    public static SimpleBooleanProperty runningProperty() {
        return running;
    }
}

