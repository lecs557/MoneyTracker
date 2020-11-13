package view.simple_panes;

import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfReader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PDFViewer extends ScrollPane {

    private String path;

    public PDFViewer(String path) throws IOException {
        this.path = path;

    }

    public void putInto(Pane container) {
        setPrefHeight(container.getPrefHeight());
        setPrefWidth(container.getPrefWidth());
        container.getChildren().clear();
        container.getChildren().add(this);
    }
}
