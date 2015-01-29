package com.trafficalarm.core.model.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.structure.BaseEntity;
import com.trafficalarm.rest.api.user.ApiUser;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class Account extends BaseEntity implements UserDetails
{
	// email address
	private String name;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	private boolean passive;

	@ElementCollection(targetClass = Role.class, fetch=FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<Role> roles = new ArrayList<Role>();

	public Account(String id)
	{
		super(id);
	}

	public Account(final ApiUser apiUser, final String hashedPassword, Role role)
	{
		this();
		this.name = apiUser.getEmailAddress().toLowerCase();
		this.password = hashedPassword;
		this.firstName = apiUser.getFirstName();
		this.lastName = apiUser.getLastName();
		this.roles.add(role);
	}

	public Account()
	{
		this.entityName = "Account";
		// this.passive=true;
	}

	@Column(nullable = false, unique = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Date getLastLogin()
	{
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin)
	{
		this.lastLogin = lastLogin;
	}

	public boolean isPassive()
	{
		return passive;
	}

	public void setPassive(boolean passive)
	{
		this.passive = passive;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void addRole(Role role)
	{
		this.roles.add(role);
	}

	public boolean hasRole(Role role)
	{
		return (this.roles.contains(role));
	}

	public List<Role> getRoles()
	{
		return Collections.unmodifiableList(this.roles);
	}

	private void deSerializeRoles(List<String> roles)
	{
		for (String role : roles)
		{
			this.addRole(Role.valueOf(role));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role role : this.getRoles())
		{
			GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
			authorities.add(authority);
		}
		return authorities;
	}

	@Override
	public String getUsername()
	{
		return getId();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return getId() + "";
	}
}
