package my.interest.tamil.rest.resources.api;

import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.trans.Transliterator;

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
@Path("api/number/reader")
public class NumberReaderResource {


    @GET
    @Path("/one/")
    @Produces("application/json; charset=UTF-8")
    public String translitGet(@QueryParam("number")String number) throws Exception {
        NumberReader reader = TamilFactory.getNumberReader();
        TamilWord w = reader.readNumber(number);
        JSONObject obj = new JSONObject();
        obj.put("tamil", w.toString());
        return obj.toString();
    }

}
