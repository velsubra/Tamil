package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaaNtarrumBoo extends YaappuBaseRx {

    public TheamaaNtarrumBoo() {
        super("தேமாநறும்பூ");
    }
    public String generate(FeatureSet featureSet) {
        return  "(?:(?:${ntear}){2}${ntirai}${ntear})";
      //  return  "(?:(?!${koovilham}.*)(?:${ntear}{2}${ntirai}${ntear}))";
       // return  "(?:(?!(?:(?:${ntirai})|${koovilham}).*)(?:${ntear}{2}${ntirai}${ntear}))";
    }
}