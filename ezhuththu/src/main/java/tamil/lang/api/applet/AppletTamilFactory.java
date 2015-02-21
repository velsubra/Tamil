package tamil.lang.api.applet;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.impl.FeatureSet;
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
 * The macro ${R_INJECT_PLATFORM_APPLET}  , will inject this applet into the resource.
 * All the platform provided will make use of this applet and avoid expensive ReST calls over HTTP
 * <p/>
 * When an application asks for the applet, the user experience is supposed to be better as some service are locally executed.
 * <p/>
 * </p>
 * <p/>
 * <p>
 * Please note: Applets need Java installed in the machine from where the application is accessed.
 * If applet is not initialized the services fall back on ReST.
 * </p>
 * <p/>
 * <b>Note: </b>
 * <p>
 * Many APIs take a features argument that is a String. It is because these methods are typically called from javascript running at the browser.
 * To understand how the features list is formed, please refer to java doc of the class {@link tamil.lang.api.feature.Feature} that defines various features as public static final variables.
 * The variable ends with number. You have to specify that number if you need the feature.
 * E.g) {@link tamil.lang.api.feature.Feature#TRANSLIT_JOIN_FEATURE_VAL_110} means the join feature.
 * If you need that feature, you have to specify "110" as the required feature. Multiple features can be specified with comma as the separator.
 * Features not-recognized by a service method will be ignored.
 * </p>
 *
 * @author velsubra
 */
public class AppletTamilFactory extends JApplet {


    Transliterator trans = null;

    @Override
    public void init() {
        System.out.println("TAMIL Platform Applet init......------------");
        TamilFactory.init();
        trans = TamilFactory.getTransliterator(null);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }


    /**
     * Reads a number into Tamil text
     *
     * @param number   the number to be read
     * @param features the comma separated set of features expressed in comma separated set of integers.
     * @return {json} - the Tamil transliterated object as JSON string or any error.
     * <b> json.tamil </b> gives the transliterated string. No English letters will be present in it.
     * <b> json.error </b> gives true if there is an error.
     * <b> json.emessage </b> gives the error message.
     * @throws org.json.JSONException
     */
    public String readNumber(String number, String features) throws org.json.JSONException {

        try {

            NumberReader reader = TamilFactory.getNumberReader();
            TamilWord w = reader.readNumber(number, FeatureSet.findFeatures(ReaderFeature.class, features).toArray(new ReaderFeature[]{}));
            JSONObject obj = new JSONObject();
            obj.put("tamil", w.toString());
            return obj.toString();
        } catch (Exception e) {
            return handle(e).toString();
        }

    }

    /**
     * Transliterate and returns json response.
     * Javascript is supposed to call these methods.
     *
     * @param data     the byte[]  of the text to transliterate
     * @param features the comma separated set of features expressed in comma separated set of integers.
     * @return {json} - the Tamil transliterated object as JSON string or any error.
     * <b> json.tamil </b> gives the transliterated string. No English letters will be present in it.
     * <b> json.error </b> gives true if there is an error.
     * <b> json.emessage </b> gives the error message.
     */
    public String transliterate(char[] data, String features) throws org.json.JSONException {

        try {
            StringBuffer buffer = new StringBuffer();
            if (data != null) {
                for (int i =0 ;i < data.length; i++) {
                    buffer.append((char)data[i]);
                }
            }

            String text = buffer.toString();
            TranslitFeature[] fs = FeatureSet.findFeatures(TranslitFeature.class, features).toArray(new TranslitFeature[]{});
            JSONObject obj = new JSONObject();
            obj.put("tamil", trans.transliterate(text, fs).toString());
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
