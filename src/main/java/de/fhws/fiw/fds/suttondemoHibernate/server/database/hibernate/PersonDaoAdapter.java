package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.PersonDao;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao.PersonDaoHibernate;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao.PersonDaoHibernateImpl;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.datafaker.PersonDataFaker;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Person;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is the adapter between the business logic layer and the database layer.
 * <p>
 * The business logic must use this class to access the database and it can use its own
 * model classes. This class takes care of translating models from the business logic into
 * models of the database layer and vice-versa.
 */
public class PersonDaoAdapter implements PersonDao {

    private PersonDaoHibernate dao = new PersonDaoHibernateImpl();
    private final PersonDataFaker faker = new PersonDataFaker();

    @Override
    public NoContentResult create(Person model) {
        final PersonDB dbModel = createFrom(model);
        final NoContentResult returnValue = this.dao.create(dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public SingleModelResult<Person> readById(long id) {
        return createResult(this.dao.readById(id));
    }

    @Override
    public CollectionModelResult<Person> readAll() {
        return this.readAll(new SearchParameter());
    }

    @Override
    public CollectionModelResult<Person> readAll(SearchParameter searchParameter) {
        return createResult(this.dao.readAll(searchParameter));
    }

    @Override
    public NoContentResult update(Person model) {
        return this.dao.update(createFrom(model));
    }

    @Override
    public CollectionModelResult<Person> readByFirstNameAndLastName(String firstName, String lastName,
                                                                    SearchParameter searchParameter) {
        return createResult(this.dao.readByFirstNameAndLastName(firstName, lastName, searchParameter));
    }

    @Override
    public NoContentResult delete(long id) {
        return this.dao.delete(id);
    }

    private Collection<Person> createFrom(Collection<PersonDB> models) {
        return models.stream().map(m -> createFrom(m)).collect(Collectors.toList());
    }

    private Person createFrom(PersonDB model) {
        final Person returnValue = new Person();
        returnValue.setId(model.getId());
        returnValue.setBirthDate(model.getBirthDate());
        returnValue.setFirstName(model.getFirstName());
        returnValue.setLastName(model.getLastName());
        returnValue.setEmailAddress(model.getEmailAddress());
        return returnValue;
    }

    private PersonDB createFrom(Person model) {
        final PersonDB returnValue = new PersonDB();
        returnValue.setId(model.getId());
        returnValue.setBirthDate(model.getBirthDate());
        returnValue.setFirstName(model.getFirstName());
        returnValue.setLastName(model.getLastName());
        returnValue.setEmailAddress(model.getEmailAddress());
        return returnValue;
    }

    private SingleModelResult<Person> createResult(SingleModelHibernateResult<PersonDB> result) {
        if (result.isEmpty()) {
            return new SingleModelResult<>();
        }
        if (result.hasError()) {
            final SingleModelResult<Person> returnValue = new SingleModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            return new SingleModelResult<>(createFrom(result.getResult()));
        }
    }

    private CollectionModelResult<Person> createResult(CollectionModelHibernateResult<PersonDB> result) {
        if (result.hasError()) {
            final CollectionModelResult<Person> returnValue = new CollectionModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            final CollectionModelResult returnValue = new CollectionModelResult<>(createFrom(result.getResult()));
            returnValue.setTotalNumberOfResult(result.getTotalNumberOfResult());
            return returnValue;
        }
    }

    private void populateDatabase() {
        IntStream.range(0, 2).forEach(i -> this.dao.create(createPerson()));
    }

    private PersonDB createPerson() {
        return this.faker.createPerson();
    }
}
