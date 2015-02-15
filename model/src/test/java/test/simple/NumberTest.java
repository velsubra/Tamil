package test.simple;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.number.NumberReader;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NumberTest {

    @Test
    public void testRead0() throws Exception {
        NumberReader reader = TamilFactory.getNumberReader();
        TamilWord word = reader.readNumber(null);
        System.out.println(word);
        Assert.assertEquals(0, word.size());


        word = reader.readNumber("190");
        System.out.println(word);
        Assert.assertEquals("நூற்றுத்தொண்ணூறு", word.toString());

        word = reader.readNumber("10010");
        System.out.println(word);
        Assert.assertEquals("பத்தாயிரத்துப்பத்து", word.toString());


        word = reader.readNumber(".000000000101");
        System.out.println(word);
        Assert.assertEquals("சுழி புள்ளி சுழி சுழி சுழி சுழி சுழி சுழி சுழி சுழி சுழி ஒன்று சுழி ஒன்று", word.toString());

        word = reader.readNumber("000000000101");
        System.out.println(word);
        Assert.assertEquals("நூற்று ஒன்று", word.toString());

        word = reader.readNumber("100000000000101");
        System.out.println(word);
        Assert.assertEquals("ஒரு கோடியே கோடியே நூற்று ஒன்று", word.toString());

        word = reader.readNumber("1000000100");
        System.out.println(word);
        Assert.assertEquals("நூறுகோடியே நூறு", word.toString());

        word = reader.readNumber("100000010");
        System.out.println(word);
        Assert.assertEquals("பத்துகோடியே பத்து", word.toString());

        word = reader.readNumber("100000000");
        System.out.println(word);
        Assert.assertEquals("பத்துகோடி", word.toString());

        word = reader.readNumber("10000000");
        System.out.println(word);
        Assert.assertEquals("ஒரு கோடி", word.toString());

        word = reader.readNumber("10000001");
        System.out.println(word);
        Assert.assertEquals("ஒரு கோடியே ஒன்று", word.toString());

        word = reader.readNumber("200091");
        System.out.println(word);
        Assert.assertEquals("இரண்டு இலட்சத்துத்தொண்ணூற்றொன்று", word.toString());


        word = reader.readNumber("100011");
        System.out.println(word);
        Assert.assertEquals("ஓர் இலட்சத்துப்பதினொன்று", word.toString());


        word = reader.readNumber("100001");
        System.out.println(word);
        Assert.assertEquals("ஓர் இலட்சத்து ஒன்று", word.toString());



        word = reader.readNumber("10000");
        System.out.println(word);
        Assert.assertEquals("பத்தாயிரம்", word.toString());

        word = reader.readNumber("10001");
        System.out.println(word);
        Assert.assertEquals("பத்தாயிரத்து ஒன்று", word.toString());



        word = reader.readNumber("1000");
        System.out.println(word);
        Assert.assertEquals("ஓர் ஆயிரம்", word.toString());


        word = reader.readNumber("43400434.0");
        System.out.println(word);
        Assert.assertEquals("நான்கு கோடியே முப்பத்துநான்கு இலட்சத்து நானூற்று முப்பத்துநான்கு புள்ளி சுழி", word.toString());


        word = reader.readNumber("43400434.");
        System.out.println(word);
        Assert.assertEquals("நான்கு கோடியே முப்பத்துநான்கு இலட்சத்து நானூற்று முப்பத்துநான்கு", word.toString());


        word = reader.readNumber("43400434");
        System.out.println(word);
        Assert.assertEquals("நான்கு கோடியே முப்பத்துநான்கு இலட்சத்து நானூற்று முப்பத்துநான்கு", word.toString());


        word = reader.readNumber("21.12");
        System.out.println(word);
        Assert.assertEquals("இருபத்தொன்று புள்ளி ஒன்று இரண்டு", word.toString());

        word = reader.readNumber("444");
        System.out.println(word);
        Assert.assertEquals("நானூற்று நாற்பத்துநான்கு", word.toString());

        word = reader.readNumber("167");
        System.out.println(word);
        Assert.assertEquals("நூற்று அறுபத்தேழு", word.toString());


        word = reader.readNumber("100");
        System.out.println(word);
        Assert.assertEquals("நூறு", word.toString());

        word = reader.readNumber("99");
        System.out.println(word);
        Assert.assertEquals("தொண்ணூற்றொன்பது", word.toString());


        word = reader.readNumber("56");
        System.out.println(word);
        Assert.assertEquals("ஐம்பத்தாறு", word.toString());


        word = reader.readNumber("50");
        System.out.println(word);
        Assert.assertEquals("ஐம்பது", word.toString());


        word = reader.readNumber("19");
        System.out.println(word);
        Assert.assertEquals("பத்தொன்பது", word.toString());

        word = reader.readNumber("12");
        System.out.println(word);
        Assert.assertEquals("பன்னிரண்டு", word.toString());


        word = reader.readNumber("11");
        System.out.println(word);
        Assert.assertEquals("பதினொன்று", word.toString());


        word = reader.readNumber("10");
        System.out.println(word);
        Assert.assertEquals("பத்து", word.toString());

        word = reader.readNumber("");
        System.out.println(word);
        Assert.assertEquals("சுழி", word.toString());

        word = reader.readNumber("0");
        System.out.println(word);
        Assert.assertEquals("சுழி", word.toString());


        word = reader.readNumber("1");
        System.out.println(word);
        Assert.assertEquals("ஒன்று", word.toString());

        word = reader.readNumber("3");
        System.out.println(word);
        Assert.assertEquals("மூன்று", word.toString());

        word = reader.readNumber("9");
        System.out.println(word);
        Assert.assertEquals("ஒன்பது", word.toString());


    }
}
