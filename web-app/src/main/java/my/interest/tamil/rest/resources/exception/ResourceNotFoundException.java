package my.interest.tamil.rest.resources.exception;

import javax.ws.rs.WebApplicationException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ResourceNotFoundException extends WebApplicationException {
    public ResourceNotFoundException() {
        super(404);
    }
}
