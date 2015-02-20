package my.interest.tamil.rest.resources;

import my.interest.lang.tamil.TamilUtils;

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
@Path("/")
public class HtmlResource {

    @GET
    @Path("{name:.*}")
    public Response test(@PathParam("name") String file) throws Exception {
        InputStream in = null;
        String type = "application/octet-stream";
        if (file.equals("tamil-letter-1.0.jar")) {
            type = "application/octet-stream";
            in = new FileInputStream("/Users/velsubra/Desktop/tamil/github/Tamil/ezhuththu/target/tamil-letter-1.0.jar");
        } else if (file.endsWith(".js")) {
            type = "application/x-javascript";
        } else if (file.endsWith(".html")) {
            type = "text/html";
        } else if (file.endsWith(".css")) {
            type = "text/css";
        }
        if (in == null) {
            File f = new File("src/main/webapp/" + file);
            if (!f.exists()) {
                f = new File("src/main/webapp/js/" + file);
            }
            System.out.println(f.getAbsolutePath());
            in = new FileInputStream(f);
        }
        return Response.status(200).type(type).entity(TamilUtils.readAllFrom(in, false)).build();
    }
}
