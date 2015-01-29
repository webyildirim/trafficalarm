package com.trafficalarm.core.repositories;

import java.util.List;

import com.trafficalarm.core.model.entities.VerificationToken;
import com.trafficalarm.core.model.entities.VerificationTokenType;

/**
 * Created by webyildirim.
 */
public interface VerificationTokenRepo
{
	public VerificationToken save(VerificationToken data) throws Exception;
	
	public VerificationToken findById(String id);

	public VerificationToken findByToken(String token);

	public List<VerificationToken> findByUserId(String userId);

	public List<VerificationToken> findByUserIdAndTokenType(String userId, VerificationTokenType tokenType);
}
