package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import tamil.lang.TamilWord;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
abstract class VinaiMuttuBase extends DerivativeWithTenseAndPaal {
    public VinaiMuttuBase(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi) {
        this(word,vinaiyadi,tense,viguthi,false);
    }
    public VinaiMuttuBase(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense, PaalViguthi viguthi, boolean implicit) {
        super(word, vinaiyadi, tense, viguthi);
        this.implicit = implicit;

    }


    private boolean implicit = false;


    /**
     * Checks if it is a simple tense.
     * @return  true if the tense is simple false otherwise.
     */
    public boolean isSimple() {
        return !isContinuousTense() && ! isPerfectTense();
    }

    /**
     * Checks if it is a continuous tense
     * @return  true if the tense is continuous else false.
     */
    public boolean isContinuousTense() {
        return Boolean.valueOf(getProperty("thodar")).booleanValue();
    }


    /**
     * Checks if it is a perfect tense
     * @return  true if the tense is perfect else false.
     */
    public boolean isPerfectTense() {
        return Boolean.valueOf(getProperty("muttu")).booleanValue();
    }


}
