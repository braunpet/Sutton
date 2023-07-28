package de.fhws.fiw.fds.sutton.server;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Use this Interface for every connection to your DataBase
 */
public interface IDatabaseConnection {

    /**
     * The {@link EntityManagerFactory} for your Database.
     * The String needs to be equal to the persistence-unit name in persistence.xml
     */
    EntityManagerFactory SUTTON_EMF = //
            Persistence.createEntityManagerFactory("de.fhws.fiw.fds.sutton");

}
