package de.fhws.fiw.fds.sutton.server.api.security.api.services;

import de.fhws.fiw.fds.sutton.server.api.security.api.queries.QueryByRoleNameLike;
import de.fhws.fiw.fds.sutton.server.api.security.api.states.role.*;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("roles")
public class RoleService extends AbstractService {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllRoles(
            @DefaultValue("") @QueryParam("name") final String name,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size,
            @DefaultValue("0") @QueryParam("wait") int waitingTime,
            @DefaultValue("") @QueryParam("sort") final String orderByAttributes) {
        return new GetAllRoles.Builder()
                .setQuery(new QueryByRoleNameLike(name, offset, size, waitingTime, orderByAttributes))
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleRole(@PathParam("id") final long id) {
        return new GetSingleRole.GetRoleStateBuilder()
                .setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSingleRole(final Role roleModel) {
        return new PostRole.Builder()
                .setModelToCreate(roleModel)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSingleRole(@PathParam("id") final long id, final Role roleModel) {
        return new PutRole.Builder()
                .setRequestedId(id)
                .setModelToUpdate(roleModel)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSingleRole(@PathParam("id") final long id) {
        return new DeleteSingleRole.Builder()
                .setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }
}