package my.interest.tamil.util;

import my.interest.lang.util.NameValuePair;

/**
 * Created by velsubra on 6/11/16.
 */
public class WebPageSpellChecker extends AbstractJSoupJob {


    public WebPageSpellChecker(String dataUrl, String submiturl, String viewurl, String scripturl) {
        super(dataUrl, submiturl, viewurl, scripturl);
    }

    @Override
    public NameValuePair<String, Integer> process(String text) throws Exception {
        return new NameValuePair<String, Integer>( text,0);
    }
}
