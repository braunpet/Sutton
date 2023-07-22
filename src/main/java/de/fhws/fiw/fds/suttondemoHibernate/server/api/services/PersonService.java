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

package de.fhws.fiw.fds.suttondemoHibernate.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Person;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.queries.QueryByFirstAndLastName;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.queries.QueryByFirstNameEquals;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.queries.QueryByLocationName;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.states.person_locations.*;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.states.persons.*;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.utils.InitializeDatabase;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.utils.ResetDatabase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("persons")
public class PersonService extends AbstractService {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons(
            @DefaultValue("") @QueryParam("firstname") final String firstName,
            @DefaultValue("") @QueryParam("lastname") final String lastName,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size,
            @DefaultValue("0") @QueryParam("wait") int waitingTime,
            @DefaultValue("") @QueryParam("sort") final String orderByAttributes) {
        return new GetAllPersons.Builder().setQuery(new QueryByFirstAndLastName(firstName, lastName, offset, size, waitingTime, orderByAttributes))
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{firstName: \\p{L}+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsWithFirstName(@PathParam("firstName") final String firstName) {
        return new GetAllPersons.Builder().setQuery(new QueryByFirstNameEquals(firstName))
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSinglePerson(@PathParam("id") final long id) {
        return new GetSinglePerson.Builder().setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createSinglePerson(final Person personModel) {
        return new PostNewPerson.Builder().setModelToCreate(personModel)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateSinglePerson(@PathParam("id") final long id, final Person personModel) {
        return new PutSinglePerson.Builder().setRequestedId(id)
                .setModelToUpdate(personModel)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteSinglePerson(@PathParam("id") final long id) {
        return new DeleteSinglePerson.Builder().setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{personId: \\d+}/locations")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getLocationsOfPerson(@PathParam("personId") final long personId,
                                         @DefaultValue("") @QueryParam("cityname") final String cityName,
                                         @DefaultValue("0") @QueryParam("offset") int offset,
                                         @DefaultValue("20") @QueryParam("size") int size,
                                         @DefaultValue("0") @QueryParam("wait") int waitingTime,
                                         @DefaultValue("") @QueryParam("sort") final String orderByAttributes) {
        return new GetAllLocationsOfPerson.Builder()
                .setParentId(personId)
                .setQuery(new QueryByLocationName(personId, cityName, offset, size, waitingTime, orderByAttributes))
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{personId: \\d+}/locations/{locationId: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getLocationByIdOfPerson(@PathParam("personId") final long personId,
                                            @PathParam("locationId") final long locationId) {
        return new GetSingleLocationOfPerson.Builder()
                .setParentId(personId)
                .setRequestedId(locationId)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @POST
    @Path("{personId: \\d+}/locations")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createNewLocationOfPerson(@PathParam("personId") final long personId, final Location location) {
        return new PostNewLocationOfPerson.Builder()
                .setParentId(personId)
                .setModelToCreate(location)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @PUT
    @Path("{personId: \\d+}/locations/{locationId: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateNewLocationOfPerson(@PathParam("personId") final long personId,
                                              @PathParam("locationId") final long locationId, final Location location) {
        return new PutSingleLocationOfPerson.Builder()
                .setParentId(personId)
                .setRequestedId(locationId)
                .setModelToUpdate(location)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @DELETE
    @Path("{personId: \\d+}/locations/{locationId: \\d+}")
    public Response deleteLocationOfPerson(@PathParam("personId") final long personId,
                                           @PathParam("locationId") final long locationId) {
        return new DeleteSingleLocationOfPerson.Builder()
                .setParentId(personId)
                .setRequestedId(locationId)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

}
