package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.personLocation;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractReadAllRelationsByPrimaryIdOperation;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonLocationDB;
import jakarta.persistence.EntityManagerFactory;


public class LoadAllPersonLocationsOperation extends AbstractReadAllRelationsByPrimaryIdOperation<PersonDB, LocationDB, PersonLocationDB> {

    public LoadAllPersonLocationsOperation(EntityManagerFactory emf, long primaryId, SearchParameter searchParameter) {
        super(emf, PersonLocationDB.class, primaryId, searchParameter);
    }

}
