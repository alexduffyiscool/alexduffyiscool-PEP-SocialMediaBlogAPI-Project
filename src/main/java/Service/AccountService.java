package Service;

import Model.Account;
import Model.Message;

import java.sql.SQLException;

import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public Account register(Account account) throws SQLException
    {
        if (account.getUsername() == null || account.getUsername().isBlank())
        {
            return null;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4)
        {
            return null;
        }
        if (accountDAO.findbyUsername(account.getUsername()) != null)
        {
            return null;
        }
        return accountDAO.create(account);
    }

    public Account login(Account creds) throws SQLException
    {
        return accountDAO.findbyUsernameAndPassword(creds.getUsername(), creds.getPassword());
    }
}
