package DAO;

import java.sql.*;
import java.util.Optional;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {

    public Account create(Account act) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, act.getUsername());
        ps.setString(2, act.getPassword());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next())
        {
            act.setAccount_id(rs.getInt(1));
        }

        rs.close();
        ps.close();
        connection.close();

        return act;
    }

    public Account findbyUsername(String username) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        Account account = null;
        if (rs.next())
        {
            account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        }

        rs.close();
        ps.close();
        connection.close();

        return account;
    }

    public Account findbyUsernameAndPassword(String username, String password) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        Account account = null;
        if (rs.next())
        {
            account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        }

        rs.close();
        ps.close();
        connection.close();

        return account;
    }

    public Account findMessageByID(int id) throws SQLException
    {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE account_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Account account = null;
        if (rs.next())
        {
            account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        }

        rs.close();
        ps.close();
        connection.close();

        return account;
    }
    
}
