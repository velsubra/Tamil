package my.interest.tamil.rest.resources.apps;

import my.interest.lang.tamil.generated.types.AppDescription;
import my.interest.lang.tamil.generated.types.AppResource;
import my.interest.lang.tamil.generated.types.AppResourceType;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.tamil.rest.resources.exception.ResourceException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
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

//
//    @Path("/generate/js/{context}/{name:.*}")
//    //@Consumes({"application/javascript","text/javascript","application/x-javascript"})
//    @GET
//    public Response generateJavaScript(@PathParam("context") String context, @PathParam("name") String name, @Context HttpServletRequest httpRequest) throws Exception {
//
//        try {
//            AppDescription app = PersistenceInterface.get().findAppWithContext(context);
//            if (app == null) {
//                return Response.status(404).build();
//            }
//            final AppResource resource = PersistenceInterface.get().findAppResource(app.getName(), name,false);
//            if (resource == null) {
//                return Response.status(404).build();
//            }
//            StringBuffer buffer = new StringBuffer();
//
//            return  Response.status(200).type("text/javascript; charset=UTF-8").entity(buffer.toString()).build();
//        } catch (WebApplicationException wa) {
//            throw wa;
//        } catch (ResourceException re) {
//            throw re;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ResourceException(e.getMessage());
//        }
//    }
}
