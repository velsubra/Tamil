package test.sound;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.WordsJoiner;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PunharchiTest {

    static {
        TamilFactory.init();
    }



    @Test
    public void testKalhiMannnaduppu0() {
       WordsJoiner joiner = TamilFactory.createWordJoiner(TamilFactory.getTransliterator(null).transliterate("kalhi")) ;
        joiner.addVaruMozhi(TamilFactory.getTransliterator(null).transliterate("manh"));
        joiner.addVaruMozhi(TamilFactory.getTransliterator(null).transliterate("aduppu"));
        String result = joiner.getSum().toString();
        System.out.println(result);
        Assert.assertEquals("களிமணடுப்பு",result);
    }

    @Test
    public void testKalhiMannnaduppu1() {
        WordsJoiner joiner = TamilFactory.createWordJoiner(TamilFactory.getTransliterator(null).transliterate("manh")) ;
        joiner.addVaruMozhi(TamilFactory.getTransliterator(null).transliterate("aduppu"));
        joiner.addNilaiMozhi(TamilFactory.getTransliterator(null).transliterate("kalhi"));

        String result = joiner.getSum().toString();
        System.out.println(result);
        Assert.assertEquals("களிமண்ணடுப்பு",result);
    }

    @Test
    public void testKalhiMannnaduppu2() {
        WordsJoiner joiner = TamilFactory.createWordJoiner(TamilFactory.getTransliterator(null).transliterate("aduppu")) ;
        joiner.addNilaiMozhi(TamilFactory.getTransliterator(null).transliterate("manh"));
        TamilWord kalhi = TamilFactory.getTransliterator(null).transliterate("kalhi");
        joiner.addNilaiMozhi(kalhi);
        String result = joiner.getSum().toString();
        System.out.println(result);
        Assert.assertEquals("களிமண்ணடுப்பு",result);
    }
}
