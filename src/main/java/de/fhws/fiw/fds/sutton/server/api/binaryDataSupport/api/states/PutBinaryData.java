package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;

public class PutBinaryData extends AbstractPutState<BinaryDataModel>
        implements IBinaryDataDaoSupplier {

    public PutBinaryData(final Builder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<BinaryDataModel> loadModel() {
        return getBinaryDataDao().readById(this.modelToUpdate.getId());
    }

    @Override
    protected NoContentResult updateModel() {
        return getBinaryDataDao().update(this.modelToUpdate);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(BinaryDataUri.REL_PATH_ID, BinaryDataRelTypes.GET_SINGLE_BINARY_DATA, getAcceptRequestHeader(),
                this.modelToUpdate.getId());
        addLink(BinaryDataUri.REL_PATH, BinaryDataRelTypes.CREATE_BINARY_DATA, getAcceptRequestHeader());
    }

    public static class Builder extends AbstractPutStateBuilder<BinaryDataModel> {
        @Override
        public AbstractState build() {
            return new PutBinaryData(this);
        }
    }
}
