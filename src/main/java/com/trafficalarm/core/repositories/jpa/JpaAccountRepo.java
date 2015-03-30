package com.trafficalarm.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.filter.AccountFilter;
import com.trafficalarm.core.repositories.AccountRepo;

/**
 * Created by webyildirim on 7/9/14.
 */
@Repository
public class JpaAccountRepo implements AccountRepo
{
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private MainDao dao;

	public JpaAccountRepo()
	{
		//dao = new MainDao();
	}

	@Override
	public List<Account> findAllAccounts()
	{
		return (List<Account>) dao.getEntityList(manager, new Account());
	}

	@Override
	public Account findAccount(String id)
	{
		BaseEntity entity = new Account();
		entity.setId(id);
		return (Account) dao.findByPrimaryKey(manager, entity);
	}

	@Override
	public Account findAccountByName(String name)
	{
		Account account = new Account();
		account.setName(name);
		return (Account) dao.findObjectByFilter(manager, new AccountFilter(account));
	}

	@Override
	public Account createAccount(Account data) throws Exception
	{
		return (Account) dao.saveOrUpdateEntity(manager, data);
	}

	@Override
	public Account deleteAccount(String id) throws Exception
	{
		Account account = findAccount(id);
		if (account != null)
			dao.removeEntity(manager, account);
		return account;
	}
}
