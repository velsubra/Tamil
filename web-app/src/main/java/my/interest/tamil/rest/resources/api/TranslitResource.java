package my.interest.tamil.rest.resources.api;

import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.trans.Transliterator;

import javax.ws.rs.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/translit")
public class TranslitResource {



    @GET
    @Path("/one/")
    @Produces("application/json; charset=UTF-8")
    public String translitGet(@QueryParam("word")String english, @QueryParam("join") boolean join) throws Exception {
        Transliterator transliterator = TamilFactory.getTransliterator(null);
        TamilWord w = transliterator.transliterate(english, join);

        JSONObject obj = new JSONObject();
        obj.put("tamil", w.toString());
        return obj.toString();
    }


    @PUT
    @Path("/one/")
    @Produces("application/json; charset=UTF-8")
    public String translit(String english, @QueryParam("join") boolean join) throws Exception {
        Transliterator transliterator = TamilFactory.getTransliterator(null);
        TamilWord w = transliterator.transliterate(english, join);

        JSONObject obj = new JSONObject();
        obj.put("tamil", w.toString());
        return obj.toString();
    }
}
