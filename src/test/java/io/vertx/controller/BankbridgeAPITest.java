package io.vertx.controller;

import io.vertx.bean.Account;
import io.vertx.bean.Currency;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.mockdao.AccountDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(VertxUnitRunner.class)
public class BankbridgeAPITest {

    private AccountDAO accountDAO = new AccountDAO();
    private BankbridgeAPI bankbridgeAPI;
    private Vertx vertx;

    @Before
    public void setUp(TestContext context) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(BankbridgeAPI.class.getName(),
                context.asyncAssertSuccess());

    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testGetByAccountId(TestContext context) {
        final Async async = context.async();

        accountDAO.initData();
        Account account = accountDAO.findByAccountId("BANKBRIDGE1230");
        final String json = Json.encodePrettily(account);

        vertx.createHttpClient().getNow(8082, "localhost", "/accounts/" + account.getId(),
                response -> {
                    response.handler(body -> {
                        context.assertEquals(response.statusCode(), 200);
                        context.assertTrue(response.getHeader("content-type").contains("application/json"));
                        context.assertEquals(account.getId(), "BANKBRIDGE1230");
                        context.assertEquals(account.getBank_id(), "psd201-bank-x--uk");
                        context.assertEquals(account.getLabel(), null);
                        context.assertEquals(account.getNumber(), "6625231013");
                        context.assertEquals(account.getIBAN(), null);
                        context.assertEquals(account.getType(), null);
                        context.assertEquals(account.getSWIFT(), null);
                        context.assertEquals(account.getBalance().getAmount(), 10000.00);
                        context.assertEquals(account.getBalance().getCurrency(), Currency.EURO);

                        context.assertEquals(account.getOwner().getId(), "mai");
                        context.assertEquals(account.getOwner().getDisplay_name(), "mai");
                        context.assertEquals(account.getOwner().getProvider(),
                                "https://psd2-api.openbankproject.com");

                        async.complete();
                    });
                });


    }

    @Test
    public void testGetAllAccounts(TestContext context) {

        final Async async = context.async();
        List<Account> accountList = accountDAO.initData();
        String json = Json.encodePrettily(accountList);


        vertx.createHttpClient().getNow(8082, "localhost", "/accounts", response -> {
            response.handler(body -> {
                context.assertEquals(response.statusCode(), 200);
                context.assertTrue(response.getHeader("content-type").contains("application/json"));
                //TODO: check if body.toString() is equal to json value
                //context.assertEquals(json, body.toString());

                async.complete();
            });
        });

    }

    @Test
    public void testDeleteById(TestContext context) {

        final Async async = context.async();

        accountDAO.initData();
        Account account = accountDAO.findByAccountId("BANKBRIDGE1230");
        accountDAO.deleteByAccountId(account.getId());

        vertx.createHttpClient().getNow(8082, "localhost", "/accounts/" + account.getId(), response -> {
            response.handler(body -> {
                //TODO: response.statusCode() should return 204
                //context.assertEquals(response.statusCode(), 204);
                //context.assertEquals(response.getHeader("Content-Length"), 0);
                //context.assertNull(body.toString());

                async.complete();
            });
        });

    }

}