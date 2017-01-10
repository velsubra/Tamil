package test.nativerx;

import my.interest.lang.tamil.impl.nativerx.TamilNativeMatcher;
import my.interest.lang.tamil.impl.nativerx.TamilNativePattern;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;

/**
 * Created by velsubra on 1/8/17.
 */
public class NativeRxTest {

    static {
        TamilFactory.init();
    }

    @Test
    public void simpleTest() throws Exception {
        //TamilNativePattern pattern = TamilNativePattern.compile("(${kuRil}${ntedil}*)+");
       // TamilNativePattern pattern = TamilNativePattern.compile("(${[tha]}|(${ntedil}${mei}))&(${ezhuththu}{4})");
       // TamilNativePattern pattern = TamilNativePattern.compile("(${kurril}{2})(?=${mey}${[ru]})");
       // TamilNativePattern pattern = TamilNativePattern.compile("(${kurril}{3})(?!${[k]})");
       // TamilNativePattern pattern = TamilNativePattern.compile("${[[பே]]}(?!${[[ ]]})");
        //TamilNativePattern pattern = TamilNativePattern.compile("${[[[\\B]]]}${uyir}${[[[\\B]]]}");
        TamilNativePattern pattern = TamilNativePattern.compile("(?<double_kurril>(${kuRil}{2}))");
        TamilWord text = TamilFactory.getTransliterator(null).transliterate("தமிழுக்கும் அமுதென்றுபேர் \tஅமுதென்றுபேர் பேa  a ");
        //TamilWord text = TamilFactory.getTransliterator(null).transliterate("பேad");
        TamilNativeMatcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.print(text.subWord(matcher.start(), matcher.end()) + "-" + matcher.group("double_kurril"));
            if (matcher.start() == matcher.end()) {
                throw new Exception("Null value matched");
            }
        }
    }
}
