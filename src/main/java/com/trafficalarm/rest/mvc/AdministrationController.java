package com.trafficalarm.rest.mvc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.services.AccountService;
import com.trafficalarm.core.services.exceptions.AccountExistsException;
import com.trafficalarm.core.services.util.AccountList;
import com.trafficalarm.rest.exceptions.ConflictException;
import com.trafficalarm.rest.mvc.resource.BaseResource;
import com.trafficalarm.rest.resources.AccountListResource;
import com.trafficalarm.rest.resources.AccountResource;
import com.trafficalarm.rest.resources.asm.AccountListResourceAsm;
import com.trafficalarm.rest.resources.asm.AccountResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/admin")
public class AdministrationController extends BaseResource
{
	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<AccountListResource> findAllAccounts(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password)
	{
		AccountList list = null;
		if (name == null)
		{
			list = accountService.findAllAccounts();
		} else
		{
			Account account = accountService.findByAccountName(name);
			list = new AccountList(new ArrayList<Account>());
			if (account != null)
			{
				if (password != null)
				{
					if (account.getPassword().equals(password))
					{
						list = new AccountList(Arrays.asList(account));
					}
				} else
				{
					list = new AccountList(Arrays.asList(account));
				}
			}
		}
		AccountListResource res = new AccountListResourceAsm().toResource(list);
		return new ResponseEntity<AccountListResource>(res, HttpStatus.OK);
	}

	/**
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<AccountResource> createAccount(@RequestBody AccountResource sentAccount)
	{
		try
		{
			Account createdAccount = accountService.createAccount(sentAccount.toAccount());
			AccountResource res = new AccountResourceAsm().toResource(createdAccount);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(res.getLink("self").getHref()));
			return new ResponseEntity<AccountResource>(res, headers, HttpStatus.CREATED);
		} catch (AccountExistsException exception)
		{
			throw new ConflictException(exception);
		} catch (Exception exception)
		{
			exception.printStackTrace();
			throw new RuntimeException(exception.getMessage());
		}
	}*/

}
