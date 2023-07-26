package de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role;

public interface UserRoleRelTypes {

    String CREATE_ROLE = "createRoleOfUser";
    String GET_ALL_LINKED_ROLES = "getAllRolesOfUser";
    String GET_ALL_ROLES = "getAllLinkableRoles";
    String UPDATE_SINGLE_ROLE = "updateRoleOfUser";
    String CREATE_LINK_FROM_USER_TO_ROLE = "linkUserToRole";
    String DELETE_LINK_FROM_USER_TO_ROLE = "unlinkUserToRole";
    String GET_SINGLE_ROLE = "getRoleOfUser";

}
