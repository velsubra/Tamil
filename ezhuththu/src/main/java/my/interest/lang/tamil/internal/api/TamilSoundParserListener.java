package my.interest.lang.tamil.internal.api;

import common.lang.impl.AbstractCharacter;
import tamil.lang.sound.AtomicSound;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface TamilSoundParserListener {

    /**
     * Called when a tamil sound is encountered. This can contain more than one letter
     *
     * @param tamil sound packet
     * @return true when parsing is to be finished, false otherwise
     */
    public boolean tamilSound(AtomicSound tamil);

    public boolean nonTamilSound(AbstractCharacter nonTamil);

    public List<AtomicSound> get();
}
