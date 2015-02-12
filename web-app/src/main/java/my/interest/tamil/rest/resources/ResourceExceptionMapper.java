package my.interest.tamil.rest.resources;

import my.interest.tamil.rest.resources.exception.ResourceException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

@Provider
public class ResourceExceptionMapper implements ExceptionMapper<ResourceException> {

    @Override
    public Response toResponse(ResourceException serviceException) {

        JSONObject obj = new JSONObject();
        try {
            //obj.p
            obj.put("message", serviceException.getMessage());
        } catch (JSONException e) {
            throw new WebApplicationException(e, 500);
        }
        return Response.status(500).type("application/json; charset=UTF-8").entity(obj.toString()).build();
    }
}
