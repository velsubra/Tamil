package my.interest.tamil.rest.resources.apps;

import my.interest.lang.tamil.internal.api.PersistenceInterface;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

@Path("apps")
public class AppsAccessResource {

    @GET
    @Path("/")
    @Produces("application/json; charset=UTF-8")
    public String list() throws Exception {
        List<String> ret = PersistenceInterface.get().getAppContexts();
        JSONObject obj = new JSONObject();
        obj.put("results", ret);
        return obj.toString();
    }


    @Path("/resources/{context}")

    public AppAccessResource find(@PathParam("context") String context) throws Exception {
        return new AppAccessResource(PersistenceInterface.get().findAppWithContext(context));
    }
}
