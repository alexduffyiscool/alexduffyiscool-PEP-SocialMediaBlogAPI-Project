package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO)
    {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message createMessage(Message msg) throws SQLException
    {
        if (msg.getMessage_text() == null || msg.getMessage_text().isBlank())
        {
            return null;
        }
        if (msg.getMessage_text().length() > 225)
        {
            return null;
        }
        if (accountDAO.findMessageByID(msg.getPosted_by()) == null)
        {
            return null;
        }
        return messageDAO.createMessage(msg);
    }

    public List<Message> getAllMessages() throws SQLException
    {
        return messageDAO.getAll();
    }

    public Message getMessageByID(int id) throws SQLException
    {
        return messageDAO.findMessageByID(id);
    }

    public Message deleteMessage(int id) throws SQLException
    {
        Message msg = messageDAO.findMessageByID(id);
        if (msg == null)
        {
            return null;
        }
        messageDAO.delete(id);
        return msg;
    }

    public Message updateMessage(int id, String replace) throws SQLException
    {
        if (replace == null || replace.isBlank() || replace.length() > 225)
        {
            return null;
        }
        Message msg = messageDAO.findMessageByID(id);
        if (msg == null)
        {
            return null;
        }
        msg.setMessage_text(replace);
        messageDAO.update(msg);
        return msg;
    }

    public List<Message> getMessagesByAccount(int id) throws SQLException
    {
        return messageDAO.findAccountByID(id);
    }
}
