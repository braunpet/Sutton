/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.converter;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import javax.ws.rs.core.Link;

/**
 * The JsonServerLinkConverter class provides the required functionality to serialize the {@link Link} property to the
 * JSON format for {@link de.fhws.fiw.fds.sutton.server.models.AbstractModel} objects, which are
 * used as resources in Sutton framework, it is also responsible for the deserialization process in order to recreate
 * the {@link Link} from the JSON object sent in the response body
 * */
public class JsonServerLinkConverter implements Converter<Link>
{
	public JsonServerLinkConverter( )
	{
	}

	@Override public void serialize( final Link link, final ObjectWriter objectWriter, final Context context )
		throws Exception
	{
		objectWriter.writeName( link.getTitle( ) );
		objectWriter.beginObject( );
		objectWriter.writeString( "href", this.replaceCharacters( link.getUri( ).toASCIIString( ) ) );
		objectWriter.writeString( "rel", link.getRel( ) );
		if ( link.getType( ) != null && !link.getType( ).isEmpty( ) )
		{
			objectWriter.writeString( "type", link.getType( ) );
		}

		objectWriter.endObject( );
	}

	@Override public Link deserialize( final ObjectReader objectReader, final Context context ) throws Exception
	{
		Link returnValue = null;
		objectReader.beginObject( );

		while ( objectReader.hasNext( ) )
		{
			objectReader.next( );
			if ( "href".equals( objectReader.name( ) ) )
			{
				final String link = objectReader.valueAsString( );
				returnValue = Link.fromUri( link ).build( new Object[ 0 ] );
			}
		}

		objectReader.endObject( );
		return returnValue;
	}

	private String replaceCharacters( final String body )
	{
		return body.replace( "%3F", "?" ).replaceAll( "%7B", "{" ).replaceAll( "%7D", "}" );
	}
}
