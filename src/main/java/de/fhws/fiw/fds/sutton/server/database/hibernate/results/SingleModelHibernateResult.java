package de.fhws.fiw.fds.sutton.server.database.hibernate.results;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;

public class SingleModelHibernateResult<T extends AbstractDBModel> extends AbstractResult {

    protected T result;

    protected boolean found;

    public SingleModelHibernateResult() {
        this.found = false;
    }

    public SingleModelHibernateResult(final T result) {
        this.result = result;
        this.found = result != null;
    }

    public T getResult() {
        return this.result;
    }

    public boolean isEmpty() {
        return !this.found;
    }

    public static class SingleModelHibernateResultBuilder<T extends AbstractDBModel> extends AbstractResultBuilder<SingleModelHibernateResult<T>>{
        private T result;
        private boolean found;

        public SingleModelHibernateResultBuilder<T> setResult(T result) {
            this.result = result;
            this.found = result != null;
            return this;
        }

        @Override
        public SingleModelHibernateResult<T> build() {
            SingleModelHibernateResult<T> singleModelHibernateResult = new SingleModelHibernateResult<>();
            singleModelHibernateResult.result = this.result;
            singleModelHibernateResult.found = this.found;
            singleModelHibernateResult.hasError = this.hasError;
            singleModelHibernateResult.errorCode = this.errorCode;
            singleModelHibernateResult.errorMessage = this.errorMessage;
            singleModelHibernateResult.databaseExecutionTimeInMs = this.databaseExecutionTimeInMs;
            return singleModelHibernateResult;
        }
    }
}
