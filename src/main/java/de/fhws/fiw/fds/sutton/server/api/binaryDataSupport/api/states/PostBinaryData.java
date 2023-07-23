package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;

public class PostBinaryData extends AbstractPostState<BinaryDataModel>
    implements IBinaryDataDaoSupplier {

    public PostBinaryData(final Builder builder) {
        super(builder);
    }

    @Override
    protected NoContentResult saveModel() {
        return getBinaryDataDao().create(this.modelToStore);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
    }

    public static class Builder extends AbstractPostStateBuilder<BinaryDataModel> {
        @Override
        public AbstractState build() {
            return new PostBinaryData(this);
        }
    }
}
