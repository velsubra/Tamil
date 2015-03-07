package test.sound;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TextTest {
    static {
        TamilFactory.init();
    }

    @Test
    public void testReverse() {
        TamilWord word = TamilWord.from("குதரா");
        TamilWord r = TamilWord.reverse(word);
        Assert.assertEquals("ராதகு", r.toString());

        Assert.assertEquals(word.toString(), TamilWord.reverse(r).toString());

        System.out.println(word.endsWith(TamilSimpleCharacter.THA));
        Assert.assertEquals(false,word.endsWith(TamilSimpleCharacter.THA));



    }
}
