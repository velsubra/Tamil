package my.interest.tamil.rest.resources.api;

import my.interest.lang.tamil.impl.FeatureSet;
import org.json.JSONArray;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.CompoundWordParser;
import tamil.lang.api.parser.ParseFeature;
import tamil.lang.api.parser.ParserResult;
import tamil.lang.known.IKnownWord;

import javax.ws.rs.*;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/parse")
public class ParserResource extends BaseResource {


    @GET
    @Path("/one/")
    @Produces("application/json; charset=UTF-8")
    public String parseGet(@QueryParam("word") String word, @DefaultValue("1") @QueryParam("max") int max, @DefaultValue("") @QueryParam("features") String features) throws Exception {
        JSONObject obj = new JSONObject();
        CompoundWordParser parser = TamilFactory.getCompoundWordParser();
        List<ParserResult> list = parser.parse(TamilWord.from(word), max, FeatureSet.findFeatures(ParseFeature.class, features).toArray(new ParseFeature[]{}));
        populate(word, obj, list);
        return obj.toString();

    }

    private void populate(String word, JSONObject obj, List<ParserResult> with) throws Exception {
        try {
            boolean parsed = true;
            obj.put("given", word);
            if (with == null || with.isEmpty()) {
                obj.put("parsed", false);
                parsed = false;
            } else {

                JSONArray array = new JSONArray();
                for (int i = with.size() - 1; i >= 0; i--) {
                    ParserResult r = with.get(i);
                    if (r.getSplitWords() == null || r.getSplitWords().isEmpty()) continue;
                    JSONObject arrayobj = new JSONObject();
                    array.put(arrayobj);
                    arrayobj.put("tamil", r.getCompoundTamilWord().toString());
                    JSONArray arraysplits = new JSONArray();
                    arrayobj.put("splits", arraysplits);
                    for (IKnownWord split : r.getSplitWords()) {
                        arraysplits.put(from(split));
                    }
                    if (!r.isParsed()) {
                        parsed = false;
                        break;

                    }
                }
                if (array.length() > 0) {
                    obj.put("parsed", parsed);

                    obj.put("splitways", array);
                } else {
                    obj.put("parsed", false);
                    parsed = false;
                }

            }

            if (!parsed) {
                if (with != null && !with.isEmpty()) {
                    ParserResult.PARSE_HINT hint = with.get(0).getParseHint();
                    if (hint != null) {
                        JSONObject hintObj = new JSONObject();
                        obj.put("hint", hintObj);
                        hintObj.put("tamilStartIndex", hint.getTamilStartIndex());
                        hintObj.put("tamilEndIndex", hint.getTamilEndIndex());
                        hintObj.put("unicodeStartIndex", hint.getUnicodeStartIndex());
                        hintObj.put("unicodeEndIndex", hint.getUnicodeEndIndex());
                        hintObj.put("message", hint.getMessage());
                    }

                }
            }


        } catch (Exception e) {
            handle(obj, e);
        }
    }


    @PUT
    @Path("/bulk/")
    @Produces("application/json; charset=UTF-8")
    public String parseBulk(String word, @DefaultValue("1") @QueryParam("max") int max, @DefaultValue("") @QueryParam("features") String features) throws Exception {
        return parseGet(word, max, features);

    }
}
