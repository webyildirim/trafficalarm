package com.accmee.trafficalarm.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.accmee.trafficalarm.core.services.util.AccountList;
import com.accmee.trafficalarm.rest.mvc.AccountController;
import com.accmee.trafficalarm.rest.resources.AccountListResource;
import com.accmee.trafficalarm.rest.resources.AccountResource;

import java.util.List;

/**
 * Created by Chris on 7/22/14.
 */
public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountList, AccountListResource> {


    public AccountListResourceAsm() {
        super(AccountController.class, AccountListResource.class);
    }

    @Override
    public AccountListResource toResource(AccountList accountList) {
        List<AccountResource> resList = new AccountResourceAsm().toResources(accountList.getAccounts());
        AccountListResource finalRes = new AccountListResource();
        finalRes.setAccounts(resList);
        return finalRes;
    }
}
