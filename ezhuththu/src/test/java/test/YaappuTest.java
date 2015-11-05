package test;

import org.junit.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.api.regex.RXKuttuFeature;
import tamil.util.regex.TamilPattern;

import java.util.regex.Matcher;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class YaappuTest {

    static {
        TamilFactory.init();
    }


    @Test
    public void testYaappuPatterns() {

        TamilPattern pattern = TamilPattern.compile("${இகரக்குற்றுநெடில்}", null, RXKuttuFeature.FEATURE);
        Matcher matcher = pattern.matcher("துயா");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${ntear}", null, RXKuttuFeature.FEATURE);
        matcher = pattern.matcher("துஅ");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${kurralh}", null, RXKuttuFeature.FEATURE);
        String kurralh =   "ஏரின் உழாஅர் உழவர் புயல்என்னும்\n" +
                "        வாரி வளங்குன்றிக் கால்";
       matcher = pattern.matcher(kurralh);

       Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${kurralh}");
        matcher = pattern.matcher(kurralh);
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${ntear}", null, RXKuttuFeature.FEATURE);
        String text =   "துயாழ்";
        matcher = pattern.matcher(text);
        Assert.assertTrue(matcher.matches());

//
//          while(matcher.find()) {
//           System.out.println(text.substring(matcher.start(), matcher.end()));
//        }
//


        pattern = TamilPattern.compile("${koovilham}", null, RXKuttuFeature.FEATURE);
        matcher = pattern.matcher("துயாழ்இனி");
        Assert.assertTrue(matcher.matches());

    }
}
