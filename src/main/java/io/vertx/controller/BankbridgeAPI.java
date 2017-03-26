package io.vertx.controller;

import io.vertx.bean.Account;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mockdao.AccountDAO;

public class BankbridgeAPI extends AbstractVerticle {

    private AccountDAO accountDAO = new AccountDAO();

    //private Map<String, JsonObject> accounts = new HashMap<>();

    @Override
    public void start() {

        //setUpInitialData();
        accountDAO.getAllAccounts();

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
                //accounts.put(accountId, account);
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
            JsonObject account = null;//accounts.get(accountId);
            if (account == null) {
                sendError(404, response);
            } else {
                response.putHeader("content-type", "application/json").end(account.encodePrettily());
            }
        }
    }



    private void getAllAccount(RoutingContext routingContext) {
        Account account = accountDAO.getAccountById("12345");
        String json = Json.encodePrettily(account);
        routingContext.response().putHeader("content-type", "application/json").end(json);
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }
}
