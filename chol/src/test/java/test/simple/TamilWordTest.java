package test.simple;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.exception.TamilPlatformException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilWordTest {
    static {
        TamilFactory.init();
    }

    @Test
    public void testIndex1() {
        TamilWord w = TamilWord.from("வந்ததால்");
        TamilWord part = TamilWord.from("ஆல்");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(3, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(-1, index);


    }


    @Test
    public void testIndex2() {
        TamilWord w = TamilWord.from("வந்ததால்");
        TamilWord part = TamilWord.from("ல்");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(4, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(4, index);


    }


    @Test
    public void testIndex3() {
        TamilWord w = TamilWord.from("வந்ததால்");
        TamilWord part = TamilWord.from("வந்ததால்");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(0, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(0, index);


    }

    @Test
    public void testIndex4() {
        TamilWord w = TamilWord.from("ந்ததால்");
        TamilWord part = TamilWord.from("வந்ததால்");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(-1, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(-1, index);


    }

    @Test
    public void testIndex5() {
        TamilWord w = TamilWord.from("வந்ததால்");
        TamilWord part = TamilWord.from("ந்ததால்");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(1, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(1, index);


    }


    @Test
    public void testIndex6() {
        TamilWord w = TamilWord.from("வந்துகொண்டிருப்பதனால்தான்");
        TamilWord part = TamilWord.from("ஆல்");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(10, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(-1, index);


    }

    @Test
    public void testIndex7() {
        TamilWord w = TamilWord.from("சொல்வேனென்பதுஞ்சரியே");
        TamilWord part = TamilWord.from("என்பது");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(3, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(-1, index);

        part = TamilWord.from("ச்");
        index = w.indexOf(part, false);
        Assert.assertEquals(0, index);


    }

    @Test
    public void testIndex8() {

        TamilWord w = TamilWord.from("ச");
        TamilWord part = TamilWord.from("ச்");
        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(0, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(-1, index);


        part = TamilWord.from("அ");

        index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(0, index);

        index = w.indexOf(part, true);
        System.out.println(index);
        Assert.assertEquals(-1, index);


    }


    @Test
    public void testsubstring0() {

        TamilWord w = TamilWord.from("சொல்வேனென்பதுஞ்சரியே");
        TamilWord part = w.subWord(3, 3);
        System.out.println("PART:" + part);
        Assert.assertEquals(new TamilWord().toString(), part.toString());


        part = w.subWord(3, 4);
        System.out.println("PART:" + part);
        Assert.assertEquals("னெ", part.toString());


    }

    @Test
    public void testsubstring1() {

        TamilWord w = TamilWord.from("அடில்");
        TamilWord part = TamilWord.from("இல்");
        TamilWord with = TamilWord.from("ஆள்");

        int index = w.indexOf(part, false);
        System.out.println(index);
        Assert.assertEquals(1, index);

        TamilWord backup = w.duplicate();
        index = w.replace(part, with, true);
        Assert.assertEquals(-1, index);
        System.out.println(w);
        Assert.assertEquals(backup, w);

        index = w.replace(part, with, false);
        Assert.assertEquals(1, index);
        System.out.println(w);
        TamilWord expected = TamilWord.from("அடாள்");
        Assert.assertEquals(expected, w);

        expected.replace(with, part, false);
        Assert.assertEquals(expected, backup);


    }


    @Test
    public void testsubstring2() throws Exception {

        TamilWord w = TamilWord.from("");
        TamilWord part = TamilWord.from("இல்");

        int index = w.indexOf(part, false);
        Assert.assertEquals(-1, index);


        part = TamilWord.from("");

        index = w.indexOf(part, false);
        Assert.assertEquals(-1, index);

        w = TamilWord.from("அடில்");
        try {
            w.replace(part, part, false);
            throw new Exception("test failed");
        } catch (TamilPlatformException e) {

        }

    }

    @Test
    public void testreplace2() {



        TamilWord w = TamilWord.from("இதனால்வந்தது");
        TamilWord part = TamilWord.from("ஆல்");
        TamilWord with = TamilWord.from("உடன்");

        testReplace(w.toString(), part.toString(),with.toString(), false,"இதனுடன்வந்தது");

    }

    @Test
    public void testreplace3() {



        TamilWord w = TamilWord.from("பழத்துடன்");
        TamilWord part = TamilWord.from("அத்து");
        TamilWord with = TamilWord.from("");

        testReplace(w.toString(), part.toString(),with.toString(), false,"பழ்டன்");
        testReplace("padam", "padam","a", false,"a");
        testReplace("படம் ", "படம்","", false," ");
        testReplace("aபடம் ", "படம்","", false,"a ");
        testReplace("aபடம் ", "aப","", false,"டம் ");
        testReplace("aபடம் ", "aப்","", false,"அடம் ");
        testReplace("பழத்துடன்", "அத்து","a", true,"பழத்துடன்");
        testReplace("பழத்துடன்", "த்து","a", true,"பழaடன்");
        testReplace("இனிமையாக பழகுவோம்", "பழகு","ஆடு", true,"இனிமையாக ஆடுவோம்");

        testReplace("நான் சொன்னேன் இல்லையா?", "ஏன் இல்ல்","", false,"நான் சொன்ன்ஐயா?");

        testReplace("இருக்கின்றோம்", "ஓம்","ஆன்", false,"இருக்கின்றான்");
        testReplace("இருக்கின்றோம்", "க்க்","ஆன்", false,"இருஆனின்றோம்");

        testReplace("ababab", "ab", "",true, "");
        testReplace("abababa", "ab", "",true, "a");
        testReplace("abababa", "ab", "ab",true, "abababa");

        testReplace("அள்ளேன்", "ள்ள்", "ள்",false, "அளேன்", true);
        testReplace("கலு", "உ", "",false, "கல்", false);

        testReplace("கத்திரி", "இரி", "அரி",false, "கத்தரி", false);


    }

    private void testReplace(String source, String find, String replace, boolean fullmatch, String expected) {
        testReplace(source,find,replace,fullmatch,expected,false);

    }


    private void testReplace(String source, String find, String replace, boolean fullmatch, String expected, boolean joinlast) {
        TamilWord w = TamilWord.from(source, true);
        TamilWord backup = w.duplicate();
        TamilWord what = TamilWord.from(find, true);
        TamilWord with = TamilWord.from(replace,true);
        TamilWord ex = TamilWord.from(expected,true);
        w.replaceAll(0,what, with, fullmatch, joinlast);
        Assert.assertEquals(ex.toString(), w.toString());
        System.out.println(w);
        if (!with.isEmpty() && !joinlast) {
            w.replace(with, what, fullmatch);
            Assert.assertEquals(w.toString(), backup.toString());
        }


    }

}
