package controller;

import javafx.beans.property.SimpleBooleanProperty;
import model.storeclasses.BankAccount;
import model.threads.Loader;
import model.threads.PDFImporter;
import model.threads.Saver;

import java.io.File;
import java.util.List;

public class IOController {
    
    private static PDFImporter pdfLoader;
    private static SimpleBooleanProperty pdfLoadRunning = new SimpleBooleanProperty();

    public static void startPDFImport(List<File> files){
        pdfLoader = new PDFImporter(files);
        pdfLoader.start();
        pdfLoadRunning.unbind();
        pdfLoadRunning.bind(pdfLoader.runningProperty());
    }


    public PDFImporter getPdfLoader() {
        return pdfLoader;
    }

    public SimpleBooleanProperty pdfLoadRunningProperty() {
        return pdfLoadRunning;
    }
}

