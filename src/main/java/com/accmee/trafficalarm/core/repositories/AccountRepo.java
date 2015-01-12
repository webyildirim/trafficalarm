package com.accmee.trafficalarm.core.repositories;

import com.accmee.trafficalarm.core.models.entities.Account;

import java.util.List;

/**
 * Created by webyildirim.
 */
public interface AccountRepo {
    public List<Account> findAllAccounts();
    public Account findAccount(Long id);
    public Account findAccountByName(String name);
    public Account createAccount(Account data) throws Exception;
    public Account deleteAccount(Long id) throws Exception;
}
