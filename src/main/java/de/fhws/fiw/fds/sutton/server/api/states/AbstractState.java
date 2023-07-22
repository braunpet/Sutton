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

package de.fhws.fiw.fds.sutton.server.api.states;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Hyperlinks;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.RateLimiter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * <p>The AbstractState class defines the basic requirements each extending state class needs to define a proper workflow.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend {@link AbstractState.AbstractStateBuilder}.</p>
 */
public abstract class AbstractState {

    protected UriInfo uriInfo;

    protected HttpServletRequest httpServletRequest;

    protected Request request;

    protected ContainerRequestContext context;

    protected Response.ResponseBuilder responseBuilder;

    private RateLimiter rateLimiter;

    /**
     * This constructor instantiates an instance of the AbstractState class using the builder pattern
     */
    protected AbstractState(final AbstractStateBuilder builder) {
        this.uriInfo = builder.uriInfo;
        this.httpServletRequest = builder.httpServletRequest;
        this.request = builder.request;
        this.context = builder.context;
        this.rateLimiter = builder.rateLimiter != null ? builder.rateLimiter : RateLimiter.DEFAULT;
        this.responseBuilder = Response.ok();
    }

    /**
     * This is the main method to start execution of this state implementation.
     *
     * @return the response sent back to the client
     */
    public final Response execute() {
        try {
            return buildInternalWithRateLimiter();
        } catch (final WebApplicationException f) {
            throw f;
        } catch (final Exception e) {
            e.printStackTrace();

            return this.responseBuilder.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    /**
     * This adds the {@link RateLimiter}-Logic and checks if the request is allowed.
     *
     * @return the response sent back to the client
     */
    protected Response buildInternalWithRateLimiter() {
        String apiKey = getApiKeyFromRequest();
        if (rateLimiter.isRequestAllowed(apiKey)) {
            return buildInternal();
        } else {
            throw new WebApplicationException(Response.status(Response.Status.TOO_MANY_REQUESTS)
                    .entity("Rate limit exceeded for API key: " + apiKey)
                    .build());
        }
    }

    /**
     * This reads the API-Key from the header of the {@link HttpServletRequest}.
     *
     * @return the API-Key String of the header
     */
    private String getApiKeyFromRequest() {
        return httpServletRequest.getHeader("API-Key");
    }

    /**
     * Extended states must implement this method which contains the main work flow depending on the state.
     *
     * @return the response sent back to the client
     */
    protected abstract Response buildInternal();

    /**
     * This method can be used to configure the state. If extended classes implement the work flow
     * in different ways, this method would be the correct place to decide. For example, the PUT state
     * could be implemented so that it returns always status code 200 with the resource OR to return status
     * code 204 without the resource.
     */
    protected void configureState() {

    }

    /**
     * Add a link to the response builder. This method should be called by sub-classes during
     * processing of the request, for example as part of method {@link #buildInternal()}.
     *
     * @param uriTemplate a template of an absolute URI
     * @param relType     the relation type of this link
     * @param params      parameters that are replaced in the given template
     */
    protected final void addLink(final String uriTemplate,
                                 final String relType,
                                 final String mediaType,
                                 final Object... params) {
        Hyperlinks.addLink(this.uriInfo, this.responseBuilder, uriTemplate, relType, mediaType, params);
    }


    /**
     * Creates hyperlinks as specified in the specifications of the REST architecture and adds them to the response as
     * headers
     *
     * @param uriTemplate {@link String} the uri template to be used to build the hyperlink in the response
     * @param relType     {@link String} describes what the hyperlink stands for
     * @param params      an ellipsis of {@link Object} to be built to the href part of the hyperlink
     */
    protected final void addLink(final String uriTemplate, final String relType, final Object... params) {
        Hyperlinks.addLink(this.uriInfo, this.responseBuilder, uriTemplate, relType, null, params);
    }

    /**
     * Returns the value of the Accept header from the request
     *
     * @return the value {@link String} of the Accept header from the request
     */
    protected final String getAcceptRequestHeader() {
        return getRequestHeader("Accept");
    }

    /**
     * @param headerName {@link String} name of the header in the request to return its value
     * @return the value {@link String} of the provided header from the request
     */
    protected final String getRequestHeader(final String headerName) {
        return this.httpServletRequest.getHeader(headerName);
    }

    /**
     * this inner static abstract class is used in the context of the builder pattern in order to
     * instantiate state classes
     */
    public static abstract class AbstractStateBuilder {
        protected UriInfo uriInfo;

        protected HttpServletRequest httpServletRequest;

        protected Request request;

        protected ContainerRequestContext context;

        protected RateLimiter rateLimiter;

        public AbstractStateBuilder setUriInfo(final UriInfo uriInfo) {
            this.uriInfo = uriInfo;
            return this;
        }

        public AbstractStateBuilder setHttpServletRequest(final HttpServletRequest httpServletRequest) {
            this.httpServletRequest = httpServletRequest;
            return this;
        }

        public AbstractStateBuilder setRequest(final Request request) {
            this.request = request;
            return this;
        }

        public AbstractStateBuilder setContext(final ContainerRequestContext context) {
            this.context = context;
            return this;
        }

        public AbstractStateBuilder setRateLimiter(final RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
            return this;
        }

        /**
         * This method is used to return a state class, which extends {@link AbstractState}
         */
        public abstract AbstractState build();
    }

}
