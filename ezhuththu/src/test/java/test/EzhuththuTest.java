package test;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.StringUtils;
import tamil.util.IPropertyFinder;
import org.junit.Assert;
import org.junit.Test;
import tamil.lang.*;
import tamil.lang.api.ezhuththu.TamilCharacterSetCalculator;
import tamil.util.regx.TamilPattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class EzhuththuTest {

    static {
        TamilFactory.init();
    }




    @Test
    public void testSize() {
        Assert.assertEquals(5 * 18 + 5, EzhuththuUtils.filterKuRil().size());
        Assert.assertEquals(7 * 18 + 7, EzhuththuUtils.filterNedil().size());
        Assert.assertEquals(12, EzhuththuUtils.filterUyir().size());
    }


    @Test
    public void testNoCalculations() {
        TamilCharacterSetCalculator calc = TamilFactory.getTamilCharacterSetCalculator();

        System.out.println("Size:"+ calc.getEzhuththuDescriptions().size());
        Assert.assertTrue(91==calc.getEzhuththuDescriptions().size());
        Set<TamilCharacter> set = calc.find("யகரவரிசை");
        Assert.assertEquals(set.size(), 13);

        set = calc.find("யகரவரிசை");
        Assert.assertEquals(set.size(), 13);

        set = calc.find("உயிர்மெய்");
        Assert.assertEquals(set.size(), 216);

        set = calc.find("!வடமொழியெழுத்து");
        Assert.assertEquals(set.size(), 247);

        set = calc.find("!இளகரவரிசை");
        Assert.assertEquals(set.size(), 234);

        set = calc.find("!நெடில்");
        Assert.assertEquals(set.size(), 114);
    }


    @Test
    public void testPatterns1() {
        TamilPattern pattern = TamilPattern.compile("${வகை[ka]}");
        Matcher matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("sai").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(வகை[kadal])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("thamizh").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(வகை[f f])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("f f").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(வகை[qwrwrwrwtetertetet y ryrtytryrtytrfhfghgfh])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("qwrwrwrwtetertetet y ryrtytryrtytrfhfghgfh").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(வகை[f f])}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("f f").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${அசை[செந்தமிழ்]}");
        matcher = pattern.matcher("கூவிளம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${மாத்திரை[தென்னை]}");
        matcher = pattern.matcher("கண்ணா");
        Assert.assertTrue(matcher.matches());



        pattern = TamilPattern.compile("${வகை[தமிழ்]}");
        matcher = pattern.matcher("பாகம்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${மெய்வகை[அங்கு]}");
        matcher = pattern.matcher("அந்தி");
        Assert.assertTrue(matcher.matches());
    }


    @Test
    public void testPatterns() {
        TamilPattern pattern = TamilPattern.compile("${ezhuththuvadivam}*");
        Matcher matcher = pattern.matcher("தமிழ்தமிழ்");

        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("தம்பியும்");
        Assert.assertFalse(matcher.matches());


//        Pattern patt = Pattern.compile("(?<!ab)a");
//        matcher = patt.matcher("ada");
//        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${ezhuththuvadivam}*");
        matcher = pattern.matcher("aதமிழ்தமிழ்");
        Assert.assertFalse(matcher.matches());

        Assert.assertFalse(TamilCompoundCharacter.ILLL.isKurilezhuththu());

        pattern = TamilPattern.compile("${kurril}*");
        matcher = pattern.matcher("தமிழ்தமிழ்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${kurril}*");
        matcher = pattern.matcher("தமிதமி");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}*");
        matcher = pattern.matcher("வேலா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}*");
        matcher = pattern.matcher("ல");
        Assert.assertFalse(TamilSimpleCharacter.LA.isNtedilezhuththu());
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}");
        matcher = pattern.matcher("ஆ");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}${kurril}+");
        matcher = pattern.matcher("ஆடுடு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${uyir}${kurril}+");
        matcher = pattern.matcher("ஆடுடு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntedil}");
        matcher = pattern.matcher("க்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${kurril}");
        matcher = pattern.matcher("க்");
        Assert.assertFalse(matcher.matches());

