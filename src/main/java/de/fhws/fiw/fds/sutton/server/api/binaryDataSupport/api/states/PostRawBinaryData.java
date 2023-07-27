package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.IBinaryDataDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.USER_ROLES;

public class PostRawBinaryData extends AbstractPostState<BinaryDataModel>
        implements IBinaryDataDaoSupplier {

    public PostRawBinaryData(final Builder builder) {
        super(builder);
        this.modelToStore = new BinaryDataModel(builder.binaryData, builder.mediaType);
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

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractPostStateBuilder<BinaryDataModel> {
        private byte[] binaryData;

        private String mediaType;

        public Builder setBinaryData(byte[] binaryData) {
            this.binaryData = binaryData;
            return this;
        }

        public Builder setMediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        @Override
        public AbstractState build() {
            return new PostRawBinaryData(this);
        }
    }
}
