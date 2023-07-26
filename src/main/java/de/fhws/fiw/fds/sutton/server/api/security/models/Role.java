package de.fhws.fiw.fds.sutton.server.api.security.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.XmlServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Role extends AbstractModel {

    private String roleName;

    private boolean createPermission = false;

    private boolean readPermission = false;

    private boolean updatePermission = false;

    private boolean deletePermission = false;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "/users/${instance.primaryId}/roles/${instance.id}",
            rel = "self",
            title = "self",
            type = "application/json",
            condition = "${instance.primaryId != 0}"
    )
    @XmlJavaTypeAdapter(XmlServerLinkConverter.class)
    private Link selfLinkOnSecond;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "/roles/${instance.id}",
            rel = "self",
            title = "self",
            type = "application/json",
            condition = "${instance.primaryId == 0}"
    )
    @XmlJavaTypeAdapter(XmlServerLinkConverter.class)
    private Link selfLinkPrimary;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, boolean createPermission, boolean readPermission, boolean updatePermission, boolean deletePermission) {
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


    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLinkOnSecond() {
        return selfLinkOnSecond;
    }

    // TODO need this?
//    public void setSelfLinkOnSecond(final Link selfLinkOnSecond) {
//        this.selfLinkOnSecond = selfLinkOnSecond;
//    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLinkPrimary() {
        return selfLinkPrimary;
    }

//    public void setSelfLinkPrimary(final Link selfLinkPrimary) {
//        this.selfLinkPrimary = selfLinkPrimary;
//    }
}
