/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The PagingBehaviorUsingOffsetSize class is an instance of {@link PagingBehavior} and describes a paging behavior
 * in which the collection of the full results is divided into subsets (pages). The client can specify the size of the
 * page and the index in the collection of the full results, from which the entries in the page should start
 *
 * @see PagingBehaviorUsingPage
 * @see OnePageWithAllResults
 */
public class PagingBehaviorUsingOffsetSize<T extends AbstractModel> extends PagingBehavior<T> {

    /**
     * The default page size {@link Integer} to use when no size is provided
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * The default page size {@link String} to use when no size is specified in the request
     */
    public static final String DEFAULT_PAGE_SIZE_STR = "" + DEFAULT_PAGE_SIZE;

    /**
     * the highest allowed value {@link Integer} for the page size
     */
    public static final int DEFAULT_MAX_PAGE_SIZE = Integer.MAX_VALUE;

    /**
     * Default name {@link String} of the size property to be used as query parameter
     */
    public static final String QUERY_PARAM_SIZE = "size";

    /**
     * Default name {@link String} of the offset property to be used as query parameter
     */
    public static final String QUERY_PARAM_OFFSET = "offset";

    /**
     * The offset {@link Integer} from the collection of the full results where the page should start
     */
    protected int offset;

    /**
     * The size {@link Integer} of the page
     */
    protected int size;

    /**
     * Name {@link String} of the offset property to be used as query parameter
     */
    protected String offsetQueryParamName = QUERY_PARAM_OFFSET;

    /**
     * Name {@link String} of the offset property to be used as query parameter
     */
    protected String sizeQueryParamName = QUERY_PARAM_SIZE;

    private PagingBehaviorUsingOffsetSize() {
    }

    private PagingBehaviorUsingOffsetSize(final String offsetQueryParamName, final String sizeQueryParamName) {
        this.offsetQueryParamName = offsetQueryParamName;
        this.sizeQueryParamName = sizeQueryParamName;
    }

    /**
     * This constructor instantiates a PagingBehaviorUsingOffsetSize and sets the offset and the size query parameters
     * names to the given values. It also checks if the given offset and size values are acceptable
     */
    public PagingBehaviorUsingOffsetSize(final String offsetQueryParamName, final String sizeQueryParamName,
                                         final int offset, final int size) {
        this(offsetQueryParamName, sizeQueryParamName);
        setOffset(offset);
        setSize(size);
    }

    /**
     * This constructor instantiates a PagingBehaviorUsingOffsetSize and checks if the given offset and size
     * values are acceptable and sets the values accordingly
     */
    public PagingBehaviorUsingOffsetSize(final int offset, final int size) {
        setOffset(offset);
        setSize(size);
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    protected boolean hasNextLink(final CollectionModelResult<?> result) {
        return this.offset + this.size < result.getTotalNumberOfResult();
    }

    @Override
    protected URI getNextUri(final UriInfo uriInfo, final CollectionModelResult<?> result) {
        final UriBuilder uriBuilder = createUriBuilder(uriInfo);
        final int newOffset = Math.min(this.offset + this.size, result.getTotalNumberOfResult() - 1);

        return uriBuilder.build(newOffset, this.size);
    }

    @Override
    protected boolean hasPrevLink() {
        return this.offset > 0;
    }

    @Override
    protected URI getPrevUri(final UriInfo uriInfo) {
        final UriBuilder uriBuilder = createUriBuilder(uriInfo);
        final int newOffset = Math.max(this.offset - this.size, 0);

        return uriBuilder.build(newOffset, this.size);
    }

    @Override
    protected URI getSelfUri(final UriInfo uriInfo) {
        final UriBuilder uriBuilder = createUriBuilder(uriInfo);

        return uriBuilder.build(this.offset, this.size);
    }

    /**
     * This method sets the maximum size of the page
     *
     * @return the maximum size {@link Integer} of the page
     */
    protected int getDefaultMaxPageSize() {
        return DEFAULT_MAX_PAGE_SIZE;
    }

    private void setOffset(final int offset) {
        this.offset = Math.max(0, offset);
    }

    private void setSize(final int size) {
        this.size = Math.max(1, Math.min(size, getDefaultMaxPageSize()));
    }

    private UriBuilder createUriBuilder(final UriInfo uriInfo) {
        return uriInfo.getRequestUriBuilder()
                .replaceQueryParam(getOffsetParamName(), getQueryParamOffsetAsTemplate())
                .replaceQueryParam(getSizeParamName(), getQueryParamSizeAsTemplate());
    }

    private String getOffsetParamName() {
        return this.offsetQueryParamName;
    }

    private String getSizeParamName() {
        return this.sizeQueryParamName;
    }

    private final String getQueryParamOffsetAsTemplate() {
        return "{" + getOffsetParamName() + "}";
    }

    private final String getQueryParamSizeAsTemplate() {
        return "{" + getSizeParamName() + "}";
    }

}
