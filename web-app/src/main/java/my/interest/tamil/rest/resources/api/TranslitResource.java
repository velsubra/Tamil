package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.impl.FeatureSet;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.number.ReaderFeature;
import tamil.lang.api.trans.TranslitFeature;
import tamil.lang.api.trans.Transliterator;

import javax.ws.rs.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/translit")
public class TranslitResource extends BaseResource {


    @GET
    @Path("/one/")
    @Produces("application/json; charset=UTF-8")
    public String translitGet(@QueryParam("word") String english,@QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            Transliterator transliterator = TamilFactory.getTransliterator(null);
            TamilWord w = transliterator.transliterate(english, FeatureSet.findFeatures(TranslitFeature.class, features).toArray(new TranslitFeature[] {}));


            obj.put("tamil", w.toString());
        } catch (Exception e) {
            handle(obj, e);
        }
        return obj.toString();

    }


    @PUT
    @Path("/one/")
    @Produces("application/json; charset=UTF-8")
    public String translit(String english, @QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            Transliterator transliterator = TamilFactory.getTransliterator(null);
            TamilWord w = transliterator.transliterate(english, FeatureSet.findFeatures(TranslitFeature.class, features).toArray(new TranslitFeature[] {}));


            obj.put("tamil", w.toString());
        } catch (Exception e) {
            handle(obj, e);
        }
        return obj.toString();

    }
}
