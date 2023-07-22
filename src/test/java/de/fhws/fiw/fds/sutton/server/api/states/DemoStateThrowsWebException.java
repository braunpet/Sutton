package de.fhws.fiw.fds.sutton.server.api.states;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DemoStateThrowsWebException extends AbstractState {
    public DemoStateThrowsWebException() {
        super(new AbstractStateBuilder() {
            @Override
            public AbstractState build() {
                return null;
            }
        });
    }

    @Override
    protected Response buildInternal() {
        throw new WebApplicationException();
    }

    @Override
    protected Response buildInternalWithRateLimiter() {
        return buildInternal();
    }
}
