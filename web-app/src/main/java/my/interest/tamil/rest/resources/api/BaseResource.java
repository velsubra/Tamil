package my.interest.tamil.rest.resources.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class BaseResource {


    protected void handle(JSONObject obj, Exception e) throws org.json.JSONException {
        e.printStackTrace();
        obj.put("error", true);
        obj.put("emessage", e.getMessage());
        obj.put("etype", e.getClass().getName());
    }


    protected JSONObject from(IKnownWord known) throws JSONException {
        JSONObject sp = new JSONObject();
        sp.put("tamil", known.getWord().toString());
        sp.put("type", known.getType().toString());
        return  sp;
    }


    protected JSONArray from(List<IKnownWord> known) throws JSONException {
        JSONArray array = new JSONArray();
         for (IKnownWord w : known) {
               array.put(from(w));
         }
        return  array;
    }


}
