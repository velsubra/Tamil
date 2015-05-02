package test.sound;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang.TamilCompoundCharacter;
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

    @Test
    public void testSuggestionCode1() {
        testSugestion("கழட்டு","கழற்று");
        testSugestion("கழத்து","கழற்று");

        testSugestion("கழத்து","கழற்று");

        testSugestion("kalai", "kalhai");

        testSugestion("amma", "ammaa");
        testSugestion("paarkka", "paarrka");

    }

    static void testSugestion(String first, String second)  {
        TamilWord f = TamilFactory.getTransliterator(null).transliterate(first);
        TamilWord s = TamilFactory.getTransliterator(null).transliterate(second);
        System.out.println(f +":" +s);
        Assert.assertEquals(f.suggestionHashCode(), s.suggestionHashCode());


    }

    @Test
    public void testkalappadam() {
         StringBuffer buffer = new StringBuffer();
//        buffer.append((char)TamilSimpleCharacter.KA.getValue());
//        buffer.append((char)'\u0B82');
//       buffer.append((char) TamilSimpleCharacter.KA.getValue());
        buffer.append((char)'\u0B82');
//        buffer.append((char)TamilSimpleCharacter.KA.getValue());
//        buffer.append((char)'\u0B82');
//
//        buffer.append("=");
//        buffer.append(TamilCompoundCharacter.IK);
//        buffer.append((char)TamilSimpleCharacter.KA.getValue());
//        buffer.append((char)'\u0008' );
        buffer.append((char)TamilCompoundCharacter.PULLI);
        System.out.println(buffer.toString());

    }
}
