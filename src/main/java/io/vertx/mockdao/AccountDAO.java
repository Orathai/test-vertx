package io.vertx.mockdao;

import io.vertx.bean.Account;
import io.vertx.bean.Balance;
import io.vertx.bean.Currency;
import io.vertx.bean.Owner;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    //private OwnerDAO ownerDAO = new OwnerDAO();
    private static final List<Account> ACCOUNT_LIST = new ArrayList<>();

    public List<Account> initData() {
        Balance balance = new Balance(Currency.EURO, 10000);
        Owner owner = new Owner("mai", "https://psd2-api.openbankproject.com", "mai");

        //create mock 5 accounts
        for (int i = 0; i <= 4; i++) {
            String accountId = "BANKBRIDGE123" + i;

            Account account = new Account(
                    accountId,
                    null,
                    "6625231013",
                    null,
                    null,
                    null,
                    "psd201-bank-x--uk",
                    balance, owner);
            ACCOUNT_LIST.add(account);

        }
        return ACCOUNT_LIST;

    }

    public Account findByAccountId(String id) {

        Account result = new Account();

        for (Account account : ACCOUNT_LIST) {
            if (account.getId().equals(id)) {
                result = account;

            }
        }
        return result;
    }

    public List<Account> findAllAccount() {
        return ACCOUNT_LIST;
    }

    public int updateByAccountId(Account account) {
        System.out.println("...reading updateByAccountId");

        for (Account oldAccount : ACCOUNT_LIST) {

            if (oldAccount.getId().equals(account.getId())) {

                oldAccount.setBank_id(account.getBank_id());
                oldAccount.setIBAN(account.getIBAN());
                oldAccount.setLabel(account.getLabel());
                oldAccount.setNumber(account.getNumber());
                oldAccount.setType(account.getType());
                oldAccount.setSWIFT(account.getSWIFT());
                oldAccount.setOwner(account.getOwner());
                oldAccount.setBalance(account.getBalance());

                int index = ACCOUNT_LIST.indexOf(oldAccount);
                ACCOUNT_LIST.set(index, account);

                return 1;
            }

        }
        return 0;
    }

    public int addNewAccount(Account account) {

        if (!isAccountExist(account.getId())) {
            ACCOUNT_LIST.add(account);
            return 1;
        }
        return 0;
    }

    public int deleteByAccountId(String id) {

        for (Account account : ACCOUNT_LIST) {
            if (account.getId().equals(id)) {
                int index = ACCOUNT_LIST.indexOf(account);
                ACCOUNT_LIST.remove(index);
                return 1;
            }
        }
        return 0;
    }

    private boolean isAccountExist(String id) {

        for (Account account : ACCOUNT_LIST) {
            if (account.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /*public Account addNewAccount(String id,
                                 String label,
                                 String number,
                                 String type,
                                 String IBAN,
                                 String SWIFT,
                                 String bank_id,
                                 Balance balance,
                                 Owner owner){


        return new Account(id, label, number, type, IBAN, SWIFT, bank_id, balance, owner);
    }*/

}
