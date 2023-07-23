package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;

public class DeleteSingleBinaryData extends AbstractDeleteState<BinaryDataModel>
        implements IBinaryDataDaoSupplier {

    public DeleteSingleBinaryData(final Builder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<BinaryDataModel> loadModel() {
        return getBinaryDataDao().readById(this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return getBinaryDataDao().delete(this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
    }

    @Override
    protected void authorizeRequest() {

    }

    public static class Builder extends AbstractDeleteStateBuilder {
        @Override
        public AbstractState build() {
            return new DeleteSingleBinaryData(this);
        }
    }
}
