package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.impl.FeatureSet;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.number.ReaderFeature;

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
public class NumberReaderResource extends BaseResource {


    @GET
    @Path("/one/")
    @Produces("application/json; charset=UTF-8")
    public String readGet(@QueryParam("number") String number,@QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            NumberReader reader = TamilFactory.getNumberReader();
            TamilWord w = reader.readNumber(number, FeatureSet.findFeatures(ReaderFeature.class, features).toArray(new ReaderFeature[] {}));
            obj.put("tamil", w.toString());
        } catch (Exception e) {
            handle(obj, e);
        }
        return obj.toString();
    }

}
