package de.fhws.fiw.fds.sutton.server.api.security.database.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class RoleDB extends AbstractDBModel {

    @Column(nullable = false)
    private String roleName;

    private boolean createPermission = false;

    private boolean readPermission = false;

    private boolean updatePermission = false;

    private boolean deletePermission = false;

    protected RoleDB() {
        // make JPA happy
    }

    public RoleDB(String roleName) {
        this.roleName = roleName;
    }

    public RoleDB(String roleName, boolean createPermission, boolean readPermission, boolean updatePermission, boolean deletePermission) {
        this.roleName = roleName;
        this.createPermission = createPermission;
        this.readPermission = readPermission;
        this.updatePermission = updatePermission;
        this.deletePermission = deletePermission;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCreatePermission() {
        return createPermission;
    }

    public void setCreatePermission(boolean createPermission) {
        this.createPermission = createPermission;
    }

    public boolean isReadPermission() {
        return readPermission;
    }

    public void setReadPermission(boolean readPermission) {
        this.readPermission = readPermission;
    }

    public boolean isUpdatePermission() {
        return updatePermission;
    }

    public void setUpdatePermission(boolean updatePermission) {
        this.updatePermission = updatePermission;
    }

    public boolean isDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(boolean deletePermission) {
        this.deletePermission = deletePermission;
    }
}
