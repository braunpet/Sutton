package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.person;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadAllOperation;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import jakarta.persistence.EntityManagerFactory;

public class LoadAllPersonsOperations extends AbstractReadAllOperation<PersonDB> {

    public LoadAllPersonsOperations(EntityManagerFactory emf, SearchParameter searchParameter) {
        super(emf, PersonDB.class, searchParameter);
    }

}
