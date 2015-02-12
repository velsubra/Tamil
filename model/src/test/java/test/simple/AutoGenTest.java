package test.simple;

import junit.framework.Assert;
import tamil.lang.TamilWord;
import my.interest.lang.tamil.internal.api.DefinitionFactory;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import my.interest.lang.tamil.bean.MultipleWordSplitResult;
import my.interest.lang.tamil.generated.types.DerivedValue;
import my.interest.lang.tamil.generated.types.DerivedValues;
import my.interest.lang.util.TimeUtils;
import org.junit.Test;

import java.io.File;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class AutoGenTest {
    static {
        new File("/Users/velsubra/Downloads/i18n/i18n.xml").delete();
    }

    @Test
    public void test_Show_How_Bad_Unicode_For_Tamil_Is() throws Exception {


        String tamil = "செம்மொழி";
        boolean endSWithE = tamil.endsWith("இ");  // This returns false
        boolean startsWithCH = tamil.startsWith("ச்"); // This too returns false
        boolean startsWithCHA = tamil.startsWith("ச"); // This  returns true
        int length = tamil.length();     // The number of letters in the word
        System.out.println("word=" + tamil);
        System.out.println("endSWithE=" + endSWithE);
        System.out.println("startsWithCH=" + startsWithCH);
        System.out.println("startsWithCHA=" + startsWithCHA);
        System.out.println("length=" + length);

        System.out.println("\n--Now Using tamil.lang.TamilWord---");

        TamilWord word = TamilWord.from(tamil);
        endSWithE = word.getLast().asTamilCharacter().getUyirPart().toString().equals("இ");  // This returns true
        startsWithCH = word.getFirst().asTamilCharacter().getMeiPart().toString().equals("ச்"); // This too returns true
        startsWithCHA = word.getFirst().toString().equals("ச");    //This returns false rightly
        length = word.size();

        System.out.println("word=" + word);
        System.out.println("endSWithE=" + endSWithE);
        System.out.println("startsWithCH=" + startsWithCH);
        System.out.println("startsWithCHA=" + startsWithCHA);
        System.out.println("length=" + length);


    }


    @Test
    public void tesSimpleSplitResultt2() throws Exception {
        PersistenceInterface.get().getAllRootWords();
        Thread.currentThread().sleep(20000);
        MultipleWordSplitResult ret = HandlerFactory.parse("மடித்துப்பிடித்துக்கொண்டேன்", 4);
        System.out.println("------------------");
        System.out.println(ret.getSplit());
    }

    @Test
    public void test1() throws Exception {
        Date date = new Date();
        DerivedValues values = DefinitionFactory.generateVinaimuttu("vinaimuttu", "ஏவு", true).getRows().get(6).getFuture();
        for (DerivedValue d : values.getList()) {
            System.out.println(d.getValue());
            System.out.println(d.getEquation());
            Assert.assertEquals("ஏவுவர்", d.getValue());
        }

        values = DefinitionFactory.generateVinaimuttu("vinaimuttu", "வா", false).getRows().get(6).getFuture();
        for (DerivedValue d : values.getList()) {
            System.out.println(d.getValue());
            System.out.println(d.getEquation());
            Assert.assertEquals("வருவர்", d.getValue());
        }

        values = DefinitionFactory.generateVinaimuttu("vinaimuttu", "வா", false).getRows().get(7).getPresent();
        Assert.assertEquals("வருகிறார்", values.getList().get(0).getValue());
        for (DerivedValue d : values.getList()) {
            System.out.println(d.getValue());
            System.out.println(d.getEquation());

        }

        System.out.println(TimeUtils.millisToLongDHMS(new Date().getTime() - date.getTime()));

    }


}
