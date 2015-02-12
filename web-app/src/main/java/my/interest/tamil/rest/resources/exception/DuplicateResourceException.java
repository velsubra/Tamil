package my.interest.tamil.rest.resources.exception;

import javax.ws.rs.WebApplicationException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class DuplicateResourceException extends WebApplicationException {
    public DuplicateResourceException() {
        super(409);
    }
}
