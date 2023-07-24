package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteAllState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

public class DeleteAllBinaryDataByMediaType extends AbstractDeleteAllState
        implements IBinaryDataDaoSupplier {

    protected String mediaType;

    public DeleteAllBinaryDataByMediaType(final Builder builder) {
        super(builder);
        this.mediaType = builder.mediaType;
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected NoContentResult deleteAll() {
        return getBinaryDataDao().deleteAllByMediaType(this.mediaType);
    }

    @Override
    protected void defineTransitionLinks() {

    }

    public static class Builder extends AbstractDeleteAllStateBuilder<BinaryDataModel> {

        private String mediaType;

        public Builder setMediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        @Override
        public AbstractState build() {
            return new DeleteAllBinaryDataByMediaType(this);
        }
    }
}
