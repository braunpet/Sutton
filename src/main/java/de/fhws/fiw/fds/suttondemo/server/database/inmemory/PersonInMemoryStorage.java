/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.suttondemo.server.database.inmemory;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.database.PersonDao;
import de.fhws.fiw.fds.suttondemo.server.models.Person;

public class PersonInMemoryStorage extends AbstractInMemoryStorage<Person> implements PersonDao {
	public PersonInMemoryStorage() {
		super();
		populateData();
	}

	@Override
	public CollectionModelResult<Person> readByFirstNameAndLastName(final String firstName,
			final String lastName, SearchParameter searchParameter) {
		return readByPredicate(byFirstNameAndLastName(firstName, lastName), searchParameter);
	}

	public void resetDatabase() {
		super.deleteAll();
	}

	private void populateData() {
		create(new Person("Felix", "Leiter", "felix.leiter@cia.com", LocalDate.of(1960, 2, 9)));
	}

	private Predicate<Person> byFirstNameAndLastName(final String firstName,
			final String lastName) {
		return p -> matchFirstName(p, firstName) && matchLastName(p, lastName);
	}

	private boolean matchFirstName(final Person person, final String firstName) {
		return StringUtils.isEmpty(firstName) || person.getFirstName().equals(firstName);
	}

	private boolean matchLastName(final Person person, final String lastName) {
		return StringUtils.isEmpty(lastName) || person.getLastName().equals(lastName);
	}
}
