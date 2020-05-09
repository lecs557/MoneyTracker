package controller;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import model.FontFilter;
import model.Main;
import model.Transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A handler which undertakes tasks which have to do the
 * selected PDF-File
 * @author Marcel
 */
public class PDFController {	
	private PdfReader pdfReader;
	private DateTimeFormatter form = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private boolean relevant = false;
	private float transacY=0;
	private String zweck="";
	private int betrag = 0;
	private LocalDate date;
	private boolean ignoreNext = false;
	/**
	 * Scans page of the PDF-File invoking "FontFilter" to get
	 * the TextRenderInfo which are given to the "FormatController"
	 * Can be invoked only for one page a time 
	 */
	public void readPDF(String path) throws IOException {

		pdfReader = new PdfReader(path);
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);

		for (int i=1;i<pdfReader.getNumberOfPages();i++){
			String content = PdfTextExtractor.getTextFromPage(pdfReader, i,	strategy);
		}


	}

	public void addTransactions(TextRenderInfo tri) {
		float y = tri.getBaseline().getStartPoint().get(1);
		float x = tri.getBaseline().getStartPoint().get(0);


		if(tri.getText().contains("Saldo") || x<60){
			relevant=false;
			if(!zweck.isEmpty()) {
				System.out.println("NEUE TRANSACTION vom: "+date.toString());
				System.out.println(betrag);
				System.out.println(zweck);
				System.out.println("----------------");
				Main.currentAccount.addTransaction(new Transaction(date,zweck,betrag));
				zweck="";
			}
		}


		if (relevant) {
			if( (68 < x && x < 72) && ( transacY-y < 0 ||  14 < transacY-y )){
				if(!zweck.isEmpty()) {
					System.out.println("NEUE TRANSACTION vom: "+date.toString());
					System.out.println(betrag);
					System.out.println(zweck);
					System.out.println("----------------");
					Main.currentAccount.addTransaction(new Transaction(date,zweck,betrag));
					ignoreNext=false;
					zweck="";
				}
				transacY = y;
				date = LocalDate.parse(tri.getText(), form);
			} else {
				if(140<x && x<450 && !ignoreNext){
					zweck+=tri.getText()+" ";
					if (tri.getText().contains("BAFOEG") || tri.getText().contains("Ticketerstattung")){
						ignoreNext=true;
					}
				}
				if(x>500){
					betrag = Integer.parseInt(tri.getText().replace(",",""));
				}
			}
		}


		if(tri.getText().contains("Valuta") && (68 < x && x < 72) ){
			relevant=true;
		}
	}

}
