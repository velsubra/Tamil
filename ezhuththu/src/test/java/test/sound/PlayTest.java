package test.sound;

import my.interest.lang.tamil.TamilUtils;
import org.junit.Test;
import tamil.lang.TamilFactory;
import tamil.lang.sound.MixingAudioInputStream;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class PlayTest {

    static {
        TamilFactory.init();
    }

    public static InputStream asByteArrayInputStream(InputStream in) {
        return new ByteArrayInputStream(TamilUtils.readAllFrom(in,false));
    }

    @Test
    public void testPlay_a_10() throws Exception {
        AudioInputStream stream = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/a_1.0.wav")));
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        clip.start();

    }

    @Test
    public void testPlay_ppaa_25() throws Exception {
        AudioInputStream stream = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/ppaa_2.5.wav")));
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        clip.start();
    }


    @Test
    public void testPlay_Together_mixer() throws Exception {
        AudioInputStream a = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/a_1.0.wav")));
        AudioInputStream ppaa = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/ppaa_2.5.wav")));
        Collection collections = new ArrayList();
        collections.add(a);
        collections.add(ppaa);

        MixingAudioInputStream appaa = new MixingAudioInputStream(a.getFormat(), collections);

        DataLine.Info info = new DataLine.Info(Clip.class, appaa.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(appaa);
        clip.start();

    }




    @Test
    public void testPlay_Together_mixer_ammaa() throws Exception {
        AudioInputStream a = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/a_1.0.wav")));
        AudioInputStream ppaa = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/mmaa_2.5.wav")));
        Collection collections = new ArrayList();
        collections.add(a);
        collections.add(ppaa);

        MixingAudioInputStream appaa = new MixingAudioInputStream(a.getFormat(), collections);

        DataLine.Info info = new DataLine.Info(Clip.class, appaa.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(appaa);
        clip.start();

    }


    @Test
    public void testPlay_mixer_akkaa() throws Exception {
        AudioInputStream a = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/a_1.0.wav")));
        AudioInputStream ppaa = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/kkaa_2.5.wav")));
        Collection collections = new ArrayList();
        collections.add(a);
        collections.add(ppaa);

        MixingAudioInputStream appaa = new MixingAudioInputStream(a.getFormat(), collections);

        DataLine.Info info = new DataLine.Info(Clip.class, appaa.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(appaa);
        clip.start();

    }



    @Test
    public void testPlay_mixer_idam() throws Exception {
        AudioInputStream e = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/e_1.0.wav")));
        AudioInputStream da = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/da_1.0.wav")));
        AudioInputStream im = AudioSystem.getAudioInputStream(asByteArrayInputStream(TamilFactory.class.getResourceAsStream("/sound/im_0.5.wav")));
        Collection<AudioInputStream> collections = new ArrayList();
        collections.add(e);
        collections.add(da);
        collections.add(im);


       // MixingAudioInputStream idam = new MixingAudioInputStream(da.getFormat(), collections);
        int count = 0;
       for (AudioInputStream au : collections) {

           if (count ==0) {
               Thread.sleep(100);
           }
           if (count ==1) {
               Thread.sleep(50);
           }
           if (count ==2) {
               Thread.sleep(200);
           }

           count ++;
           DataLine.Info info = new DataLine.Info(Clip.class, au.getFormat());
           Clip clip = (Clip) AudioSystem.getLine(info);
           clip.open(au);
           clip.start();



       }
    }



}
