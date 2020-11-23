package model;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FontFilter extends RenderFilter {

	private DateTimeFormatter form = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private boolean relevant = false;
	private float transacY=0;
	private String zweck="";
	private int betrag = 0;
	private LocalDate date;
	private boolean ignoreNext = false;
	
	@Override
	public boolean allowText(TextRenderInfo tri) {
		addTransactions(tri);
		return true;
	}

	private void addTransactions(TextRenderInfo tri) {
		float y = tri.getBaseline().getStartPoint().get(1);
		float x = tri.getBaseline().getStartPoint().get(0);


		if(tri.getText().contains("Saldo") || x<60){
			relevant=false;
			if(!zweck.isEmpty()) {
				System.out.println("NEUE TRANSACTION vom: "+date.toString());
				System.out.println(betrag);
				System.out.println(zweck);
				System.out.println("----------------");
				zweck="";
			}
		}


		if (relevant) {
            try {
                if( (68 < x && x < 72) && ( transacY-y < 0 ||  14 < transacY-y )){
                    if(!zweck.isEmpty()) {
                        System.out.println("NEUE TRANSACTION vom: "+date.toString());
                        System.out.println(betrag);
                        System.out.println(zweck);
                        System.out.println("----------------");
                        ignoreNext=false;
                        zweck="";
                        betrag=0;
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
                        betrag = Integer.parseInt(tri.getText().replace(",","").replace(".",""));
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("FEHLER bei:");
                System.out.println("NEUE TRANSACTION vom: "+date.toString());
                System.out.println(betrag);
                System.out.println(zweck);
                e.printStackTrace();
            }
        }


		if(tri.getText().contains("Valuta") && (68 < x && x < 72) ){
			relevant=true;
		}
	}
}
