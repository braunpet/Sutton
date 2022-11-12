package de.fhws.fiw.fds.demo.api.states.students;

import de.fhws.fiw.fds.demo.database.DaoFactory;
import de.fhws.fiw.fds.demo.models.Student;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;

public class GetAllStudents extends AbstractGetCollectionState<Student> {
	public GetAllStudents(final Builder builder) {
		super(builder);
	}

	@Override
	protected void authorizeRequest() {
	}

	protected void defineHttpResponseBody() {
		this.responseBuilder.entity(new GenericEntity<Collection<Student>>(this.result.getResult()) {
		});
	}

	@Override
	protected void defineTransitionLinks() {
		addLink(StudentUri.REL_PATH, StudentRelTypes.CREATE_STUDENT, getAcceptRequestHeader());
	}

	public static class AllStudents extends AbstractQuery<Student> {
		@Override
		protected CollectionModelResult<Student> doExecuteQuery() throws DatabaseException {
			return DaoFactory.getInstance().getStudentDao().readByPredicate(all());
		}
	}

	public static class Builder extends AbstractGetCollectionStateBuilder<Student> {
		@Override
		public AbstractState build() {
			return new GetAllStudents(this);
		}
	}
}
