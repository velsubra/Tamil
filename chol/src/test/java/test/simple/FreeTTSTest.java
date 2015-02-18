package test.simple;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.junit.Test;

import java.io.PrintWriter;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class FreeTTSTest {

    @Test
    public void testSpeak() throws Exception {
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin");
        voice.allocate();
        voice.speak("தமிழ்நாட்டு இதுதான்");
        voice.dump(new PrintWriter(System.out, true),2, "test");
    }
}
