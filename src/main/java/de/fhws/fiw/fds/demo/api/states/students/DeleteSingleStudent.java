package de.fhws.fiw.fds.demo.api.states.students;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.demo.database.DaoFactory;
import de.fhws.fiw.fds.demo.models.Student;

public class DeleteSingleStudent extends AbstractDeleteState<Student> {
	public DeleteSingleStudent(final Builder builder) {
		super(builder);
	}

	@Override
	protected void authorizeRequest() {
	}

	@Override
	protected SingleModelResult<Student> loadModel() {
		return DaoFactory.getInstance().getStudentDao().readById(this.modelIdToDelete);
	}

	@Override
	protected NoContentResult deleteModel() {
		return DaoFactory.getInstance().getStudentDao().delete(this.modelIdToDelete);
	}

	@Override
	protected void defineTransitionLinks() {
	}

	public static class Builder extends AbstractDeleteStateBuilder {
		@Override
		public AbstractState build() {
			return new DeleteSingleStudent(this);
		}
	}
}
