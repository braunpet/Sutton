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

    private String name;

    private boolean canCreate;

    private boolean canRead;

    private boolean canUpdate;

    private boolean canDelete;

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

    public Role() {

    }

    public Role(String name, boolean canCreate, boolean canRead, boolean canUpdate, boolean canDelete) {
        this.name = name;
        this.canCreate = canCreate;
        this.canRead = canRead;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanCreate() {
        return canCreate;
    }

    public void setCanCreate(boolean canCreate) {
        this.canCreate = canCreate;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
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
