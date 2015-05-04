package tamil.lang.api.applet;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.dictionary.RemoteDictionaryProvider;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.number.NumberReader;
import tamil.lang.api.number.ReaderFeature;
import tamil.lang.api.trans.TranslitFeature;
import tamil.lang.api.trans.Transliterator;

import javax.swing.*;

/**
 * <p>
 * Tamil platform applet that can be downloaded into the html resource of any application.
 * The macro ${R_INJECT_PLATFORM_APPLET}   will inject this applet into the resource.
 * All the platform provided APIs could make use of this applet and avoid expensive ReST calls over HTTP
 * <p/>
 * When an application asks for the applet, the user experience is supposed to be better as some service are locally executed.
 * <p/>
 * </p>
 * <p/>
 * <p>
 * Please note: Applets need Java installed in the machine from where the application is accessed.
 * If applet is not initialized the services fall back on ReST APIs.
 * </p>
 * <p/>
 * <b>Note: </b>
 * <p>
 * Many APIs take a features argument that is a String. It is because these methods are typically called from javascript running at the browser.
 * To understand how the features list is formed, please refer to java doc of the class {@link tamil.lang.api.feature.Feature} that is the base for
 * various features. The class {@link tamil.lang.api.feature.FeatureConstants} declares features as public static final variables ending with number. You have to specify that number if you need the feature.
 * E.g) {@link tamil.lang.api.feature.FeatureConstants#TRANSLIT_JOIN_FEATURE_VAL_110} means the join feature used by {@link tamil.lang.api.trans.Transliterator}.
 * If you need that feature, you have to specify "110" as the required feature. Multiple features can be specified with comma as the separator.
 * Features not-recognized by a service method will be ignored.
 * </p>
 *
 * @author velsubra
 */
public class AppletTamilFactory extends JApplet {


    Transliterator trans = null;
    NumberReader reader = null;

    public AppletTamilFactory() {
        super();
    }

    @Override
    public void init() {

        String url = getParameter("archive");
        System.out.println("TAMIL....------------:" + url );
         java.net.URL doc = getDocumentBase();

        int api = url.indexOf("/api/");
        if (api > 0) {
            url = url.substring(0, api) + "/api/";
            try {
                RemoteDictionaryProvider.REMOTE_SERVER_API_URL = doc.toURI().resolve(url).toString();
                System.out.println("SEt:" + RemoteDictionaryProvider.REMOTE_SERVER_API_URL);
            } catch (Exception e) {
                e.printStackTrace();;
            }
        }

        TamilFactory.init();
        trans = TamilFactory.getTransliterator(null);
        reader = TamilFactory.getNumberReader();


    }

    @Override
    public void start() {
       this.showStatus("Applet Started.");
    }

    @Override
    public void stop() {
        this.showStatus("Applet Stopped.");
    }

    @Override
    public void destroy() {

    }


    /**
     * Reads a number into Tamil text
     *
     * @param number   the number to be read    in decimal form (Eg. 100000000000000)
     * @param features the comma separated set of features expressed in comma separated set of integers.
     * @return {json} - the number text (E.g ஒரு கோடியே கோடி)
     * <b> json.tamil </b> gives the transliterated string. No English letters will be present in it.
     * <b> json.error </b> gives true if there is an error.
     * <b> json.emessage </b> gives the error message.
     * @throws org.json.JSONException
     */
    public String readNumber(String number, String features) throws org.json.JSONException {

        try {

            TamilWord w = reader.readNumber(number, FeatureSet.findFeatures(ReaderFeature.class, features).toArray(new ReaderFeature[]{}));
            // System.out.println("Number:" + w.toString());
            // System.out.println("file.encoding:" +System.getProperty("file.encoding"));
            JSONObject obj = new JSONObject();
            obj.put("tamil", w.toString());
            return obj.toString();
        } catch (Exception e) {
            return handle(e).toString();
        }

    }

    /**
     * Reads text and convert that into a number
     * @param number  the number text to be read.    (E.g ஒரு கோடியே கோடி)
     * @param features the features lists
     * @return   {json} - the Tamil transliterated object as JSON string or any error.
     * <b> json.number </b> gives the number in decimal form .(Eg. 100000000000000)
     * <b> json.error </b> gives true if there is an error.
     * <b> json.emessage </b> gives the error message.
     * @throws org.json.JSONException
     * @throws org.json.JSONException
     */
    public String readAsNumber(String number, String features) throws org.json.JSONException {

        try {

            String w = reader.readAsNumber(number, FeatureSet.findFeatures(ReaderFeature.class, features).toArray(new ReaderFeature[]{}));
            // System.out.println("Number:" + w.toString());
            // System.out.println("file.encoding:" +System.getProperty("file.encoding"));
            JSONObject obj = new JSONObject();
            obj.put("number", w);
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return handle(e).toString();
        }

    }

    /**
     * Transliterate and returns json response.   Note: This only takes ascii text as input.
     * Javascript is supposed to call these methods.
     *
     * @param text     the text to transliterate
     * @param features the comma separated set of features expressed in comma separated set of integers.
     * @return {json} - the Tamil transliterated object as JSON string or any error.
     * <b> json.tamil </b> gives the transliterated string. No English letters will be present in it.
     * <b> json.error </b> gives true if there is an error.
     * <b> json.emessage </b> gives the error message.
     */
    public String transliterate(String text, String features) throws org.json.JSONException {


        try {
            TranslitFeature[] fs = FeatureSet.findFeatures(TranslitFeature.class, features).toArray(new TranslitFeature[]{});
            JSONObject obj = new JSONObject();
            TamilWord w = trans.transliterate(text, fs);
            //  System.out.println("Tamil:" + w.toString());
            obj.put("tamil", w.toString());
            obj.put("given", text);
            return obj.toString();
        } catch (Exception e) {
            return handle(e).toString();
        }
    }

    private JSONObject handle(Exception e) throws org.json.JSONException {
        JSONObject obj = new JSONObject();
        obj.put("error", "true");
        obj.put("emessage", e.getMessage());
        return obj;
    }
}
