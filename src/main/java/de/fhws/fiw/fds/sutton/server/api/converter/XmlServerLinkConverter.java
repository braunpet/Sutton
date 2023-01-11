package de.fhws.fiw.fds.sutton.server.api.converter;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The XmlServerLinkConverter class provides the required functionality to serialize the {@link Link} property to the
 * XML format for {@link de.fhws.fiw.fds.sutton.server.models.AbstractModel} objects, which are
 * used as resources in Sutton framework, it is also responsible for the deserialization process in order to recreate
 * the {@link Link} from XML sent in the request body
 * */
public class XmlServerLinkConverter extends XmlAdapter<ServerLink, Link>
{
	@Override
	public Link unmarshal( final ServerLink v ) throws Exception
	{
		if ( v != null )
		{
			return Link.fromUri( v.getHref( ) ).build( );
		}
		else
		{
			return null;
		}
	}

	@Override
	public ServerLink marshal( final Link v ) throws Exception
	{
		if ( v != null )
		{
			return new ServerLink( v.getUri( ).toASCIIString( ), v.getRel( ), v.getType( ) );
		}
		else
		{
			return null;
		}
	}
}
