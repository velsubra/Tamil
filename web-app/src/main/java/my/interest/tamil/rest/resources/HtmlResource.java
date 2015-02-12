package my.interest.tamil.rest.resources;

import my.interest.lang.tamil.TamilUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
@Path("/")
public class HtmlResource {

    @GET
    @Path("{name:.*}")
    public Response test(@PathParam("name") String file) throws Exception {
        String type = "application/octet-stream";
        if (file.endsWith(".js")) {
            type = "application/x-javascript";
        } else if (file.endsWith(".html")) {
            type = "text/html";
        }
        else if (file.endsWith(".css")) {
            type = "text/css";
        }
        File f = new File("git/tamil/web-app/src/main/webapp/" + file);
        if (!f.exists()) {
            f = new File("git/tamil/web-app/src/main/webapp/js/" + file);
        }
        System.out.println(f.getAbsolutePath());
        return Response.status(200).type(type).entity(TamilUtils.readAllFrom( new FileInputStream(f), false)).build();
    }
}
