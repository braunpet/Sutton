package de.fhws.fiw.fds.suttondemoHibernate.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.states.locations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("locations")
public class LocationService extends AbstractService {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllLocations() {
        return new GetAllLocations.Builder()
                .setQuery(new GetAllLocations.AllLocations())
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .setAuthProvider(this.authProvider)
                .build()
                .execute();
    }

    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleLocation(@PathParam("id") final long id) {
        return new GetSingleLocation.Builder()
                .setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .setAuthProvider(this.authProvider)
                .build()
                .execute();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSingleLocation(final Location locationModel) {
        return new PostNewLocation.Builder()
                .setModelToCreate(locationModel)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .setAuthProvider(this.authProvider)
                .build()
                .execute();
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSingleLocation(@PathParam("id") final long id, final Location locationModel) {
        return new PutSingleLocation.Builder()
                .setRequestedId(id)
                .setModelToUpdate(locationModel)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .setAuthProvider(this.authProvider)
                .build()
                .execute();
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSingleLocation(@PathParam("id") final long id) {
        return new DeleteSingleLocation.Builder()
                .setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .setAuthProvider(this.authProvider)
                .build()
                .execute();
    }

}
