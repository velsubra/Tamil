package test;

import junit.framework.Assert;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.join.KnownWordsJoiner;
import tamil.lang.api.join.WordsJoiner;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.idai.Kalh;
import tamil.lang.known.non.derived.Peyarchchol;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class JoinerTest {

    static {
        TamilFactory.init();
    }


    @Test
    public void testJoin1() {
        WordsJoiner joiner =  TamilFactory.createWordJoiner(TamilWord.from("பூ"));
        joiner.addVaruMozhi(TamilWord.from("கள்"));
        TamilWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "பூகள்");

    }

    @Test
    public void testJoin2() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("பூ"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "பூக்கள்");

    }

    @Test
    public void testJoin3() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("பழம்"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "பழங்கள்");

    }

    @Test
    public void testJoin4() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("கல்"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "கற்கள்");

    }

    @Test
    public void testJoin5() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("முள்"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "முட்கள்");

    }

    @Test
    public void testJoin6() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("சங்கு"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "சங்குகள்");

    }

    @Test
    public void testJoin7() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("வீடு"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "வீடுகள்");

    }

    @Test
    public void testJoin8() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("பாட்டு"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "பாட்டுகள்");

    }

    @Test
    public void testJoin9() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("வினா"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "வினாக்கள்");

    }

    @Test
    public void testJoin10() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("உலை"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "உலைகள்");

    }


    @Test
    public void testJoin11() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("கை"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "கைகள்");

    }


    @Test
    public void testJoin12() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("ஈ"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "ஈக்கள்");

    }


    @Test
    public void testJoin13() {
        KnownWordsJoiner joiner =  TamilFactory.createKnownWordJoiner(new Peyarchchol( TamilWord.from("பிணி"), 0));
        joiner.addVaruMozhi(Kalh.KALH, KnownWordsJoiner.TYPE.ALVAZHI);
        IKnownWord ret = joiner.getSum();
        System.out.println(ret);
        Assert.assertEquals(ret.toString(), "பிணிகள்");

    }
}
