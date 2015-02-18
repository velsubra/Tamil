package my.interest.lang.tamil;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.internal.api.TamilCharacterParserListener;
import tamil.lang.*;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IPeyarchchol;

import java.io.*;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilUtils extends  EzhuththuUtils {


    private static List<Class<? extends IKnownWord>> peyar = new ArrayList<Class<? extends IKnownWord>>();

    static {
        peyar.add(IPeyarchchol.class);
    }





    public static boolean isUyarThinhai(PaalViguthi viguthi) {
        return (viguthi != PaalViguthi.A) && (viguthi != PaalViguthi.THU);
    }


    public static boolean isPadarkkai(PaalViguthi viguthi) {
        return viguthi == PaalViguthi.A
                || viguthi == PaalViguthi.THU ||
                viguthi == PaalViguthi.AALH ||
                viguthi == PaalViguthi.AAN ||
                viguthi == PaalViguthi.AAR || viguthi ==
                PaalViguthi.AR;
    }

    public static boolean isVinaiMuttuAsNoun(SimpleTense tense, PaalViguthi viguthi) {
        if (!isPadarkkai(viguthi)) return false;
        if (viguthi == PaalViguthi.A) {
            return false;

        }
        if (viguthi == PaalViguthi.THU) {
            if (tense == SimpleTense.FUTURE) {
                return false;
            } else {
                return true;
            }
        }
        if (tense == SimpleTense.PRESENT) return false;
        return true;
    }


//    public static boolean isUyarThinhaipPeyar(TamilWord w) {
//        //E.gகலாம் -உயர்திணை
//        List<IKnownWord> list = PersistenceInterface.findMatchingDerivedWords(w, true, 5, peyar);
//        boolean isUyarthinai = false;
//        for (IKnownWord ipeyar : list) {
//            if (((IPeyarchchol) ipeyar).isUyarThinhai()) {
//                isUyarthinai = true;
//                break;
//            }
//        }
//        return isUyarthinai;
//    }



}
