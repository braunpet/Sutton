package de.fhws.fiw.fds.sutton.server.api.states;

import javax.ws.rs.core.Response;

public class DemoStateThrowsIllegalArgumentException extends AbstractState {
    public DemoStateThrowsIllegalArgumentException() {
        super(new AbstractStateBuilder() {
            @Override
            public AbstractState build() {
                return null;
            }
        });
    }

    @Override
    protected Response buildInternal() {
        throw new IllegalArgumentException();
    }

    @Override
    protected Response buildInternalWithRateLimiter() {
        return buildInternal();
    }
}
