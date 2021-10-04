package model;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import controller.DatabaseController;
import controller.ProfileAccountManager;
import model.storeclasses.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionExtractor extends RenderFilter {

	private DateTimeFormatter form = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private boolean relevant = false;
	private float transacY=0;
	private String purpose ="";
	private String amount = "0";
	private String fileID = "0";
	private LocalDate date;

	private boolean ignoreNext = false;

    public TransactionExtractor(String fileID) {
        this.fileID = fileID;
    }

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
			if(!purpose.isEmpty()) {
                Transaction tran = new Transaction();
                System.out.println("NEUE TRANSACTION vom: "+date.toString());
                tran.setLocalDate(date);
                System.out.println(amount);
                tran.setAmount(amount);
                System.out.println(purpose);
                tran.setPurpose(purpose);
                tran.addInvoiceFieldId(fileID);
                if(DatabaseController.getInstance().storeObject(tran,true)){
                    ProfileAccountManager.getInstance().addNewTransactionToImport(tran);
                }
                System.out.println("----------------");
                ignoreNext=false;
                purpose ="";
                amount ="0";
			}
		}


		if (relevant) {
            try {
                if( (68 < x && x < 72) && ( transacY-y < 0 ||  14 < transacY-y )){
                    if(!purpose.isEmpty()) {
                        Transaction tran = new Transaction();
                        System.out.println("NEUE TRANSACTION vom: "+date.toString());
                        tran.setLocalDate(date);
                        System.out.println(amount);
                        tran.setAmount(amount);
                        System.out.println(purpose);
                        tran.setPurpose(purpose);
                        DatabaseController.getInstance().storeObject(tran,true);
                        System.out.println("----------------");
                        ignoreNext=false;
                        purpose ="";
                        amount ="0";
                    }
                    transacY = y;
                    date = LocalDate.parse(tri.getText(), form);
                } else {
                    if(140<x && x<450 && !ignoreNext){
                        purpose +=tri.getText()+" ";
                        if (tri.getText().contains("BAFOEG") || tri.getText().contains("Ticketerstattung")){
                            ignoreNext=true;
                        }
                    }
                    if(x>500){
                        amount = tri.getText().replace(",","").replace(".","");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("FEHLER bei:");
                System.out.println("NEUE TRANSACTION vom: "+date.toString());
                System.out.println(amount);
                System.out.println(purpose);
                e.printStackTrace();
            }
        }

		if(tri.getText().contains("Valuta") && (68 < x && x < 72) ){
			relevant=true;
		}
	}
}
