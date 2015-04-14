package my.interest.lang.tamil.impl.dictionary;

import my.interest.lang.tamil.EzhuththuUtils;
import org.json.JSONException;
import org.json.JSONObject;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;
import tamil.lang.spi.TamilDictionaryProvider;

import java.net.HttpURLConnection;
import java.util.*;
import java.util.logging.Logger;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class RemoteDictionaryProvider extends DefaultPlatformDictionaryBase implements TamilDictionaryProvider {


    public static String REMOTE_SERVER_DICTIONARY_URL = null;


    static final Logger logger = Logger.getLogger(RemoteDictionaryProvider.class.getName());

    public static class KNOWN implements IKnownWord {

        TamilWord word = null;
        TamilWord type = null;

        KNOWN(String w, String t) {
            word = TamilWord.from(w);
            type = TamilWord.from(t);
        }

        @Override
        public TamilWord getWord() {
            return word;
        }

        @Override
        public TamilWord getType() {
            return type;
        }

        @Override
        public Set<String> getPropertyNames() {
            return null;
        }

        @Override
        public String getProperty(String name) {
            return null;
        }


        @Override
        public int compareTo(Object o) {
            return getWord().compareTo(((KNOWN) o).getWord());
        }
    }

    public RemoteDictionaryProvider() {

    }


    @Override
    public IKnownWord peekEnglish(String english) {

        List<IKnownWord> list = english_mapping.get(english);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        java.net.URL url = null;
        HttpURLConnection connection = null;
        try {

            System.out.println("Peeking for:" + english + ":" + REMOTE_SERVER_DICTIONARY_URL);
            url = new java.net.URL(REMOTE_SERVER_DICTIONARY_URL + "peekenglish/?word=" + english);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (connection.getResponseCode() == 200) {
                String json = new String(EzhuththuUtils.readAllFrom(connection.getInputStream(), false), "UTF-8");
                logger.info("Received:" + json);
                System.out.println("-------->Received:" + json);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(json);
                    JSONObject found = obj.getJSONObject("found");
                    if (found != null) {
                        IKnownWord known = new KNOWN(found.getString("tamil"), found.getString("type"));
                        System.out.println("-------->Returning:" + known);
                        list = new ArrayList<IKnownWord>();
                        list.add(known);
                        english_mapping.put(english, list);
                        return known;
                    } else {
                        return null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Callback to the dictionary provider.
     *
     * @return the TamilDictionary object.
     */
    @Override
    public TamilDictionary create() {
        System.out.println("Creating remote Dictionary!");
        if (REMOTE_SERVER_DICTIONARY_URL == null) {
            return null;
        }
        return this;
    }
}
