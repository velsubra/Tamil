package my.interest.tamil.rest.resources;

import my.interest.lang.tamil.bean.DigestBeans;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("/digest")
public class DigestResource {

    @GET
    @Path("/")
    @Produces("application/json; charset=UTF-8")
    public DigestBeans getList(@QueryParam("names") String list) throws Exception {
        if (list == null) {
            list = "";
        }
        return DigestBeans.getDigestBean(list);

    }

}
