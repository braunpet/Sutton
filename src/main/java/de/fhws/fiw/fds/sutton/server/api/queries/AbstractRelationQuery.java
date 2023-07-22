package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.AbstractAttributeEqualsValue;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.util.List;

/**
 * The AbstractRelationQuery extends the functionality of the {@link AbstractQuery} in order to fetch associated
 * data from the database
 */
public abstract class AbstractRelationQuery<T extends AbstractModel> extends AbstractQuery<T> {

    protected long primaryId;

    protected AbstractRelationQuery(final long primaryId) {
        this.primaryId = primaryId;
    }

    public AbstractRelationQuery setPagingBehavior(final PagingBehavior pagingBehavior) {
        super.setPagingBehavior(pagingBehavior);
        return this;
    }

    public AbstractRelationQuery setOrderByAttributes(final String orderByAttributes) {
        super.setOrderByAttributes(orderByAttributes);
        return this;
    }

    public AbstractRelationQuery addAttributeEqualValue(final AbstractAttributeEqualsValue attributeEqualValue) {
        super.addAttributeEqualValue(attributeEqualValue);
        return this;
    }

    public AbstractRelationQuery setAttributesEqualsValues(final List<AbstractAttributeEqualsValue> attributesEqualsValues) {
        super.setAttributesEqualsValues(attributesEqualsValues);
        return this;
    }

}
