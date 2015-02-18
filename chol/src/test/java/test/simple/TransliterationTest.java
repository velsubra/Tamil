package test.simple;

import junit.framework.Assert;
import tamil.lang.TamilPara;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TransliterationTest {

    @Test
    public void testTrans() throws Exception {
        TamilWord words =  EnglishToTamilCharacterLookUpContext.getBestMatch("") ;
        System.out.println(words);
        Assert.assertEquals("",words.toString());

    }

    @Test
    public void testTrans1() throws Exception {
        TamilWord words =  EnglishToTamilCharacterLookUpContext.getBestMatch("ingea thaddungalh!.") ;
        System.out.println(words);
        Assert.assertEquals("இங்கே தட்டுங்கள்!.",words.toString());

    }

    @Test
    @Ignore
    public void testTrans1_1() throws Exception {
        TamilWord words =  EnglishToTamilCharacterLookUpContext.getBestMatch("இngகே thaட்dunggalh!.") ;
        System.out.println(words);
        Assert.assertEquals("இங்கே தட்டுங்கள்!.",words.toString());
        Assert.assertEquals("இங்கே தட்டுங்கள்!.", TamilPara.from(words.toString()).toString());
        Assert.assertEquals(true, TamilWord.from( words.toString()).isPure());
    //    Assert.assertEquals(true, words.isPure());

    }

    @Test
    public void testTrans1_2() throws Exception {
        TamilWord words =  EnglishToTamilCharacterLookUpContext.getBestMatch("இங்கே தட்டுங்கள்") ;
        System.out.println(words);
        System.out.println(TamilWord.from(words.toString()));
        Assert.assertEquals(true, TamilWord.from(words.toString()).isPure());
       // Assert.assertEquals(true, words.isPure());

    }

    @Test
    public void testTrans2() throws Exception {
        TamilWord words =  EnglishToTamilCharacterLookUpContext.getBestMatch(".") ;
        System.out.println(words);
        Assert.assertEquals(".",words.toString());

    }


    @Test
    public void testTrans3() throws Exception {
        TamilWord words =  EnglishToTamilCharacterLookUpContext.getBestMatch("334533443") ;
        System.out.println(words);
        Assert.assertEquals("334533443",words.toString());

    }


    @Test
    public void testTrans4() throws Exception {
        TamilWord words =  EnglishToTamilCharacterLookUpContext.getBestMatch("amma") ;
        System.out.println(words);
        Assert.assertEquals("அம்ம",words.toString());

        words =  EnglishToTamilCharacterLookUpContext.getBestMatch("ammaa") ;
        System.out.println(words);
        Assert.assertEquals("அம்மா",words.toString());

    }
}
