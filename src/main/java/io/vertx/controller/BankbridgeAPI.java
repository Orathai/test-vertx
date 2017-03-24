package io.vertx.controller;

import io.vertx.bean.Account;
import io.vertx.bean.Currency;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mockdao.AccountDAO;

import java.util.HashMap;
import java.util.Map;

public class BankbridgeAPI extends AbstractVerticle {

    private AccountDAO accountDAO;

    private Map<String, JsonObject> accounts = new HashMap<>();

    @Override
    public void start() {

        setUpInitialData();

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/accounts/:id").handler(this::getByAccountId);
        router.get("/accounts").handler(this::getAllAccount);
        //router.post("/accounts").handler(this::addByAccountId);

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    //TODO:implementing POST, PUT, DELETE
    private void addByAccountId(RoutingContext routingContext) {

        String accountId = routingContext.request().getParam("id");
        HttpServerResponse response = routingContext.response();
        if (accountId == null) {
            sendError(400, response);
        } else {
            JsonObject account = routingContext.getBodyAsJson();
            if (account == null) {
                sendError(400, response);
            } else {
                accounts.put(accountId, account);
                response.end();
            }
        }
    }

    private void getByAccountId(RoutingContext routingContext) {

        String accountId = routingContext.request().getParam("id");
        HttpServerResponse response = routingContext.response();
        if (accountId == null) {
            sendError(400, response);
        } else {
            JsonObject account = accounts.get(accountId);
            if (account == null) {
                sendError(404, response);
            } else {
                response.putHeader("content-type", "application/json").end(account.encodePrettily());
            }
        }
    }

    private void setUpInitialData() {

        JsonObject balanceObj = new JsonObject().put("currency", Currency.NOK).put("amount", 25000.00);
        JsonObject ownerObj = new JsonObject().put("id", "mai").put("provider", "https://psd2-api.openbankproject.com")
                .put("display_name", "mai");


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
    }

    private void addAccount(JsonObject account) {
        accounts.put(account.getString("id"), account);
    }

    private void getAllAccount(RoutingContext routingContext) {

        JsonArray arr = new JsonArray();
        accounts.forEach((k, v) -> arr.add(v));
        routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }
}
