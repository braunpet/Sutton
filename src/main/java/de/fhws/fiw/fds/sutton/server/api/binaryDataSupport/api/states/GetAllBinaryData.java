package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;

public class GetAllBinaryData extends AbstractGetCollectionState<BinaryDataModel>
        implements IBinaryDataDaoSupplier {

    public GetAllBinaryData(final Builder builder) {
        super(builder);
        this.query = new AbstractQuery<BinaryDataModel>() {
            @Override
            protected CollectionModelResult<BinaryDataModel> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
                return getBinaryDataDao().readAll();
            }
        };
    }

    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<BinaryDataModel>>(this.result.getResult()) {
        });
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(BinaryDataUri.REL_PATH, BinaryDataRelTypes.CREATE_BINARY_DATA, getAcceptRequestHeader());
    }

    public static class Builder extends AbstractGetCollectionStateBuilder<BinaryDataModel> {

        @Override
        public AbstractState build() {
            return new GetAllBinaryData(this);
        }
    }
}
