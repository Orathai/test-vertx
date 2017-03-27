package io.vertx.mockdao;

import io.vertx.bean.Account;
import io.vertx.bean.Balance;
import io.vertx.bean.Currency;
import io.vertx.bean.Owner;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDAO {
    private OwnerDAO ownerDAO = new OwnerDAO();

    public Account getAccountById(String id) {

        Balance balance = new Balance(Currency.EURO, 10000);
        Owner owner = new Owner("mai", "https://psd2-api.openbankproject.com", "mai");

        Account account = new Account(
                id,
                null,
                "6625231013",
                null,
                null,
                null,
                "psd201-bank-x--uk",
                balance, owner);

        return account;
    }

    public List<Account> getAllAccount() {
        Balance balance = new Balance(Currency.EURO, 10000);
        Owner owner = new Owner("mai", "https://psd2-api.openbankproject.com", "mai");
        //create owner
        ArrayList<Account> accountList = new ArrayList<>();



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
            accountList.add(account);

        }
        return accountList;
    }

}
