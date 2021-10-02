package model.threads;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import controller.DatabaseController;
import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.TransactionExtractor;
import model.storeclasses.InvoiceFile;
import model.storeclasses.Transaction;
import view.simple_panes.StoreClassTable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFImporter extends Thread {

    private List<File> files;
    private final SimpleDoubleProperty progress = new SimpleDoubleProperty();
    private final SimpleBooleanProperty running = new SimpleBooleanProperty();
    private PdfReader pdfReader;

    public PDFImporter(List<File> files) {
        this.files = files;
    }

    @Override
    public void run() {
        running.set(true);
        int p=0;
        int size = files.size();
        for(File file:files) {
            if (file != null) {
                String path = file.getAbsolutePath();
                try {
                    InvoiceFile infile = new InvoiceFile();
                    infile.setPath(path);
                    DatabaseController.getInstance().storeObject(infile, true);
                    pdfReader = new PdfReader(path);
                    RenderFilter info = new TransactionExtractor(infile.getId()+"");
                    TextExtractionStrategy strategy = new FilteredTextRenderListener(
                            new LocationTextExtractionStrategy(), info);

                    for (int i=1;i<pdfReader.getNumberOfPages();i++){
                        String content = PdfTextExtractor.getTextFromPage(pdfReader, i,	strategy);
                    }
                    pdfReader.close();

                } catch (IOException e) {
                    pdfReader.close();
                    System.out.println("Problem bei: "+path);
                    e.printStackTrace();
                }
            }
            p++;
            progress.set((double)p/size);
        }
        running.set(false);
        ViewController.getInstance().refresh(new Transaction());
        Platform.runLater(() -> WindowManager.getInstance().openStageOf(new StoreClassTable(ProfileAccountManager.getInstance().getImportedTransactions(),new Transaction())));
    }

    public SimpleDoubleProperty progressProperty() {
        return progress;
    }

    public SimpleBooleanProperty runningProperty() {
        return running;
    }
}
