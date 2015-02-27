package my.interest.lang.tamil.parser.impl;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.internal.api.TamilSoundParserListener;
import tamil.lang.sound.AtomicSound;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilSoundListener implements TamilSoundParserListener {

    List<AtomicSound> list = new ArrayList<AtomicSound>();

    @Override
    public boolean tamilSound(AtomicSound tamil) {
        list.add(tamil);
        return false;
    }

    @Override
    public boolean nonTamilSound(AbstractCharacter nonTamil) {
        list.add(AtomicSound.SPACE);
        return false;
    }

    @Override
    public List<AtomicSound> get() {
        return list;
    }
}
