package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.USER_ROLES;

public class GetSingleBinaryData extends AbstractGetState<BinaryDataModel>
    implements IBinaryDataDaoSupplier {


    public GetSingleBinaryData(AbstractGetStateBuilder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<BinaryDataModel> loadModel() {
        return getBinaryDataDao().readById(requestedId);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink( BinaryDataUri.REL_PATH_ID, BinaryDataRelTypes.UPDATE_SINGLE_BINARY_DATA, getAcceptRequestHeader( ),
                this.requestedId );
        addLink( BinaryDataUri.REL_PATH_ID, BinaryDataRelTypes.DELETE_SINGLE_BINARY_DATA, getAcceptRequestHeader( ),
                this.requestedId );
    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class GetBinaryDataStateBuilder extends AbstractGetStateBuilder {
        @Override
        public GetSingleBinaryData build() {
            return new GetSingleBinaryData(this);
        }
    }
}
