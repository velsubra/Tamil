package test;

import junit.framework.Assert;
import my.interest.lang.tamil.impl.dictionary.DefaultPlatformDictionaryBase;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class CompareTest {

    static {
        TamilFactory.init();
    }


    @Test
    public void testCompare() {
        TamilWord w = TamilWord.from("நேரத்தை");
        TamilWord ath = TamilWord.from("அத்தை");
        int val = TamilWord.reverse(w).compareTo(TamilWord.reverse(ath));
        System.out.println(val);
        Assert.assertEquals(1, val);
        boolean end = w.endsWith(ath, false);
        System.out.println(end);
        Assert.assertTrue(end);

        val = TamilWord.reverse(ath).compareTo(TamilWord.reverse(w));
        System.out.println(val);

        Assert.assertEquals(-1, val);
    }

    @Test
    public void testSort() {

        List<IKnownWord> list = new ArrayList<IKnownWord>();
        list.add(Kalh.KALH);
        list.add(Thaan.THAAN);
        list.add(Um.UM);
        list.add(Aththu.ATHTHU);
        list.add(Ottu.IK);
        list.add(Ottu.ICH);
        list.add(Ottu.ITH);
        list.add(Ottu.IP);


        list.add(new Peyarchchol(TamilWord.from("சிவப்பு"), 0));
        list.add(new Peyarchchol(TamilWord.from("சேர்ப்பு"), 0));
        list.add(new Peyarchchol(TamilWord.from("ச்ச்"), 0));
        list.add(new Peyarchchol(TamilWord.from("அம்மா"), 0));
        list.add(new Peyarchchol(TamilWord.from("உப்பு"), 0));
        list.add(new Peyarchchol(TamilWord.from("அகம்"), 0));
        list.add(new Peyarchchol(TamilWord.from("ஆடு"), 0));
        list.add(new Peyarchchol(TamilWord.from("காடு"), 0));
        list.add(new Peyarchchol(TamilWord.from("ஃ"), 0));
        Collections.sort(list);

        DefaultPlatformDictionaryBase dictionaryBase = new DefaultPlatformDictionaryBase() {

        };
        System.out.println(dictionaryBase.size());
        for (IKnownWord k : list) {
            System.out.println(k);
            dictionaryBase.add(k);

        }

        TamilWord uppu = TamilWord.from("உப்பு");
        TamilWord thaan = TamilWord.from("தான்");
        System.out.println(uppu.getSoundSizeDigest());
        System.out.println(uppu.getCharacterTypeDigest());
        System.out.println(uppu.getVowelDigest());
        System.out.println(uppu.getConsonantDigest());
        System.out.println(uppu.getSoundStrengthDigest());

        System.out.println(uppu.compareTo(thaan));

        System.out.println(dictionaryBase.size() + " :" + list.size());


        TamilWord ik = TamilWord.from("க்");
        TamilWord ich = TamilWord.from("ச்");

        System.out.println("ik to ich:" + ik.compareTo(ich));
        for (IKnownWord k : list) {

            System.out.println(k + ":" + dictionaryBase.lookup(k.getWord()));
        }
        System.out.println(dictionaryBase.lookup(TamilWord.from("அத்து")));

        System.out.println(dictionaryBase.search(TamilWord.from(""), false, 16, null));

        System.out.println(dictionaryBase.peek(Ottu.IK.getWord()));


    }

}
