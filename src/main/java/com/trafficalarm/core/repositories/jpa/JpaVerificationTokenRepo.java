package com.trafficalarm.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.VerificationToken;
import com.trafficalarm.core.model.entities.VerificationTokenType;
import com.trafficalarm.core.repositories.VerificationTokenRepo;

/**
 * Created by webyildirim on 7/9/14.
 */
@Repository
public class JpaVerificationTokenRepo implements VerificationTokenRepo
{
	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MainDao dao;

	public JpaVerificationTokenRepo()
	{
		//dao = new MainDao();
	}

	@Override
	public VerificationToken save(VerificationToken data) throws Exception
	{
		return (VerificationToken) dao.saveOrUpdateEntity(manager, data);
	}
	
	@Override
	public VerificationToken findById(String id)
	{
		BaseEntity entity = new VerificationToken();
		entity.setId(id);
		return (VerificationToken) dao.findByPrimaryKey(manager, entity);
	}

	@Override
	public VerificationToken findByToken(String token)
	{
		Query query = manager.createQuery("SELECT vt FROM VerificationToken vt WHERE vt.token=?1");
		query.setParameter(1, token);
		return (VerificationToken) query.getSingleResult();
	}

	@Override
	public List<VerificationToken> findByUserId(String userId)
	{
		Query query = manager.createQuery("SELECT vt FROM VerificationToken vt WHERE vt.account.id=?1");
		query.setParameter(1, userId);
		return query.getResultList();
	}

	@Override
	public List<VerificationToken> findByUserIdAndTokenType(String userId, VerificationTokenType tokenType)
	{
		Query query = manager.createQuery("SELECT vt FROM VerificationToken vt WHERE vt.account.id=?1 AND vt.tokenType=?2");
		query.setParameter(1, userId);
		query.setParameter(2, tokenType);
		return query.getResultList();
	}
}
