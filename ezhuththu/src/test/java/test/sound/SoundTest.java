package test.sound;

import junit.framework.Assert;
import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.parser.impl.TamilSoundListener;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.sound.AtomicSound;
import tamil.lang.sound.MixingAudioInputStream;
import tamil.lang.sound.TamilSoundLookUpContext;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.SequenceInputStream;
import java.util.*;

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
        Set<String> set = new HashSet<String>();
        for (AtomicSound s : list) {
            count++;
            String eng = s.getWord().translitToEnglish();
            System.out.println(count + ":" + s.getWord() +" English:" + eng);
            if (set.contains(eng)) {
                System.out.println("-----------Duplicate--------:" + eng);
            } else {
                set.add(eng);
            }
        }
        System.out.println("Count:" + set.size());
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
//        Assert.assertEquals("கல்கி/", ret.toString());

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
        Assert.assertEquals("காகம்", ret.toString());

    }


    @Test
    public void testRead3() throws Exception {

        TamilWord word = TamilFactory.getTransliterator(null).transliterate("kangai");
        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        TamilWord ret = listener.getSynthesizedWord();
        System.out.println("Text size:" + word.size());
        System.out.println("Sound Units:" + listener.get().size());
//        Assert.assertEquals(word.size() -1, listener.get().size());
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
//        Assert.assertEquals(word.size() -1, listener.get().size());
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
//        Assert.assertEquals(word.size() -4, listener.get().size());
        System.out.println("ret:" + ret);
        Assert.assertEquals("அக்கம்பக்கத்தில்", ret.toString());

    }


    @Test
    public void testRead() throws Exception {
        // TamilWord word = TamilWord.from("", true);
        TamilWord word = TamilFactory.getTransliterator(null).transliterate("padambaarththu katahisol");
        System.out.println("Text size:" + word.size());

        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        List<AtomicSound> list = listener.get();
        System.out.println("Sound Units:" + list.size());
        System.out.println("");
        List<AudioInputStream> collections = new ArrayList();
        int count = 0;
        for (AtomicSound s : list) {
            System.out.println(s.getWord());
            Thread.sleep(100);
//            if (count ==0) {
//                Thread.sleep(10);
//            }
//            if (count ==1) {
//                Thread.sleep(200);
//            }
//            if (count ==2) {
//                Thread.sleep(200);
//            }
            count ++;
            AudioInputStream  stream = AudioSystem.getAudioInputStream(s.getDataInputStream());


            collections.add( stream);

            DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(stream);
            clip.start();


        }
//        if (collections.size() > 0) {
//            MixingAudioInputStream appaa = new MixingAudioInputStream(collections.get(0).getFormat(), collections);
//            DataLine.Info info = new DataLine.Info(Clip.class, appaa.getFormat());
//            Clip clip = (Clip) AudioSystem.getLine(info);
//            clip.open(appaa);
//            clip.start();
//        }


    }



    @Test
    public void testMix() throws Exception {
        // TamilWord word = TamilWord.from("", true);
        TamilWord word = TamilFactory.getTransliterator(null).transliterate("iththudan enathu kanhakkai mudikkirrean");
        System.out.println("Text size:" + word.size());
        System.out.println("word :" + word);

        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        List<AtomicSound> list = listener.get();
        System.out.println("Sound Units:" + list.size());
        System.out.println("");
        List<AudioInputStream> collections = new ArrayList();
        long frameLength = 0;
        for (AtomicSound s : list) {
            AudioInputStream ai = AudioSystem.getAudioInputStream(s.getDataInputStream());
            collections.add(ai);
            frameLength += ai.getFrameLength();

            System.out.println(s.getWord().toString());
        }



        if (collections.size() > 0) {
            System.out.println("======>"+collections.size());
            SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(collections));
            AudioInputStream appaa = new AudioInputStream(sequenceInputStream, collections.get(0).getFormat(), frameLength);
            DataLine.Info info = new DataLine.Info(Clip.class, appaa.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(appaa);
            clip.start();
            Thread.currentThread().sleep(1000);
            while (clip.isRunning()) {
                Thread.currentThread().sleep(100);
            }
        }


    }



}
