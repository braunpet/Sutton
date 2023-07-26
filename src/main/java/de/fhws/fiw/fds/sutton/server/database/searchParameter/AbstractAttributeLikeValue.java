package de.fhws.fiw.fds.sutton.server.database.searchParameter;

public abstract class AbstractAttributeLikeValue extends AbstractAttributeEqualsValue<String>{

    public AbstractAttributeLikeValue(String searchByAttribute, String likeValue) {
        super(searchByAttribute, likeValue);
    }

}
