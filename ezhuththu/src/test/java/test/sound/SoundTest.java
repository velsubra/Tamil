package test.sound;

import junit.framework.Assert;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.parser.impl.TamilSoundListener;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.sound.AtomicSound;
import tamil.lang.sound.TamilSoundLookUpContext;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class SoundTest {
    static {
        TamilFactory.init();
    }

    @Test
    public void testList() throws Exception {
        List<AtomicSound> list = TamilSoundLookUpContext.getAllTamilSounds();
        Collections.sort(list);
        int count = 0;
        for (AtomicSound s : list) {
            count++;

            System.out.println(count + ":" + s.getWord());
        }
    }


    @Test
    public void testRead1() throws Exception {
        // TamilWord word = TamilWord.from("", true);
        TamilWord word = TamilFactory.getTransliterator(null).transliterate("kalki");
        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        TamilWord ret = listener.getSynthesizedWord();
        System.out.println("Text size:" + word.size());
        System.out.println("Sound Units:" + listener.get().size());
        Assert.assertEquals(word.size(), listener.get().size());
        System.out.println("ret:" + ret);
        Assert.assertEquals("கல்கி/", ret.toString());

    }


    @Test
    public void testRead2() throws Exception {
        // TamilWord word = TamilWord.from("", true);
        TamilWord word = TamilFactory.getTransliterator(null).transliterate("kaakam");
        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        TamilWord ret = listener.getSynthesizedWord();
        System.out.println("Text size:" + word.size());
        System.out.println("Sound Units:" + listener.get().size());
        Assert.assertEquals(word.size(), listener.get().size());
        System.out.println("ret:" + ret);
        Assert.assertEquals("காக\\ம்", ret.toString());

    }


    @Test
    public void testRead3() throws Exception {

        TamilWord word = TamilFactory.getTransliterator(null).transliterate("kangai");
        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        TamilWord ret = listener.getSynthesizedWord();
        System.out.println("Text size:" + word.size());
        System.out.println("Sound Units:" + listener.get().size());
        Assert.assertEquals(word.size() -1, listener.get().size());
        System.out.println("ret:" + ret);
        Assert.assertEquals("கங்கை", ret.toString());

    }



    @Test
    public void testRead4() throws Exception {

        TamilWord word = TamilFactory.getTransliterator(null).transliterate("kaakkaa");
        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        TamilWord ret = listener.getSynthesizedWord();
        System.out.println("Text size:" + word.size());
        System.out.println("Sound Units:" + listener.get().size());
        Assert.assertEquals(word.size() -1, listener.get().size());
        System.out.println("ret:" + ret);
        Assert.assertEquals("காக்கா", ret.toString());

    }

    @Test
    public void testRead5() throws Exception {

        TamilWord word = TamilFactory.getTransliterator(null).transliterate("akkampakkaththil");
        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        TamilWord ret = listener.getSynthesizedWord();
        System.out.println("Text size:" + word.size());
        System.out.println("Sound Units:" + listener.get().size());
        Assert.assertEquals(word.size() -4, listener.get().size());
        System.out.println("ret:" + ret);
        Assert.assertEquals("அக்கம்பக்கத்தில்", ret.toString());

    }


    @Test
    public void testRead() throws Exception {
        // TamilWord word = TamilWord.from("", true);
        TamilWord word = TamilFactory.getTransliterator(null).transliterate("kalki");
        System.out.println("Text size:" + word.size());

        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        List<AtomicSound> list = listener.get();
        System.out.println("Sound Units:" + list.size());
        System.out.println("");
        for (AtomicSound s : list) {
            System.out.println(s.getWord());
        }


    }


}