//        Pattern j = Pattern.compile("a(?!d)");
//        System.out.println( j.matcher("ac").find());

        pattern = TamilPattern.compile("${!kurril}*");
        matcher = pattern.matcher("காக்கா");
        Assert.assertTrue(matcher.matches());
//        Assert.assertTrue(matcher.find());
//        System.out.println(matcher.start() +":" + matcher.end());
        pattern = TamilPattern.compile("${!kurril}*");
        matcher = pattern.matcher("காகம்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${!ezhuththu}*");
        matcher = pattern.matcher("abcd");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${!ezhuththu}*");
        matcher = pattern.matcher("காகம்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${ezhuththu}*");
        matcher = pattern.matcher("காகம்a");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${mozhimuthal}${mozhiyidai}*${mozhikkadai}+");
        matcher = pattern.matcher("காகம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ஓரெழுத்துமொழி}+");
        matcher = pattern.matcher("கா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${mozhi}");
        matcher = pattern.matcher("பி");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${mozhi}");
        matcher = pattern.matcher("அம்மா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(mozhi)}");
        matcher = pattern.matcher("மா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${(ntear)}");
        matcher = pattern.matcher("மா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}");
        matcher = pattern.matcher("அம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("நடா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("னடா");
        Assert.assertTrue(matcher.matches());




        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("காசு");
        Assert.assertFalse(matcher.matches());




        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("பசு");
        Assert.assertTrue(matcher.matches());



        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பசு");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பசுக்கள்");
        Assert.assertFalse(matcher.matches());



        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுகா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காசு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("காசும்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காசும்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காசுமட்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுகன்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("சுக்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntirai}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${ntear}");
        matcher = pattern.matcher("சுக்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}${ntear}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}${ntear}");
        matcher = pattern.matcher("சுக்க");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("மாமா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("காக்கா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${ntear}${ntear}");
        matcher = pattern.matcher("பக்கா");
        Assert.assertTrue(matcher.matches());




        pattern = TamilPattern.compile("${pulhimaa}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${pulhimaa}");
        matcher = pattern.matcher("புளிமா");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${pulhimaa}");
        matcher = pattern.matcher("மாமா");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("புளிமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("மாமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("கூவிளம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("அம்மடு");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("கருவி");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("அலகிடம்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("அரங்குடன்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${koovilham}");
        matcher = pattern.matcher("காலடம்");
        Assert.assertTrue(matcher.matches());




        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("புளிமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("மாமா");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("கருவிளம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("கருவி");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("அலாக்கிடாம்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("அரங்குடன்");
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${karuvilham}");
        matcher = pattern.matcher("காலடம்");
        Assert.assertFalse(matcher.matches());



        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("காலடம்");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("தேமாங்காய்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("கூவிளம்");
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("கூவிள");
        Assert.assertFalse(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher("மாம்மாம்ம");
        Assert.assertTrue(matcher.matches());




        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பக்கா");
        Assert.assertTrue(matcher.matches());
        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("பக்காடு");
        Assert.assertFalse(matcher.matches());

        matcher = pattern.matcher("பக்காட்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுக்க");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaa}");
        matcher = pattern.matcher("சுக்கன்");
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${theamaangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("theamaangani").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("தெம்மாங்கனி").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("தெம்மாங்க").toString());
        Assert.assertTrue(matcher.matches());



        pattern = TamilPattern.compile("${pulhimaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("புளிமங்காய்").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${pulhimaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("pulhimaangaay").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${pulhimaangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("pulhimaangani").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("koovilhangaay").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("koovilhangani").toString());
        Assert.assertTrue(matcher.matches());


        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டான").toString());
        Assert.assertFalse(matcher.matches());
        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டா").toString());
        Assert.assertFalse(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்னடா").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்ன").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டா").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${koovilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("அக்கடல்டால்").toString());
        Assert.assertTrue(matcher.matches());

        pattern = TamilPattern.compile("${karuvilhangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("karuvilhangaay").toString());
        Assert.assertTrue(matcher.matches());
        pattern = TamilPattern.compile("${karuvilhangani}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("karuvilhangani").toString());
        Assert.assertTrue(matcher.matches());



        pattern = TamilPattern.compile("${theamaangaay}");
        matcher = pattern.matcher(TamilFactory.getTransliterator(null).transliterate("pulhimaangaay").toString());
        Assert.assertFalse(matcher.matches());





    }


    @Test
    public void testUniCodeChar() {
        TamilWord tamil = TamilWord.from("தமிழ்");
        String unicode = tamil.toUnicodeStringRepresentation();
        unicode = unicode.replace("\"", "\\\"");
        System.out.println(unicode);
        Pattern p = Pattern.compile(unicode, Pattern.CANON_EQ);
        Matcher matcher = p.matcher("abதமிழ்a தமிழ்     ");
        while (matcher.find()) {
            System.out.println(matcher.start() + ":" + matcher.end());
        }


    }


    @Test
    public void tesIndexReplacement() {

        testIndexReplacement("one", "1=one,two=2,200=two hundred", 2, new StringUtils.IndexContext.Range(2));
        testIndexReplacement("${1} ${two}", "1=one,two=2,200=two hundred", 4, new StringUtils.IndexContext.Range(5, 11));
        testIndexReplacement("${1} ${2}", "1=one,2=2,200=two hundred", 0, new StringUtils.IndexContext.Range(0, 4));

        testIndexReplacement("${1} ${2} ${one}", "one=1,1=${one},2=2,200=two hundred", 4, new StringUtils.IndexContext.Range(10, 16));


        testIndexReplacement("${1} ${000} ${2} ${one}", "one=1,1=${one},000=${ZERO}, ZERO=${0z}, 0z=100000000,  one=1,1=${one},2=2,200=two hundred", 10, new StringUtils.IndexContext.Range(5, 11));


    }

    private StringUtils.IndexContext testIndexReplacement(String pattern, final String dataStr, int indexToTest, StringUtils.IndexContext.Range range) {
        Map<String, String> data = new HashMap<String, String>();

        if (dataStr != null) {
            String[] list = dataStr.split(",");
            for (String item : list) {
                String[] pair = item.split("=");
                if (pair.length > 1) {
                    data.put(pair[0].trim(), pair[1]);
                }
            }
        }

        StringUtils.IndexContext context = testIndexReplacement(pattern, data, indexToTest, range);
        return context;

    }


    private StringUtils.IndexContext testIndexReplacement(String pattern, final Map<String, String> data, int indexToTest, StringUtils.IndexContext.Range range) {

        IPropertyFinder keys = new IPropertyFinder() {
            @Override
            public String findProperty(String p1) {
                if (data == null) {
                    return null;
                }
                return data.get(p1);
            }
        };
        StringUtils.IndexContext context = StringUtils.replaceWithContext("${", "}", pattern, keys, true, true, true);
        return testContext(context, indexToTest, range);

    }

    private StringUtils.IndexContext testContext(StringUtils.IndexContext context, int indexToTest, StringUtils.IndexContext.Range range) {
        System.out.println(context.finalString);
        System.out.println(context.ranges);

        StringUtils.IndexContext.Range range1 = context.getSourceIndexRecursively(indexToTest);
        System.out.println("Found Index range:" + range1 + " for given index:" + indexToTest);
        Assert.assertTrue(range1.from == ((range == null) ? indexToTest : range.from));

        Assert.assertTrue(range1.to == ((range == null) ? indexToTest : range.to));

        return context;

    }


    @Test
    public void testStartAndEnding() {

        Assert.assertTrue(wordOK("தமிழ்"));
        Assert.assertTrue(wordOK("இடப்பா"));
        Assert.assertFalse(wordOK("டப்பா"));
        Assert.assertFalse(wordOK("வுடி"));
        Assert.assertFalse(wordOK("றிடிசும்"));
        Assert.assertFalse(wordOK("கப்"));
        Assert.assertTrue(wordOK("கப்பு"));
        Assert.assertTrue(wordOK("புஷ்பம்"));
    }


    private boolean wordOK(String word) {
        TamilWord w = TamilWord.from(word);

        if (w.getFirst().isTamilLetter() && w.getLast().isTamilLetter()) {
            return w.getFirst().asTamilCharacter().isWordToStartWith() && w.getLast().asTamilCharacter().isWordToEndWith();
        } else {
            return false;
        }
    }

}
