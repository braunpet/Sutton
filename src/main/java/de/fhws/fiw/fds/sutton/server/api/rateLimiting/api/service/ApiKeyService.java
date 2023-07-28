package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.service;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.ApiKeyGenerator;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states.*;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("apikeys")
public class ApiKeyService extends AbstractService {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllApiKeys() {
        return new GetAllApiKeys.Builder()
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
    public Response getSingleApiKey(@PathParam("id") final long id) {
        return new GetSingleApiKeyById.GetApiKeyByIdStateBuilder()
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
    public Response createSingleApiKey(final ApiKey apiKeyModel) {
        return new PostApiKey.Builder()
                .setModelToCreate(apiKeyModel)
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
    public Response updateSingleApiKey(@PathParam("id") final long id, final ApiKey apiKeyModel) {
        return new PutApiKey.Builder()
                .setRequestedId(id)
                .setModelToUpdate(apiKeyModel)
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
    public Response deleteSingleApiKey(@PathParam("id") final long id) {
        return new DeleteSingleApiKeyById.Builder()
                .setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("generate")
    public Response createSingleApiKey(
            @DefaultValue("10") @QueryParam("resetRate") final long resetRateInSeconds,
            @DefaultValue("10") @QueryParam("requestLimit") final long requestLimit) {
        ApiKey apiKey = ApiKeyGenerator.generateUniqueKey(resetRateInSeconds, requestLimit);
        return new GetGeneratedApiKey.Builder()
                .setResetRate(resetRateInSeconds)
                .setRequestLimit(requestLimit)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @GET
    @Path("{apiKey}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleApiKey(@PathParam("apiKey") final String apiKey) {
        return new GetSingleApiKey.GetApiKeyStateBuilder()
                .setApiKey(apiKey)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }

    @DELETE
    @Path("{apiKey}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSingleApiKey(@PathParam("apiKey") final String apiKey) {
        return new DeleteSingleApiKey.DeleteApiKeyStateBuilder()
                .setApiKey(apiKey)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build()
                .execute();
    }


}
