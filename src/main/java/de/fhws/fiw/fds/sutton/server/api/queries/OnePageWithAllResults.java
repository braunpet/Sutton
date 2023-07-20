package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * The OnePageWithAllResults is a paging behavior and represents sending the whole amount of the request data in one single response
 *
 * @see PagingBehaviorUsingOffsetSize
 * @see PagingBehaviorUsingPage
 */
public class OnePageWithAllResults<T extends AbstractModel> extends PagingBehaviorUsingOffsetSize<T> {

    public OnePageWithAllResults() {
        super(0, Integer.MAX_VALUE, "");
    }

    @Override
    protected int getDefaultMaxPageSize() {
        return Integer.MAX_VALUE;
    }
}
