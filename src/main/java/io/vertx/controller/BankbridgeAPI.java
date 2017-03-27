package io.vertx.controller;

import io.vertx.bean.Account;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mockdao.AccountDAO;

import java.util.List;

public class BankbridgeAPI extends AbstractVerticle {

    private AccountDAO accountDAO = new AccountDAO();
    private static final Logger log = LoggerFactory.getLogger(BankbridgeAPI.class);

    @Override
    public void start() {

        accountDAO.initData();

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/accounts/:id").handler(this::getByAccountId);
        router.get("/accounts").handler(this::getAllAccount);
        router.put("/accounts/:id").handler(this::updateByAccountId);
        router.patch("/accounts/:id").handler(this::updateByAccountId);
        //router.post("/accounts").handler(this::addByAccountId);
        router.delete("/accounts/:id").handler(this::deleteByAccountId);

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    //TODO:implementing POST, PUT
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
        Account account = accountDAO.findByAccountId(accountId);

        HttpServerResponse response = routingContext.response();
        if (account.getId() == null) {
            sendError(400, response);
        } else {
            String json = Json.encodePrettily(account);
            if (json == null) {
                sendError(404, response);
            } else {
                response.putHeader("content-type", "application/json").end(json);
            }
        }
    }


    private void getAllAccount(RoutingContext routingContext) {
        List<Account> accountList = accountDAO.findAllAccount();

        String json = Json.encodePrettily(accountList);
        routingContext.response().putHeader("content-type", "application/json").end(json);
    }

    //Its a mess
    private void updateByAccountId(RoutingContext routingContext) {


        Account update = Json.decodeValue(routingContext.getBodyAsString(), Account.class);
        String accountId = routingContext.request().getParam("id");
        Account account = accountDAO.findByAccountId(accountId);

        if (account.getId() == null) {
            routingContext.response().setStatusCode(404).end();
        } else {
            account.setBank_id(update.getBank_id());
            account.setIBAN(update.getIBAN());
            //account.setBalance(balance);
            accountDAO.updateByAccountId(account);
        }
        routingContext.response().setStatusCode(201).end();

    }

    private void deleteByAccountId(RoutingContext routingContext) {

        String accountId = routingContext.request().getParam("id");
        Account account = accountDAO.findByAccountId(accountId);

        if (account.getId() == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            accountDAO.deleteByAccountId(account.getId());
        }
        routingContext.response().setStatusCode(204).end();

    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }
}
