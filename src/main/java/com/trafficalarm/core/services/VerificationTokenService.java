package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.VerificationToken;
import com.trafficalarm.rest.api.user.LostPasswordRequest;
import com.trafficalarm.rest.api.user.PasswordRequest;

public interface VerificationTokenService {

    public VerificationToken sendEmailVerificationToken(String userId);

    public VerificationToken sendEmailRegistrationToken(String userId);

    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest);

    public VerificationToken verify(String base64EncodedToken);

    public VerificationToken generateEmailVerificationToken(String emailAddress);

    public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest);
}
