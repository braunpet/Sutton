package de.fhws.fiw.fds.sutton.server.api.caching;

import javax.ws.rs.core.EntityTag;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;

/**
 * The EtagGenerator class creates Etags for serializable objects to send them to the client as a header in the
 * response to apply the caching mechanism according to the REST specifications
 * */
public class EtagGenerator
{
	public static EntityTag createEntityTag( final Serializable object )
	{
		return new EntityTag( createEtag( object ) );
	}

	/**
	 * Creates an Etag for the given serializable object
	 * @param object {@link Serializable} the object to create an Etag for
	 * @return an Etag {@link String} for the given object if it is not null
	 *
	 * @throws NullPointerException
	 * */
	public static String createEtag( final Serializable object )
	{
		if ( object == null )
		{
			throw new NullPointerException( "Cannot create Etag for null object" );
		}

		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try
		{
			baos = new ByteArrayOutputStream( );
			oos = new ObjectOutputStream( baos );
			oos.writeObject( object );
			final MessageDigest md = MessageDigest.getInstance( "MD5" );
			return DatatypeConverter.printHexBinary( md.digest( baos.toByteArray( ) ) );
		}
		catch ( final Exception e )
		{
			e.printStackTrace( );
			return null;
		}
		finally
		{
			try
			{
				oos.close( );
				baos.close( );
			}
			catch ( final IOException e )
			{
				// not handled.
			}
		}
	}
}
