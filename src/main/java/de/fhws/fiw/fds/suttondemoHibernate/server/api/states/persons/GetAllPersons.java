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

package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.persons;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Person;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.queries.QueryByFirstAndLastName;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.USER_ROLES;


public class GetAllPersons extends AbstractGetCollectionState<Person> {

    public GetAllPersons(final Builder builder) {
        super(builder);
    }

    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<Person>>(this.result.getResult()) {
        });
    }

    @Override
    protected void authorizeRequest() {
        if(this.query instanceof QueryByFirstAndLastName){
            QueryByFirstAndLastName theQuery = (QueryByFirstAndLastName) this.query;
            int waitingTime = theQuery.getWaitingTime();

            try {
                Thread.sleep(waitingTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PersonUri.REL_PATH, PersonRelTypes.CREATE_PERSON, getAcceptRequestHeader());
    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractGetCollectionStateBuilder<Person> {

        @Override
        public AbstractState build() {
            return new GetAllPersons(this);
        }
    }
}
