package view.simple_panes;

import model.storeclasses.BankAccount;
import model.storeclasses.Group;

public class BankAccountList extends StoreClassList<BankAccount> {

    @Override
    public BankAccount getDummy() {
        return new BankAccount();
    }
}
