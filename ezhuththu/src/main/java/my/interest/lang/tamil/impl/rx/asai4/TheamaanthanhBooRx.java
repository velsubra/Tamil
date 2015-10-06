package my.interest.lang.tamil.impl.rx.asai4;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.yaappu.YaappuBaseRx;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TheamaanthanhBooRx extends YaappuBaseRx {

    public TheamaanthanhBooRx() {
        super("தேமாந்தண்பூ");
    }
    public String generate(FeatureSet featureSet) {

       // return  "(?:(?!(${koovilham}|${theamaangani}).*)(?:${ntear}{4}))";
       return  "(?:(?:${ntear}){4})";
    }
}