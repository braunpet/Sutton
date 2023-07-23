package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.binaryData.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.models.BinaryDataModel;

public class PostRawBinaryData extends AbstractPostState<BinaryDataModel>
        implements IBinaryDataDaoSupplier {

    public PostRawBinaryData(final Builder builder) {
        super(builder);
        this.modelToStore = new BinaryDataModel(builder.binaryData);
    }

    @Override
    protected NoContentResult saveModel() {
        return getBinaryDataDao().create(modelToStore);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
    }

    public static class Builder extends AbstractPostStateBuilder<BinaryDataModel> {
        private byte[] binaryData;

        public Builder setBinaryData(byte[] binaryData) {
            this.binaryData = binaryData;
            return this;
        }

        @Override
        public AbstractState build() {
            return new PostRawBinaryData(this);
        }
    }
}
