package my.interest.tamil.rest.resources.api;

import org.codehaus.jettison.json.JSONException;
import org.json.JSONObject;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class BaseResource {


    protected void handle(JSONObject obj, Exception e) throws org.json.JSONException {
        obj.put("error", "true");
        obj.put("emessage", e.getMessage());
    }
}
