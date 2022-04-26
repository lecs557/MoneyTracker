package view.panes.elements;

import model.storeclasses.BankAccount;

public class BankAccountList extends StoreClassList<BankAccount> {

    public BankAccountList() {
        super();
        this.getStyleClass().add("bank");
    }

    @Override
    public BankAccount getDummy() {
        return new BankAccount();
    }
}
