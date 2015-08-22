package test;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import org.junit.Assert;
import org.junit.Test;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.exception.TamilPlatformException;
import tamil.util.regx.TamilPattern;

import java.util.Set;
import java.util.regex.Matcher;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class EzhuththuTest implements IPropertyFinder {

    @Test
    public void testCompilations() throws Exception {
        TamilPattern pattern = TamilFactory.getRegXCompiler().compile("${tamil_like}", new EzhuththuTest());
        Matcher matcher = pattern.matcher("தமிழ்");
        Assert.assertTrue(matcher.matches());

        matcher = pattern.matcher("கமல்");
        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void testCalculations() throws Exception {
        TamilCharacterSetCalculator calc = TamilFactory.getTamilCharacterSetCalculator();
        Set<TamilCharacter> set = calc.find("வலியுகரவரிசை");
        System.out.println("Size:" + set.size());

        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 6);


        set = calc.find("மொழிமுதல்வலி");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 4 * 12);

        set = calc.find("!மொழிமுதல்வலி");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 247 - 4 * 12);

        set = calc.find("மொழிமுதல்வலியல்லாதது");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 247 - 4 * 12);

        set = calc.find("!மொழிமுதல்வலியல்லாதது");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 4 * 12);


        set = calc.find("உயிர்க்குறில்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 5);


        set = calc.find("குறிலல்லாவுயிர்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 7);

        set = calc.find("குறில்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 95);

        set = calc.find("உயிரல்லாக்குறில்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 90);


        set = calc.find("மெய்க்குறில்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 0);


        set = calc.find("மொழிமுதல்வலியாகாரவரிசை");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 4);

        set = calc.find("மொழிக்கடைவலியாகாரவரிசை");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 6);

        set = calc.find("மொழிமுதலல்லாவலியிகரவரிசையல்லாதது");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 245);

        set = calc.find("ஓரெழுத்துமொழிவலி");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 28);

        set = calc.find("வலியல்லாதது");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 169);

        try {
            set = calc.find("அல்லாத");
            throw new Exception("Test failed.");
        } catch (TamilPlatformException tp) {

        }

        try {
            set = calc.find("அல்லாவலி");
            throw new Exception("Test failed.");
        } catch (TamilPlatformException tp) {

        }


        set = calc.find("ககரவரிசையுயிர்மெய்நெடில்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 7);

        set = calc.find("ககரவரிசையல்லாவுயிர்மெய்நெடில்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 119);

        set = calc.find("மொழிமுதல்வலியெழுத்து");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 4 * 12);


        set = calc.find("உயிர் அல்லது     மெய்");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 12 + 18);

        set = calc.find("உயிர்மெய்      அல்லது    நெடில்    ");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 216 + 7);
    }

    @Override
    public String findProperty(String p1) {
        if (TamilFactory.getTransliterator(null).transliterate("tamil_like").toString().equals(p1)) {
            return "(${வலியுயிர்மெய்}${மெலியுயிர்மெய்}${இடையினமெய்})";
        }
        return null;
    }
}
