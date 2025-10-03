package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import DAO.MessageDAO;
import DAO.AccountDAO;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message createMessage(Message msg) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, msg.getPosted_by());
        ps.setString(2, msg.getMessage_text());
        ps.setLong(3, msg.getTime_posted_epoch());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next())
        {
            msg.setMessage_id(rs.getInt(1));
        }

        rs.close();
        ps.close();
        connection.close();

        return msg;
    }

    public List<Message> getAll() throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message";
        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        List<Message> messages = new ArrayList<>();
        while (rs.next())
        {
            messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
        }

        rs.close();
        ps.close();
        connection.close();

        return messages;
    }

    public Message findMessageByID(int id) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        Message message = null;
        if (rs.next())
        {
            message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
        }

        rs.close();
        ps.close();
        connection.close();

        return message;
    }

    public void delete(int id) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "DELETE FROM message WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

        ps.close();
        connection.close();
    }

    public void update(Message msg) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, msg.getMessage_text());
        ps.setInt(2, msg.getMessage_id());

        ps.executeUpdate();

        ps.close();
        connection.close();
    }

    public List<Message> findAccountByID(int id) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        List<Message> messages = new ArrayList<>();
        while (rs.next())
        {
            messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
        }

        rs.close();
        ps.close();
        connection.close();

        return messages;
    }
}
