package de.fhws.fiw.fds.suttondemoHibernate.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.states.dispatcher.GetDispatcher;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.utils.InitializeDatabase;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.utils.ResetDatabase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class DispatcherService extends AbstractService {

    @GET
    public Response getDispatcher() {
        return new GetDispatcher.Builder().setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .setAuthProvider(this.authProvider)
                .build()
                .execute();
    }

    /*
     * JUST FOR TESTING!!!
     */

    @GET
    @Path("resetdatabase")
    @Produces({MediaType.APPLICATION_JSON})
    public Response resetDatabase() {
        System.out.println("RESET DATABASE");

        ResetDatabase.resetAll();

        return Response.ok().build();
    }

    @GET
    @Path("filldatabase")
    @Produces({MediaType.APPLICATION_JSON})
    public Response fillDatabase() {
        System.out.println("FILL DATABASE");

        InitializeDatabase.initializeDBWithRelations();

        return Response.ok().build();
    }
}
