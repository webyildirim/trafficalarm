package com.trafficalarm.core.repositories;

import java.util.List;

import com.trafficalarm.core.model.entities.Account;

/**
 * Created by webyildirim.
 */
public interface AccountRepo {
    public List<Account> findAllAccounts();
    public Account findAccount(String id);
    public Account findAccountByName(String name);
    public Account createAccount(Account data) throws Exception;
    public Account deleteAccount(String id) throws Exception;
}
