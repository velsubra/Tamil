package test.sound;

import my.interest.lang.tamil.EzhuththuUtils;
import my.interest.lang.tamil.parser.impl.TamilSoundListener;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.sound.AtomicSound;
import tamil.lang.sound.TamilSoundLookUpContext;

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
        int count = 0;
        for (AtomicSound s: list) {
            count   ++;

            System.out.println(count +":" + s.getWord());
        }
    }

    @Test
    public void testRead() throws Exception {
       // TamilWord word = TamilWord.from("", true);
        TamilWord word = TamilFactory.getTransliterator(null).transliterate("kangai");
        System.out.println("Text size:" + word.size());

        TamilSoundListener listener = new TamilSoundListener();
        EzhuththuUtils.readSound(word, listener);
        List<AtomicSound> list = listener.get();
        System.out.println("Sound Units:" + list.size());
        System.out.println("");
        for (AtomicSound s: list) {
            System.out.println(s.getWord());
        }


    }


}
