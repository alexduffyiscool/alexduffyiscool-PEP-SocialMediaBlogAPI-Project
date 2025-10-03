package Controller;

import static org.mockito.ArgumentMatchers.nullable;

import java.sql.SQLException;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController()
    {
        AccountDAO accountDAO = new AccountDAO();
        MessageDAO messageDAO = new MessageDAO();

        this.accountService = new AccountService(accountDAO);
        this.messageService = new MessageService(messageDAO, accountDAO);
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context ctx) throws SQLException
    {
        Account act = ctx.bodyAsClass(Account.class);
        Account created = accountService.register(act);
        if (created == null)
        {
            ctx.status(400);
            ctx.result("");
        }
        else
        {
            ctx.json(created);
        }
    }

    private void loginHandler(Context ctx) throws SQLException
    {
        Account creds = ctx.bodyAsClass(Account.class);
        Account found = accountService.login(creds);
        if (found == null)
        {
            ctx.status(401);
        }
        else
        {
            ctx.json(found);
        }
    }

    private void createMessageHandler(Context ctx) throws SQLException
    {
        Message msg = ctx.bodyAsClass(Message.class);
        Message created = messageService.createMessage(msg);
        if (created == null)
        {
            ctx.status(400);
            ctx.result("");
        }
        else
        {
            ctx.json(created);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws SQLException
    {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIDHandler(Context ctx) throws SQLException
    {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.getMessageByID(id);
        if (msg == null)
        {
            ctx.json("");
        }
        else
        {
            ctx.json(msg);
        }
    }

    private void deleteMessageHandler(Context ctx) throws SQLException
    {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessage(id);
        if (deleted == null)
        {
            ctx.json("");
        }
        else
        {
            ctx.json(deleted);
        }
    }

    private void updateMessageHandler(Context ctx) throws SQLException
    {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updateReq = ctx.bodyAsClass(Message.class);
        Message updated = messageService.updateMessage(id, updateReq.getMessage_text());
        if (updated == null)
        {
            ctx.status(400);
        }
        else
        {
            ctx.json(updated);
        }
    }

    private void getMessagesByAccountHandler(Context ctx) throws SQLException
    {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getMessagesByAccount(account_id));
    }
}