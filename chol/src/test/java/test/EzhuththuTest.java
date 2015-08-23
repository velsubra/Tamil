package test;

import my.interest.lang.tamil.internal.api.IPropertyFinder;
import my.interest.lang.util.Grid;
import my.interest.lang.util.Row;
import my.interest.lang.util.TextFileWriter;
import org.junit.Assert;
import org.junit.Test;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilFactory;
import tamil.lang.api.ezhuththu.EzhuththuDescription;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.lang.api.regex.RxDescription;
import tamil.lang.exception.TamilPlatformException;
import tamil.util.regx.TamilMatcher;
import tamil.util.regx.TamilPattern;

import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class EzhuththuTest implements IPropertyFinder {


    @Test
    public void testDescriptions() {
        Set<? extends RxDescription> set = TamilFactory.getRegXCompiler().getRegXDescriptions();
        List<RxDescription> list = new ArrayList<RxDescription>();
        list.addAll(set);

        Collections.sort(list, new Comparator<RxDescription>() {
            @Override
            public int compare(RxDescription o1, RxDescription o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Grid grid1 = new Grid();
        grid1.addColumn("S.No");

        grid1.addColumn("Usage in Regular Expression");
        grid1.addColumn("Description of the expression");
        grid1.addColumn("Number of Letters");
        grid1.addColumn("Letters");
        int count1 = 0;
        for (RxDescription rx : list) {

            if (EzhuththuDescription.class.isAssignableFrom(rx.getClass())) {
                EzhuththuDescription e = (EzhuththuDescription) rx;
                if (e.getCharacterSet() == null) continue;
                count1++;
                int cols = 0;

                Row r = grid1.createNewRow();
                r.setValueAt(String.valueOf(count1), cols++);
                r.setValueAt("${" + rx.getName() + "}", cols++);
                r.setValueAt(rx.getDescription(), cols++);

                r.setValueAt(String.valueOf(e.getCharacterSet().size()), cols++);
                List<TamilCharacter> chars = new ArrayList<TamilCharacter>();
                chars.addAll(e.getCharacterSet());
                Collections.sort(chars, new Comparator<TamilCharacter>() {
                    @Override
                    public int compare(TamilCharacter o1, TamilCharacter o2) {
                        return o1.compareTo(o2);
                    }
                });
                r.setValueAt(chars.toString(), cols++);


            }
        }

        grid1.pack(400);
        grid1.printTo(new TextFileWriter(new PrintStream(System.out, true)));

       // System.out.println(grid1.toHtml());



        Grid grid = new Grid();
        grid.addColumn("S.No");

        grid.addColumn("Usage in Regular Expression");
        grid.addColumn("Description of the expression");

        int count = 0;
        for (RxDescription rx : list) {
             boolean nonchar = true;

            if (EzhuththuDescription.class.isAssignableFrom(rx.getClass())) {
                EzhuththuDescription e = (EzhuththuDescription) rx;
                if (e.getCharacterSet() != null) {
                    nonchar  = false;
                };
            }
            if (nonchar) {
                count++;
                int cols = 0;
                System.out.println(rx.getName());
                Row r = grid.createNewRow();
                r.setValueAt(String.valueOf(count), cols++);
                r.setValueAt("${" + rx.getName() + "}", cols++);
                r.setValueAt(rx.getDescription(), cols++);


            }
        }

        grid.pack(400);
        grid.printTo(new TextFileWriter(new PrintStream(System.out, true)));

        System.out.println(grid.toHtml());


    }

    @Test
    public void testPatterns() {
        TamilPattern pattern = TamilPattern.compile("${ntearbu}");
        Matcher matcher = pattern.matcher("அம்சு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntearbu}");
        matcher = pattern.matcher("காசு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntiraibu}");
        matcher = pattern.matcher("பிறப்பு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntiraibu}");
        matcher = pattern.matcher("காசு");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntearbu}");
        matcher = pattern.matcher("பிறப்பு");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntearbu}");
        matcher = pattern.matcher("பசு");
        Assert.assertTrue(matcher.matches());


    }


    @Test
    public void testCompilationsTamilMatcher() throws Exception {
        TamilPattern pattern = TamilFactory.getRegXCompiler().compile("${தேமாங்காய்)}", new EzhuththuTest());
        TamilMatcher matcher = pattern.tamilMatcher("அமங்கம்மா");
        if (!matcher.find()) {
            throw new Exception("தேமாங்காய் is not found.");

        } else {
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            Assert.assertEquals(1, matcher.start());
            Assert.assertEquals(6, matcher.end());
        }

        pattern = TamilFactory.getRegXCompiler().compile("${(தேமாங்காய்)}", new EzhuththuTest());
        matcher = pattern.tamilMatcher("அமங்கம்மாவின் அங்கம்மா அங்கு ");
        if (!matcher.find()) {
            throw new Exception("தேமாங்காய் is not  found.");

        } else {
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            Assert.assertEquals(9, matcher.start());
            Assert.assertEquals(14, matcher.end());
        }
    }


    @Test
    public void testCompilations() throws Exception {
        TamilPattern pattern = TamilFactory.getRegXCompiler().compile("${tamil_like}", new EzhuththuTest());
        Matcher matcher = pattern.matcher("தமிழ்");
        Assert.assertTrue(matcher.matches());

        matcher = pattern.matcher("கமல்");
        Assert.assertTrue(matcher.matches());

        matcher = pattern.matcher("கமலி");
        Assert.assertFalse(matcher.matches());

        pattern = TamilFactory.getRegXCompiler().compile("${குற்றியலுகரம்}", new EzhuththuTest());
        matcher = pattern.matcher("அரசு");
        Assert.assertTrue(matcher.matches());


        matcher = pattern.matcher("அசு");
        Assert.assertFalse(matcher.matches());

        matcher = pattern.matcher("பேசி");
        Assert.assertFalse(matcher.matches());

        matcher = pattern.matcher("பிறப்பு");
        Assert.assertTrue(matcher.matches());

        matcher = pattern.matcher("பேசு");
        Assert.assertTrue(matcher.matches());


        matcher = pattern.matcher("அப்பசுபட்டு");
        Assert.assertTrue(matcher.matches());


        pattern = TamilFactory.getRegXCompiler().compile("${தேமாங்காய்}", new EzhuththuTest());
        matcher = pattern.matcher("அங்கம்மா");
        Assert.assertTrue(matcher.matches());


        matcher = pattern.matcher("அமங்கம்மாவின்");
        if (matcher.find()) {
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            Assert.assertEquals(1, matcher.start());
            Assert.assertEquals(9, matcher.end());
        } else {
            throw new Exception("தேமாங்காய் could not be found.");
        }

        pattern = TamilFactory.getRegXCompiler().compile("${(தேமாங்காய்}", new EzhuththuTest());
        matcher = pattern.matcher("அமங்கம்மா");
        if (matcher.find()) {
            throw new Exception("தேமாங்காய் is  found.");

        }

        pattern = TamilFactory.getRegXCompiler().compile("${தேமாங்காய்)}", new EzhuththuTest());
        matcher = pattern.matcher("அமங்கம்மா");
        if (!matcher.find()) {
            throw new Exception("தேமாங்காய் is not found.");

        } else {
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            Assert.assertEquals(1, matcher.start());
            Assert.assertEquals(9, matcher.end());
        }

        pattern = TamilFactory.getRegXCompiler().compile("${(தேமாங்காய்)}", new EzhuththuTest());
        matcher = pattern.matcher("அமங்கம்மாவின்");
        if (matcher.find()) {
            throw new Exception("தேமாங்காய் is  found.");

        }

        pattern = TamilFactory.getRegXCompiler().compile("${(தேமாங்காய்)}", new EzhuththuTest());
        matcher = pattern.matcher("அமங்கம்மாவின் அங்கம்மா அங்கு ");
        if (!matcher.find()) {
            throw new Exception("தேமாங்காய் is not  found.");

        } else {
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            Assert.assertEquals(14, matcher.start());
            Assert.assertEquals(22, matcher.end());
        }
    }

    @Test
    public void testCalculations() throws Exception {
        TamilCharacterSetCalculator calc = TamilFactory.getTamilCharacterSetCalculator();
        Set<TamilCharacter> set = calc.find("வலியுகரவரிசை");
        System.out.println("Size:" + set.size());

        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 6);


        set = calc.find("உகரவரிசைவலி");
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

        set = calc.find("மொழிமுதலல்லாத அகரவரிசைவலி");
        System.out.println("Size:" + set.size());
        System.out.println("Set:" + set);
        Assert.assertEquals(set.size(), 2);
    }

    @Override
    public String findProperty(String p1) {
        if (TamilFactory.getTransliterator(null).transliterate("tamil_like").toString().equals(p1)) {
            return "(${வலியுயிர்மெய்}${மெலியுயிர்மெய்}${இடையினமெய்})";
        }

        if ("குற்றியலுகரம்".equals(p1)) {
            return "(${ntedil}|${kurril}${ezhuththu})(${ezhuththu})*${உகரவரிசைவலி}";
        }
        return null;
    }
}