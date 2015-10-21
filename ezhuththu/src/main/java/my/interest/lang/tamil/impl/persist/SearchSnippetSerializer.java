package my.interest.lang.tamil.impl.persist;

import my.interest.lang.tamil.TamilUtils;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.persist.object.ObjectSerializer;
import tamil.lang.exception.service.ServiceException;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SearchSnippetSerializer implements ObjectSerializer<SearchResultSnippet> {
    public Class<SearchResultSnippet> getTypeToSerialize() {
        return SearchResultSnippet.class;
    }

    public SearchResultSnippet deserialize(byte[] data) {
        try {
            JSONObject json = new JSONObject(new String(data, TamilUtils.ENCODING));
            if (json.has("match")) {
                String match = json.getString("word");
                SearchResultSnippet snippet = new SearchResultSnippet();
                snippet.setMatch(match);
                if (json.has("pre")) {
                    snippet.setPre(json.getString("pre"));
                }
                if (json.has("post")) {
                    snippet.setPost(json.getString("post"));
                }
                return snippet;

            }
            return null;

        } catch (Exception e) {
            throw new ServiceException("Unable to de-serialize object:" + e.getMessage());
        }
    }

    public byte[] serialize(SearchResultSnippet object) {
        JSONObject json = new JSONObject();
        try {
            if (object != null && object.getMatch() != null) {
                json.put("match", object.getMatch());
               if (object.getPre() != null) {
                   json.put("pre", object.getPre());
               }
                if (object.getPost() != null) {
                    json.put("post", object.getPost());
                }
            }
            return json.toString().getBytes(TamilUtils.ENCODING);
        } catch (Exception e) {
            throw new ServiceException("Unable to serialize object:" + e.getMessage());
        }

    }
}