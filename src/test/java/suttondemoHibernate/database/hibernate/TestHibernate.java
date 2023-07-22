package suttondemoHibernate.database.hibernate;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.AbstractAttributeEqualsValue;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao.PersonDaoHibernate;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao.PersonDaoHibernateImpl;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestHibernate extends AbstractHibernateTestHelper{

    @Test
    public void test_db_save_successful() throws Exception {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        CollectionModelHibernateResult<PersonDB> resultGetAll = dao.readAll();
        assertEquals(1, resultGetAll.getResult().size());
    }

    @Test
    public void test_db_load_by_id() throws Exception {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        SingleModelHibernateResult<PersonDB> resultGetById = dao.readById(person.getId());
        assertEquals(person.getEmailAddress(), resultGetById.getResult().getEmailAddress());
    }

    @Test
    public void test_db_delete_by_id() throws Exception {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        NoContentResult resultDelete = dao.delete(person.getId());
        assertFalse(resultDelete.hasError());
    }

    @Test
    public void test_db_update() throws Exception {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        SingleModelHibernateResult<PersonDB> resultGetById = dao.readById(person.getId());
        PersonDB personInDB = resultGetById.getResult();
        personInDB.setFirstName("Jimmy");

        NoContentResult resultUpdate = dao.update(personInDB);
        assertFalse(resultUpdate.hasError());

        SingleModelHibernateResult<PersonDB> resultGetByIdAfterUpdate = dao.readById(person.getId());
        assertEquals("Jimmy", resultGetByIdAfterUpdate.getResult().getFirstName());
    }


    @Test
    public void test_db_load_by_names() throws Exception {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        CollectionModelHibernateResult<PersonDB> resultByNames = dao.readByFirstNameAndLastName("", "Bond", new SearchParameter());
        assertEquals(1, resultByNames.getResult().size());
    }

    @Test
    public void test_db_load_all_with_order_by_SearchParameter_firstName_ASC_lastName_ASC() {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        PersonDB person2 = new PersonDB();
        person2.setFirstName("James");
        person2.setLastName("Alu");
        person2.setBirthDate(LocalDate.of(1964, 3, 18));
        person2.setEmailAddress("jeremy.alu@thws.de");

        NoContentResult resultSave2 = dao.create(person2);

        assertFalse(resultSave2.hasError());

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setOrderByAttributes("asc:firstName,asc:lastName"); //firstName ASC and lastName ASC
        CollectionModelHibernateResult<PersonDB> result = dao.readAll(searchParameter);
        assertEquals(2, result.getResult().size());

        // Check if the list is sorted correctly.
        List<PersonDB> personList = new ArrayList<>(result.getResult());
        assertEquals("James", personList.get(1).getFirstName());
        assertEquals("Bond", personList.get(1).getLastName());
        assertEquals("James", personList.get(0).getFirstName());
        assertEquals("Alu", personList.get(0).getLastName());
    }

    @Test
    public void test_db_load_all_with_order_by_SearchParameter_lastName_ASC() {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        PersonDB person2 = new PersonDB();
        person2.setFirstName("Jeremy");
        person2.setLastName("Alu");
        person2.setBirthDate(LocalDate.of(1964, 3, 18));
        person2.setEmailAddress("jeremy.alu@thws.de");

        NoContentResult resultSave2 = dao.create(person2);

        assertFalse(resultSave2.hasError());

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setOrderByAttributes("asc:lastName");
        CollectionModelHibernateResult<PersonDB> result = dao.readAll(searchParameter);
        assertEquals(2, result.getResult().size());

        // Check if the list is sorted correctly.
        List<PersonDB> personList = new ArrayList<>(result.getResult());
        assertEquals("James", personList.get(1).getFirstName());
        assertEquals("Bond", personList.get(1).getLastName());
        assertEquals("Jeremy", personList.get(0).getFirstName());
        assertEquals("Alu", personList.get(0).getLastName());
    }

    @Test
    public void test_db_load_all_with_order_by_SearchParameter_lastName_DESC() {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        PersonDB person2 = new PersonDB();
        person2.setFirstName("Jeremy");
        person2.setLastName("Alu");
        person2.setBirthDate(LocalDate.of(1964, 3, 18));
        person2.setEmailAddress("jeremy.alu@thws.de");

        NoContentResult resultSave2 = dao.create(person2);

        assertFalse(resultSave2.hasError());

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setOrderByAttributes("desc:lastName");
        CollectionModelHibernateResult<PersonDB> result = dao.readAll(searchParameter);
        assertEquals(2, result.getResult().size());

        // Check if the list is sorted correctly.
        List<PersonDB> personList = new ArrayList<>(result.getResult());
        assertEquals("James", personList.get(0).getFirstName());
        assertEquals("Bond", personList.get(0).getLastName());
        assertEquals("Jeremy", personList.get(1).getFirstName());
        assertEquals("Alu", personList.get(1).getLastName());
    }

    @Test
    public void test_db_load_all_with_order_by_SearchParameter_firstName_DESC() {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        PersonDB person2 = new PersonDB();
        person2.setFirstName("Jeremy");
        person2.setLastName("Alu");
        person2.setBirthDate(LocalDate.of(1964, 3, 18));
        person2.setEmailAddress("jeremy.alu@thws.de");

        NoContentResult resultSave2 = dao.create(person2);

        assertFalse(resultSave2.hasError());

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setOrderByAttributes("desc:firstName");
        CollectionModelHibernateResult<PersonDB> result = dao.readAll(searchParameter);
        assertEquals(2, result.getResult().size());

        // Check if the list is sorted correctly.
        List<PersonDB> personList = new ArrayList<>(result.getResult());
        assertEquals("James", personList.get(1).getFirstName());
        assertEquals("Bond", personList.get(1).getLastName());
        assertEquals("Jeremy", personList.get(0).getFirstName());
        assertEquals("Alu", personList.get(0).getLastName());
    }

    @Test
    public void test_db_load_all_with_order_by_SearchParameter_birthDate_DESC() {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        PersonDB person2 = new PersonDB();
        person2.setFirstName("Jeremy");
        person2.setLastName("Alu");
        person2.setBirthDate(LocalDate.of(1964, 3, 18));
        person2.setEmailAddress("jeremy.alu@thws.de");

        NoContentResult resultSave2 = dao.create(person2);

        assertFalse(resultSave2.hasError());

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setOrderByAttributes("desc:birthDate");
        CollectionModelHibernateResult<PersonDB> result = dao.readAll(searchParameter);
        assertEquals(2, result.getResult().size());

        // Check if the list is sorted correctly.
        List<PersonDB> personList = new ArrayList<>(result.getResult());
        assertEquals("James", personList.get(1).getFirstName());
        assertEquals("Bond", personList.get(1).getLastName());
        assertEquals("Jeremy", personList.get(0).getFirstName());
        assertEquals("Alu", personList.get(0).getLastName());
    }

    @Test
    public void test_db_load_all_with_attribute_equal_value_in_SearchParameter() {
        PersonDB person = new PersonDB();
        person.setFirstName("James");
        person.setLastName("Bond");
        person.setBirthDate(LocalDate.of(1948, 7, 7));
        person.setEmailAddress("james.bond@thws.de");

        PersonDaoHibernate dao = new PersonDaoHibernateImpl();
        NoContentResult resultSave = dao.create(person);

        assertFalse(resultSave.hasError());

        PersonDB person2 = new PersonDB();
        person2.setFirstName("Jeremy");
        person2.setLastName("Alu");
        person2.setBirthDate(LocalDate.of(1964, 3, 18));
        person2.setEmailAddress("jeremy.alu@thws.de");

        NoContentResult resultSave2 = dao.create(person2);

        assertFalse(resultSave2.hasError());

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.addAttributeEqualValue(new AbstractAttributeEqualsValue<String>("firstName", "James") {});
        CollectionModelHibernateResult<PersonDB> result = dao.readAll(searchParameter);
        assertEquals(1, result.getTotalNumberOfResult());
        assertEquals("James", result.getResult().stream().toList().get(0).getFirstName());
    }
}
