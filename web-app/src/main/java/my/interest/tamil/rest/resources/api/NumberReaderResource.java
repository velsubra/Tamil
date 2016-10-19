package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.impl.FeatureSet;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.number.NumberReaderFeature;

import javax.ws.rs.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/number/reader")
public class NumberReaderResource extends BaseResource {


    @GET
    @Path("/totext/one/")
    @Produces("application/json; charset=UTF-8")
    public String readTextGet(@QueryParam("number") String number, @DefaultValue("") @QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            NumberReader reader = TamilFactory.getNumberReader();
            TamilWord w = reader.readNumber(number, FeatureSet.findFeatures(NumberReaderFeature.class, features).toArray(new NumberReaderFeature[] {}));
            obj.put("tamil", w.toString());
        } catch (Exception e) {
            handle(obj, e);
        }
        return obj.toString();
    }


    @GET
    @Path("/tonumber/one/")
    @Produces("application/json; charset=UTF-8")
    public String readNumberGet(@QueryParam("number") String number, @DefaultValue("") @QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            NumberReader reader = TamilFactory.getNumberReader();
            String w = reader.readAsNumber(number, FeatureSet.findFeatures(NumberReaderFeature.class, features).toArray(new NumberReaderFeature[]{}));
            obj.put("number", w);
        } catch (Exception e) {
            handle(obj, e);
        }
        return obj.toString();
    }


}
