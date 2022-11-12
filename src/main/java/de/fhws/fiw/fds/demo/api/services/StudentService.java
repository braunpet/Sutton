package de.fhws.fiw.fds.demo.api.services;

import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;
import de.fhws.fiw.fds.demo.api.states.students.*;
import de.fhws.fiw.fds.demo.models.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("students")
public class StudentService extends AbstractService {
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllStudents() {
		return new GetAllStudents.Builder()
				.setQuery(new GetAllStudents.AllStudents())
				.setUriInfo(this.uriInfo)
				.setRequest(this.request)
				.setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context)
				.build()
				.execute();
	}

	@GET
	@Path("{id: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSingleStudent(@PathParam("id") final long id) {
		return new GetSingleStudent.Builder()
				.setRequestedId(id)
				.setUriInfo(this.uriInfo)
				.setRequest(this.request)
				.setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context)
				.build()
				.execute();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createSingleStudent(final Student StudentModel) {
		return new PostNewStudent.Builder()
				.setModelToCreate(StudentModel)
				.setUriInfo(this.uriInfo)
				.setRequest(this.request)
				.setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context)
				.build()
				.execute();
	}

	@PUT
	@Path("{id: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateSingleStudent(@PathParam("id") final long id, final Student StudentModel) {
		return new PutSingleStudent.Builder()
				.setRequestedId(id)
				.setModelToUpdate(StudentModel)
				.setUriInfo(this.uriInfo)
				.setRequest(this.request)
				.setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context)
				.build()
				.execute();
	}

	@DELETE
	@Path("{id: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteSingleStudent(@PathParam("id") final long id) {
		return new DeleteSingleStudent.Builder()
				.setRequestedId(id)
				.setUriInfo(this.uriInfo)
				.setRequest(this.request)
				.setHttpServletRequest(this.httpServletRequest)
				.setContext(this.context)
				.build()
				.execute();
	}
}
