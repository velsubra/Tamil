package my.interest.tamil.rest.resources.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tamil.lang.TamilCharacterLookUpContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
@Path("api/ezhuththu")
public class EzhuththuResource extends BaseResource {


    @GET
    @Path("unicode/normalization/map")
    @Consumes("application/json")
    @Produces("application/json; charset=UTF-8")
    public String getMap() throws Exception {
        return from(TamilCharacterLookUpContext.getMap()).toString();
    }

    @GET
    @Path("unicode/normalization/map/html")
    @Produces("text/html; charset=UTF-8")
    @Consumes(MediaType.TEXT_HTML)
    public String getMapHtml() throws Exception {
        StringBuffer table = new StringBuffer();
        table.append("<html><head><title>Tamil unicode characters</title><body><table border='1'><tr><th>S.NO</th><th colspan='6'>Sequence =></th></tr>");
        table.append(toHtmlTableBody("", TamilCharacterLookUpContext.getMap()));
        table.append("</table></body></html>");
        return table.toString();
    }


    private static JSONArray from(Map<Integer, TamilCharacterLookUpContext> map) throws JSONException {
        JSONArray array = new JSONArray();
        List<Integer> next = new ArrayList<Integer>(map.keySet());
        Collections.sort(next);
        for (Integer item : next) {
            array.put(from(item, map.get(item)));
        }
        return array;
    }

    private static JSONObject from(int val, TamilCharacterLookUpContext context) throws JSONException {
        JSONObject object = new JSONObject();
        JSONObject firstcolumn = new JSONObject();
        firstcolumn.put("value", String.valueOf(val));
        if (!context.currentChar.toString().equals("" + (char) val)) {
            firstcolumn.put("form", "" + (char) val);
        }

        firstcolumn.put("tamil", context.currentChar.toString());
        object.put("first", firstcolumn);

        Map<Integer, TamilCharacterLookUpContext> continuations = context.getContinuations();
        if (continuations != null) {
            object.put("second", from(continuations));

        }
        return object;
    }


    private static String toHtmlTableBody(String ssno, Map<Integer, TamilCharacterLookUpContext> map) throws JSONException {
        StringBuffer table = new StringBuffer();
        table.append("<tbody>");
        List<Integer> next = new ArrayList<Integer>(map.keySet());
        Collections.sort(next);
        int count = 0;
        for (Integer item : next) {
            count ++;
            table.append("<tr>");
            table.append("<td>#" + ssno + count  + "</td>");
            table.append(toHtml(ssno + String.valueOf(count) +".",  item, map.get(item)));

            table.append("</tr>");
        }
        table.append("</tbody>");
        return table.toString();
    }

    private static String toHtml(String sno, int val, TamilCharacterLookUpContext context) throws JSONException {
        StringBuffer table = new StringBuffer();
        table.append("<td>");

        table.append("@value:<b>0x0</b>" + Integer.toHexString(val));
        table.append("<br/>letter:" + context.currentChar.toString());

        if (!context.currentChar.toString().equals("" + (char) val)) {
            table.append("<br/><br/>form:" + (char) val);

        } else {

        }



        Map<Integer, TamilCharacterLookUpContext> continuations = context.getContinuations();
        if (continuations != null && !continuations.isEmpty()) {
            table.append("</td><td><table border='1'>");
            table.append(toHtmlTableBody(sno, continuations));
            table.append("</table>");

        }
        table.append("</td>");
        return table.toString();
    }

}
