package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.dao.IDatabaseRelationAccessObjectHibernate;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;

public interface PersonLocationDaoHibernate extends IDatabaseRelationAccessObjectHibernate<LocationDB> {

    CollectionModelHibernateResult<LocationDB> readByCityName(long primaryId, String cityName, SearchParameter searchParameter);

}
