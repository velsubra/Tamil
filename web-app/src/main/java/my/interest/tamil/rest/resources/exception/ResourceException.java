package my.interest.tamil.rest.resources.exception;

import javax.ws.rs.WebApplicationException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ResourceException extends RuntimeException {
    public ResourceException(String message) {
        super(message);
    }
}
