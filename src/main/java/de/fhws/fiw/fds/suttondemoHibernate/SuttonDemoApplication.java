/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.suttondemoHibernate;

import de.fhws.fiw.fds.sutton.server.api.AbstractApplication;
import de.fhws.fiw.fds.sutton.server.api.security.IAuthenticationProvider;
import de.fhws.fiw.fds.sutton.server.api.security.SuttonAuthenticationProvider;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.services.DispatcherService;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.services.LocationService;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.services.PersonService;
import org.apache.catalina.loader.ParallelWebappClassLoader;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class SuttonDemoApplication extends AbstractApplication {


    @Override
    protected AbstractBinder registerDependencyInjectionBinder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                /*
                 * Configure your DependencyInjection here
                 */
                bind(SuttonAuthenticationProvider.class).to(IAuthenticationProvider.class);
            }
        };
    }

    @Override
    protected Set<Class<?>> getServiceClasses() {
        /*
         * The following two lines solve the problem that the embedded version of Tomcat cannot be started
         * by using class Start. The problem was that JPA is initialized using the system class loader
         * whereas the Web app is loaded by the classloader ParallelWebappClassLoader. The latter one is
         * configured by default not to use delegation, i.e. the ParallelWebappClassloader does know
         * the system class loader as parent but does not use it. Delegation is activated by the following
         * two lines.
         */
        ParallelWebappClassLoader classloader = (ParallelWebappClassLoader) this.getClass().getClassLoader();
        classloader.setDelegate(true);

        final Set<Class<?>> returnValue = new HashSet<>();

        returnValue.add(PersonService.class);
        returnValue.add(LocationService.class);
        returnValue.add(DispatcherService.class);

        return returnValue;
    }

}
