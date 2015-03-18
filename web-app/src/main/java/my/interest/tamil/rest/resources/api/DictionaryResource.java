package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.impl.FeatureSet;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.DictionaryFeature;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.api.parser.ParseFeature;
import tamil.lang.known.IKnownWord;

import javax.ws.rs.*;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/dictionary")
public class DictionaryResource extends BaseResource {


    @GET
    @Path("search/")
    @Produces("application/json; charset=UTF-8")
    public String search(@QueryParam("word") String word, @DefaultValue("20") @QueryParam("max") int max, @DefaultValue("") @QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        try {
            obj.put("given", word);
            TamilDictionary dictionary = TamilFactory.getSystemDictionary();
            List<IKnownWord> list = dictionary.search(TamilWord.from(word), max, null, FeatureSet.findFeatures(DictionaryFeature.class, features).toArray(new DictionaryFeature[]{}));
            obj.put("list", from(list));
            return obj.toString();

        } catch (Exception e) {
            handle(obj, e);


        }
        return obj.toString();
    }
}

