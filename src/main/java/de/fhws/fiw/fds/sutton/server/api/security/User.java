package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The User class extends {@link AbstractModel} and describes the users who are making the HTTP requests. The user class
 * is used in the context of HTTP security to authenticate clients when making HTTP requests
 * */
public class User extends AbstractModel
{
	private String name;

	private String secret;

	private String role;

	public User( )
	{
	}

	public User( final String name, final String secret )
	{
		this.name = name;
		this.secret = secret;
		this.role = "";
	}

	public User( final String name, final String secret, final String role )
	{
		this.name = name;
		this.secret = secret;
		this.role = role;
	}

	public String getName( )
	{
		return name;
	}

	public String getSecret( )
	{
		return secret;
	}

	public String getRole( )
	{
		return role;
	}

	/**
	 * Returns a clone of the user without their password
	 * @return {@link User} a clone of the user
	 * */
	public User cloneWithoutSecret( )
	{
		final User returnValue = new User( );

		returnValue.id = this.id;
		returnValue.role = this.role;

		return returnValue;
	}
}
