package io.vertx.mockdao;

import io.vertx.bean.Account;
import io.vertx.bean.Currency;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDAO {

    private Map<String, JsonObject> accounts = new HashMap<>();

    public List<Account> getAllAccountsNg(){
        return null;
    }

    public Account getAccountById( String id ) {
        return new Account(id, "dummy", "dummy", "dummy", "dummy", "dummy", "dummy", null,null);
    }

    public JsonObject getAllAccounts(){

        JsonObject balanceObj = new JsonObject().put("currency", Currency.NOK).put("amount", 25000.00);
        JsonObject ownerObj = new JsonObject().put("id", "mai").put("provider", "https://psd2-api.openbankproject.com")
                .put("display_name", "mai");



        JsonObject accountObj = new JsonObject();

        for (int i = 0; i <= 4; i++) {

            String accountId = "BANKBRIDGE123" + i;

            addAccount(new JsonObject().
                    put("id", accountId).
                    put("label", "").
                    put("number", "6625231013").
                    put("type", "").
                    put("IBAN", "NO93 8601 1117 948").
                    put("SWIFT", "").
                    put("bank_id", "psd201-bank-x--no").
                    put("balance", balanceObj).
                    put("owner", ownerObj));

        }
        return accountObj;
    }

    private void addAccount(JsonObject account) {
        accounts.put(account.getString("id"), account);
    }

}
