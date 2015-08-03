package test;

import org.junit.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;

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




    private boolean wordOK(String word)  {
        TamilWord w = TamilWord.from(word);

        if (w.getFirst().isTamilLetter() && w.getLast().isTamilLetter()) {
               return w.getFirst().asTamilCharacter().isWordToStartWith() && w.getLast().asTamilCharacter().isWordToEndWith();
        }  else {
            return  false;
        }
    }

}
