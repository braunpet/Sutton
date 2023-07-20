package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.personLocation;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOrderByOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonLocationDB;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.stream.Collectors;

public class LoadPersonLocationByCityName extends AbstractDatabaseOrderByOperation<LocationDB, CollectionModelHibernateResult<LocationDB>, Join<PersonLocationDB, LocationDB>> {


    private final Class<PersonLocationDB> clazzOfRelation = PersonLocationDB.class;
    private final long primaryId;
    private final String cityName;
    private final SearchParameter searchParameter;

    public LoadPersonLocationByCityName(EntityManagerFactory emf,
                                        long primaryId,
                                        String cityName,
                                        SearchParameter searchParameter) {
        super(emf);
        this.primaryId = primaryId;
        this.cityName = cityName;
        this.searchParameter = searchParameter;
    }

    @Override
    protected CollectionModelHibernateResult<LocationDB> run() {
        var returnValue = new CollectionModelHibernateResult<>(readResult());
        returnValue.setTotalNumberOfResult(getTotalNumberOfResults());
        return returnValue;
    }

    @Override
    protected CollectionModelHibernateResult<LocationDB> errorResult() {
        final CollectionModelHibernateResult<LocationDB> returnValue = new CollectionModelHibernateResult<>();
        returnValue.setError();
        return returnValue;
    }

    private List<LocationDB> readResult() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PersonLocationDB> find = cb.createQuery(this.clazzOfRelation);
        Root<PersonLocationDB> rootEntry = find.from(this.clazzOfRelation);
        Join<PersonLocationDB, LocationDB> join = rootEntry.join(SuttonColumnConstants.SECONDARY_MODEL);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);
        Predicate cityNameEquals = cb.like(cb.lower(join.get("cityName")), "%" + cityName.toLowerCase() + "%");
        find.where(primaryIdEquals, cityNameEquals)
                .orderBy(getOrderFromSearchParameter(cb, join, this.searchParameter));
        TypedQuery<PersonLocationDB> findQuery = em.createQuery(find);
        return findQuery
                .setHint("org.hibernate.cacheable", true)
                .setFirstResult(this.searchParameter.getOffset())
                .setMaxResults(this.searchParameter.getSize())
                .getResultList()
                .stream()
                .map(r -> (LocationDB) r.getSecondaryModel())
                .collect(Collectors.toList());
    }

    private int getTotalNumberOfResults() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> find = cb.createQuery(Long.class);
        Root<PersonLocationDB> rootEntry = find.from(this.clazzOfRelation);
        Join<PersonLocationDB, LocationDB> join = rootEntry.join(SuttonColumnConstants.SECONDARY_MODEL);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);
        Predicate cityNameEquals = cb.like(cb.lower(join.get("cityName")), "%" + cityName.toLowerCase() + "%");
        find.select(cb.count(rootEntry)).where(primaryIdEquals, cityNameEquals);

        return this.em.createQuery(find)
                .setHint("org.hibernate.cacheable", true)
                .getSingleResult().intValue();
    }

}
