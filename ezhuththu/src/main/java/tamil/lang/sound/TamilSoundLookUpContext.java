package tamil.lang.sound;

import common.lang.impl.AbstractCharacter;
import common.lang.impl.UnknownCharacter;
import my.interest.lang.tamil.EzhuththuUtils;
import tamil.lang.TamilCharacter;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilSimpleCharacter;
import tamil.lang.TamilWord;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * This class registers all the possible sounds that the  Tamil characters can make.
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public final class TamilSoundLookUpContext {

    /**
     * Returns the direct sound in the context.
     */
    public AtomicSound directSound;

    /**
     * Special sound when it is following any  uyir;
     */
    public AtomicSound nextToUyirSound;

    /**
     * Special sound when it is following any consonant;
     */

    public AtomicSound nextToConsonantSound;


    // TamilSoundLookUpContext parent = null;
    private Map<AbstractCharacter, TamilSoundLookUpContext> continuations = null;

    /**
     * root sound map
     */
    private static Map<AbstractCharacter, TamilSoundLookUpContext> map = new HashMap<AbstractCharacter, TamilSoundLookUpContext>();

    private TamilSoundLookUpContext() {

    }

    /**
     * Lists all tamil characters registered.
     *
     * @return the set of all known tamil characters
     */

    public List<AtomicSound> getAllTamilSoundsHere() {
        List<AtomicSound> list = new ArrayList<AtomicSound>();
        if (this.directSound != null) {
            list.add(this.directSound);
        }
        if (this.nextToUyirSound != null) {
            list.add(this.nextToUyirSound);
        }
        if (this.nextToConsonantSound != null) {
            list.add(this.nextToConsonantSound);
        }
        if (continuations != null) {
            for (TamilSoundLookUpContext context : this.continuations.values()) {
                list.addAll(context.getAllTamilSoundsHere());
            }
        }
        return list;
    }

    public static List<AtomicSound> getAllTamilSounds() {
        List<AtomicSound> list = new ArrayList<AtomicSound>();
        for (TamilSoundLookUpContext context : map.values())  {
            list.addAll(context.getAllTamilSoundsHere());
        }
        return list;

    }


    private static TamilSoundLookUpContext registerSequence(AtomicSound simpleSound) {
        TamilWord word = simpleSound.getWord();
        if (word == null || word.isEmpty()) {
            throw new RuntimeException("Empty word can not be registered");
        }

        TamilSoundLookUpContext context = map.get(word.get(0));
        if (context == null) {
            context = new TamilSoundLookUpContext();
            map.put(word.get(0), context);
        }

        for (int i = 1; i < word.size(); i++) {
            AbstractCharacter ch = word.get(i);
            if (context.continuations == null) {
                context.continuations = new HashMap<AbstractCharacter, TamilSoundLookUpContext>();
            }

            TamilSoundLookUpContext subcontext = context.continuations.get(ch);
            if (subcontext == null) {
                subcontext = new TamilSoundLookUpContext();
                context.continuations.put(ch, subcontext);
            }

            context = subcontext;


        }
//        if (asDirect) {
        context.directSound = simpleSound;
//        } else if (asNextToVowel) {
//            context.nextToUyiSound = simpleSound;
//        } else {
//            context.nextToConsonantSound = simpleSound;
//        }
        return context;
    }

    public TamilSoundLookUpContext next(AbstractCharacter value) {
        if (continuations == null) {
            return null;
        }
        return continuations.get(value);
    }

    public static TamilSoundLookUpContext lookup(AbstractCharacter ch) {
        return map.get(ch);
    }


    static {
        List<Field> list = EzhuththuUtils.getPublicStaticFinalFieldsOfType(AtomicSound.class, AtomicSound.class);
        for (Field f : list) {
            try {
                AtomicSound s = (AtomicSound) f.get(null);
                TamilSoundLookUpContext context = registerSequence(s);

               TamilWord w = s.getWord();
                if (w.size() == 1) {
                    AbstractCharacter ch = w.getFirst();
                    if (ch.isTamilLetter()) {
                        TamilCharacter tm = (TamilCharacter)ch;
                        if ( tm.isUyirMeyyezhuththu()) {
                            if (tm.isVallinam()) {
                                TamilWord inam = new TamilWord(TamilSimpleCharacter.E,tm.getInaMellinam(), tm);
                                AtomicSound chainSound = new AtomicSound(inam);
                                registerSequence(chainSound);
                            }
                           // if (tm.isVallinam()) {
                                if (tm.getMeiPart() != TamilCompoundCharacter.IR) {
                                    TamilWord douable = new TamilWord(TamilSimpleCharacter.E,tm.getMeiPart(), tm);
                                    AtomicSound chaindouable = new AtomicSound(douable);
                                    registerSequence(chaindouable);
                                }
                            //}

                            if (tm.isVallinam()) {
                                TamilWord afterAKthu = new TamilWord(TamilSimpleCharacter.AKTHU, tm);
                                AtomicSound afterAKthuSound = new AtomicSound(afterAKthu);
                                registerSequence(afterAKthuSound);
                            }


                            if (tm.isVallinam()) {

//                            TamilWord afterMei = new TamilWord(tm);
//                            afterMei.add(0,UnknownCharacter.getFor('.'));
//                            context.nextToConsonantSound =   new AtomicSound(afterMei, null);


                                TamilWord afterUyir = new TamilWord(tm);
                                afterUyir.add(0, TamilSimpleCharacter.E);

                                context.nextToUyirSound = new AtomicSound(afterUyir);
                             //   registerSequence(context.nextToUyirSound);

                            }

                        }
                    }
                }
            } catch (IllegalAccessException il) {
                il.printStackTrace();
            }
        }
    }
}
