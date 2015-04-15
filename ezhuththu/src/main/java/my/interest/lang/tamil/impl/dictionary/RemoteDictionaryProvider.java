package my.interest.lang.tamil.impl.dictionary;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.PeyarchchchorrkalhDescription;
import my.interest.lang.tamil.generated.types.PeyarchcholDescription;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import my.interest.lang.tamil.punar.handler.verrrrumai.AbstractVearrrrumaiHandler;
import org.json.JSONObject;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.derived.PeyarchCholThiribu;
import tamil.lang.known.non.derived.Peyarchchol;
import tamil.lang.spi.TamilDictionaryProvider;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class RemoteDictionaryProvider extends DefaultPlatformDictionaryBase implements TamilDictionaryProvider {


    /**
     * This has to be set to point the remote url
     */
    public static String REMOTE_SERVER_API_URL = null;


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

    private void populateData() {
        byte[] data = getData("persist/applet/nouns/");
        if (data == null) {
            return;
        }

        try {


            String json = new String(data, "UTF-8");
            PeyarchchchorrkalhDescription nouns = EzhuththuUtils.getPeyarchchchorrkalhDescription(new JSONObject(json).getJSONObject("value"));
            if (nouns.getList() != null) {
                for (PeyarchcholDescription peyar : nouns.getList().getWord()) {

                    PropertyDescriptionContainer container = new PropertyDescriptionContainer(peyar);

                    String pronoun = container.getProNounMaruvi();
                    List<String> pnounlist = null;
                    if (pronoun != null) {
                        pnounlist = TamilUtils.parseString(pronoun, ",", true);
                        if (pnounlist != null && pnounlist.isEmpty()) {
                            pnounlist = null;
                        }
                    }

                    TamilWord n = TamilWord.from(peyar.getRoot());
                    TamilWord n_rm_ak = TamilUtils.trimFinalAKOrReturn(n);
                    Peyarchchol p = new Peyarchchol(n_rm_ak, n.size() - n_rm_ak.size(), container.isUyarthinhaipPeyar(), pnounlist != null);
                    add(p);
                    if (p.isUyarThinhai()) {
                        AbstractVearrrrumaiHandler.addUyarThinai(p);
                    }


                    if (pnounlist != null) {

                        for (String m : pnounlist) {
                            PeyarchCholThiribu pro = new PeyarchCholThiribu(TamilWord.from(m), p);
                            AbstractVearrrrumaiHandler.addPeyarchCholThiribu(pro);
                            add(pro);
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static byte[] getData(String path) {
        HttpURLConnection connection = null;
        java.net.URL url = null;
        try {

            url = new java.net.URL(REMOTE_SERVER_API_URL + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (connection.getResponseCode() == 200) {
                return EzhuththuUtils.readAllFrom(connection.getInputStream(), false);
            } else {
                return null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return null;

        }

    }


    @Override
    public IKnownWord peekEnglish(String english) {

        List<IKnownWord> list = english_mapping.get(english);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        System.out.println("Peeking for:" + english);
        byte[] data = getData("dictionary/peekenglish/?word=" + english);
        if (data == null) {
            return null;
        } else {
            try {
                String json = new String(data, "UTF-8");
                logger.info("Received:" + json);
                System.out.println("-------->Received:" + json);
                JSONObject obj = null;

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
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
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
        if (REMOTE_SERVER_API_URL == null) {
            return null;
        }
        populateData();
        return this;
    }
}
