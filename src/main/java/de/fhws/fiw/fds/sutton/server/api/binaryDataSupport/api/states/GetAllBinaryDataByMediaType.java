package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;
import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.USER_ROLES;

public class GetAllBinaryDataByMediaType extends AbstractGetCollectionState<BinaryDataModel>
        implements IBinaryDataDaoSupplier {

    protected String mediaType;

    public GetAllBinaryDataByMediaType(final Builder builder) {
        super(builder);
        this.mediaType = builder.mediaType;
        this.query = new AbstractQuery<BinaryDataModel>() {
            @Override
            protected CollectionModelResult<BinaryDataModel> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
                return getBinaryDataDao().readAllByMediaType(mediaType);
            }
        };
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<BinaryDataModel>>(this.result.getResult()) {
        });
    }

    @Override
    protected void defineTransitionLinks() {

    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractGetCollectionStateBuilder<BinaryDataModel> {

        protected String mediaType;

        public Builder setMediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        @Override
        public AbstractState build() {
            return new GetAllBinaryDataByMediaType(this);
        }
    }
}
