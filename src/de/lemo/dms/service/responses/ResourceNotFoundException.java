package de.lemo.dms.service.responses;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

public class ResourceNotFoundException extends WebApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a HTTP 404 (Not Found) exception.
     */
    public ResourceNotFoundException() {
        super(Status.NOT_FOUND);
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     * 
     * @param message
     *            the String that is the entity of the 404 response.
     */
    public ResourceNotFoundException(String message) {
        super(Response.status(Status.NOT_FOUND).entity(message).type("text/plain").build());
    }

}