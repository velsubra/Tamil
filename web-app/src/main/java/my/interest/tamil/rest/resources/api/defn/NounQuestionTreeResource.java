package my.interest.tamil.rest.resources.api.defn;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.GlobalTypes;
import my.interest.lang.tamil.generated.types.RootVerbDescription;
import my.interest.lang.tamil.generated.types.Typed;
import my.interest.lang.tamil.internal.api.DefinitionFactory;
import my.interest.tamil.rest.resources.api.BaseResource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tamil.lang.TamilFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/noun/question-tree")
public class NounQuestionTreeResource extends BaseResource {

    @GET
    @Path("/describe/id/{id}")
    @Produces("application/json; charset=UTF-8")
    public String describeNode(@PathParam("id") String id) throws JSONException {
        JSONObject ret = new JSONObject();
        JSONArray list = new JSONArray();
        ret.put("list", list);
        try {

            if (id == null) {
                id = "";
            }

            GlobalTypes types = TamilFactory.getPersistenceManager().getNounManager().getNounGlobalTypes();
            if (types != null) {
                for (Typed t : types.getDeclare()) {


                    if (t.getName().trim().startsWith(id)) {
                        JSONObject obj = new JSONObject();
                        list.put(obj);
                        obj.put("id", t.getName());
                        obj.put("desc", t.getDescription());

                        QName name = t.getType();
                        Map<String, String> map = null;
                        if (name != null & DefinitionFactory.NS_TAMIL.equals(name.getNamespaceURI())) {
                            obj.put("qytpe", "CHOICE");
                            //multiple choice

                            String localpart = name.getLocalPart();
                            localpart = localpart.trim();
                            localpart = localpart.replaceAll("\\n", "");

                            if (localpart.startsWith("[") && localpart.endsWith("]")) {
                                List<String> items = TamilUtils.parseString(localpart.substring(1, localpart.length() - 1));
                                map = new LinkedHashMap<String, String>();
                                for (String it : items) {
                                    it = it.trim();
                                    if (it.equals("")) continue;
                                    List<String> pairs = TamilUtils.parseString(it, "=");
                                    String key = pairs.get(0);
                                    String value = null;
                                    if (pairs.size() > 1) {
                                        value = TamilFactory.getTransliterator(null).transliterate(pairs.get(1)).toString();
                                    } else {
                                        value = key;
                                    }
                                    map.put(key.trim(), value.trim());
                                }
                            } else {
                                try {
                                    map = TamilUtils.getEnumValuesAsList(Class.forName(TamilUtils.getPackageName(RootVerbDescription.class.getName()) + "." + name.getLocalPart()));
                                } catch (ClassNotFoundException cnf) {
                                    cnf.printStackTrace();

                                }
                            }

                        } else if (name != null & "boolean".equals(name.getLocalPart())) {
                            obj.put("qytpe", "YN");
                            //boolean typed.
                            map = new HashMap<String, String>();
                            map.put("true", "ஆம்");
                            map.put("false", "இல்லை");

                        } else {
                            // descriptive
                            obj.put("qytpe", "DESC");

                        }
                        if (map != null) {
                            JSONArray subquestions = new JSONArray();
                            obj.put("choices", subquestions);
                            Iterator<String> it = map.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                JSONObject sub = new JSONObject();
                                subquestions.put(sub);
                                sub.put("id", key);
                                sub.put("val", map.get(key));
                            }


                        }
                    }

                }
            }
        } catch (Exception e) {
            handle(ret, e);


        }
        return ret.toString();
    }
}
