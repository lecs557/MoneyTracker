package model.threads;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.FontFilter;
import model.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFLoader extends Thread {

    private List<File> files;
    private SimpleDoubleProperty progress = new SimpleDoubleProperty();
    private SimpleBooleanProperty running = new SimpleBooleanProperty();
    private PdfReader pdfReader;

    public PDFLoader(List<File> files) {
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
                    pdfReader = new PdfReader(path);
                    RenderFilter info = new FontFilter();
                    TextExtractionStrategy strategy = new FilteredTextRenderListener(
                            new LocationTextExtractionStrategy(), info);

                    for (int i=1;i<pdfReader.getNumberOfPages();i++){
                        String content = PdfTextExtractor.getTextFromPage(pdfReader, i,	strategy);
                    }
                } catch (IOException e) {
                    System.out.println("Problem bei: "+path);
                    e.printStackTrace();
                }
            }
            p++;
            progress.set((double)p/size);
        }
        running.set(false);
        Main.currentAccount.reload();
    }

    public SimpleDoubleProperty progressProperty() {
        return progress;
    }

    public SimpleBooleanProperty runningProperty() {
        return running;
    }
}
