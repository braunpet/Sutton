package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractDeleteAllOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

public class DeleteAllBinaryDataByMediaTypeOperation extends AbstractDeleteAllOperation<BinaryDataDBModel> {

    private String mediaType;

    public DeleteAllBinaryDataByMediaTypeOperation(EntityManagerFactory emf, String mediaType) {
        super(emf, BinaryDataDBModel.class);
        this.mediaType = mediaType;
    }

    @Override
    protected NoContentResult run() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<BinaryDataDBModel> delete = cb.createCriteriaDelete(BinaryDataDBModel.class);
        Root<BinaryDataDBModel> root = delete.from(BinaryDataDBModel.class);

        delete.where(cb.equal(root.get("mediaType"), mediaType));
        em.createQuery(delete).executeUpdate();

        return new NoContentResult();
    }
}
