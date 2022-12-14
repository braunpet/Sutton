/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.suttondemo.server.api.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByFirstAndLastName;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.DeleteSinglePerson;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.GetAllPersons;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.GetSinglePerson;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.PostNewPerson;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.PutSinglePerson;
import de.fhws.fiw.fds.suttondemo.server.database.utils.ResetDatabase;
import de.fhws.fiw.fds.suttondemo.server.models.Person;

@Path("persons")
public class PersonService extends AbstractService {
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getAllPersons(@DefaultValue("") @QueryParam("firstname") final String firstName,
			@DefaultValue("") @QueryParam("lastname") final String lastName) {
		return new GetAllPersons.Builder()
				.setQuery(new QueryByFirstAndLastName(firstName, lastName)).setUriInfo(this.uriInfo)
				.setRequest(this.request).setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context).build().execute();
	}

	@GET
	@Path("{id: \\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getSinglePerson(@PathParam("id") final long id) {
		return new GetSinglePerson.Builder().setRequestedId(id).setUriInfo(this.uriInfo)
				.setRequest(this.request).setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context).build().execute();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createSinglePerson(final Person personModel) {
		return new PostNewPerson.Builder().setModelToCreate(personModel).setUriInfo(this.uriInfo)
				.setRequest(this.request).setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context).build().execute();
	}

	@PUT
	@Path("{id: \\d+}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateSinglePerson(@PathParam("id") final long id, final Person personModel) {
		return new PutSinglePerson.Builder().setRequestedId(id).setModelToUpdate(personModel)
				.setUriInfo(this.uriInfo).setRequest(this.request)
				.setHttpServletRequest(this.httpServletRequest).setContext(this.context).build()
				.execute();
	}

	@DELETE
	@Path("{id: \\d+}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response deleteSinglePerson(@PathParam("id") final long id) {
		return new DeleteSinglePerson.Builder().setRequestedId(id).setUriInfo(this.uriInfo)
				.setRequest(this.request).setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context).build().execute();
	}

	@GET
	@Path("resetdatabase")
	@Produces({MediaType.APPLICATION_JSON})
	public Response resetDatabase() {
		System.out.println("RESET DATABASE");

		ResetDatabase.reset();

		return Response.ok().build();
	}
}
