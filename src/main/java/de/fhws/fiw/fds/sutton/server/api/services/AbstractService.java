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

package de.fhws.fiw.fds.sutton.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.security.IAuthenticationProvider;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

public abstract class AbstractService {

    @Context
    protected ContainerRequestContext context;

    @Context
    protected UriInfo uriInfo;

    @Context
    protected ContainerRequestContext requestContext;

    @Context
    protected Request request;

    @Context
    protected HttpServletRequest httpServletRequest;

    @Inject
    protected IAuthenticationProvider authProvider;

}
