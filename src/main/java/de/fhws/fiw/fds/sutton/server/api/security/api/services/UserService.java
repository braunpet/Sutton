package de.fhws.fiw.fds.sutton.server.api.security.api.services;

import de.fhws.fiw.fds.sutton.server.api.security.api.queries.QueryByRoleNameOfUserLike;
import de.fhws.fiw.fds.sutton.server.api.security.api.queries.QueryByUserNameLike;
import de.fhws.fiw.fds.sutton.server.api.security.api.states.user.*;
import de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role.*;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
public class UserService extends AbstractService {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers(
            @DefaultValue("") @QueryParam("name") final String name,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size,
            @DefaultValue("0") @QueryParam("wait") int waitingTime,
            @DefaultValue("") @QueryParam("sort") final String orderByAttributes) {
        return new GetAllUsers.Builder().setQuery(new QueryByUserNameLike(name, offset, size, waitingTime, orderByAttributes))
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
    public Response getSingleUser(@PathParam("id") final long id) {
        return new GetSingleUser.GetUserStateBuilder().setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createSingleUser(final User userModel) {
        return new PostUser.Builder().setModelToCreate(userModel)
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
    public Response updateSingleUser(@PathParam("id") final long id, final User userModel) {
        return new PutUser.Builder().setRequestedId(id)
                .setModelToUpdate(userModel)
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
    public Response deleteSingleUser(@PathParam("id") final long id) {
        return new DeleteSingleUser.Builder().setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{userId: \\d+}/roles")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRolesOfUser(@PathParam("userId") final long userId,
                                   @DefaultValue("") @QueryParam("name") final String name,
                                   @DefaultValue("0") @QueryParam("offset") int offset,
                                   @DefaultValue("20") @QueryParam("size") int size,
                                   @DefaultValue("0") @QueryParam("wait") int waitingTime,
                                   @DefaultValue("") @QueryParam("sort") final String orderByAttributes) {
        return new GetAllRolesOfUser.Builder().setQuery(new QueryByRoleNameOfUserLike(userId, name, offset, size, waitingTime, orderByAttributes))
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{userId: \\d+}/roles/{roleId: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRoleByIdOfUser(@PathParam("userId") final long userId,
                                      @PathParam("roleId") final long roleId) {
        return new GetSingleRoleOfUser.Builder()
                .setParentId(userId)
                .setRequestedId(roleId)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @POST
    @Path("{userId: \\d+}/roles")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createNewRoleOfUser(@PathParam("userId") final long userId, final Role role) {
        return new PostNewRoleOfUser.Builder()
                .setParentId(userId)
                .setModelToCreate(role)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @PUT
    @Path("{userId: \\d+}/roles/{roleId: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateNewRoleOfUser(@PathParam("userId") final long userId,
                                        @PathParam("roleId") final long roleId, final Role role) {
        return new PutSingleRoleOfUser.Builder()
                .setParentId(userId)
                .setRequestedId(roleId)
                .setModelToUpdate(role)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @DELETE
    @Path("{userId: \\d+}/roles/{roleId: \\d+}")
    public Response deleteRoleOfUser(@PathParam("userId") final long userId,
                                     @PathParam("roleId") final long roleId) {
        return new DeleteSingleRoleOfUser.Builder()
                .setParentId(userId)
                .setRequestedId(roleId)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

}
