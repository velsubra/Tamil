package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

@Path("api/browse/")
public class FileAccessResource extends BaseResource {

    @GET
    @Path("{name:.*}")
    public Response readFile(@PathParam("name") String file) throws Exception {
        try {
            InputStream in = null;
            String type = "application/octet-stream";
            in = new FileInputStream(new File(PersistenceInterface.getWorkDir(), file));
            try {
                return Response.status(200).type(type).entity(TamilUtils.readAllFrom(in, false)).build();
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
            JSONObject obj = new JSONObject();
            handle(obj, e);
            return Response.status(500).type("application/json").entity(obj.toString()).build();
        }
    }
}
