package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.personLocation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractReadAllRelationsByPrimaryIdOperation;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonLocationDB;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.*;

import java.util.List;

public class LoadPersonLocationByCityName extends AbstractReadAllRelationsByPrimaryIdOperation<PersonDB, LocationDB, PersonLocationDB> {

    private final String cityName;

    public LoadPersonLocationByCityName(EntityManagerFactory emf,
                                        long primaryId,
                                        String cityName,
                                        SearchParameter searchParameter) {
        super(emf, PersonLocationDB.class, primaryId, searchParameter);
        this.cityName = cityName;
    }


    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        final Predicate cityNameEquals = cb.like(cb.lower(from.get("cityName")), "%" + cityName.toLowerCase() + "%");
        return List.of(cityNameEquals);
    }
}
