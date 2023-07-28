package de.fhws.fiw.fds.sutton.server.api.states;

import de.fhws.fiw.fds.sutton.server.api.security.RequiredPermission;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

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
    protected RequiredPermission getRequiredPermission() {
        return RequiredPermission.TEST;
    }

    @Override
    protected List<String> getAllowedRoles() {
        return Collections.emptyList();
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
