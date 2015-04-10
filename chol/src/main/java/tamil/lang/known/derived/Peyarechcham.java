package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.TamilCompoundCharacter;
import tamil.lang.TamilWord;
import tamil.lang.known.AbstractVinaiyadiPeyarechcham;
import tamil.lang.known.non.derived.Vinaiyadi;

/**
 * <p>
 * பெயரெச்சம்  எ.கா) படித்த, வந்த [பையன்]
 * </p>
 *
 * @author velsubra
 */
public final class Peyarechcham extends AbstractVinaiyadiPeyarechcham implements HavingTense {
    SimpleTense tense;

    static final TamilWord maarru = TamilWord.from("மாறு");

    public Peyarechcham(TamilWord word, Vinaiyadi vinaiyadi, SimpleTense tense) {
        super(word, vinaiyadi);
        this.tense = tense;

        if (this.tense == SimpleTense.PAST) {
            TamilWord t = word.duplicate();
            t.addLast(TamilCompoundCharacter.IM_I);
            MaiyeettupPeyarechchapPeyar thozhir = new MaiyeettupPeyarechchapPeyar(t, this);
            PersistenceInterface.addKnown(thozhir);
        } else if (this.tense == SimpleTense.FUTURE && word.getLast() == TamilCompoundCharacter.IM) {
            //வருமாறு
            TamilWord ve = word.subWord(0, word.size() - 1);
            ve.addAll(maarru);
            Vinaiyechcham v = new Vinaiyechcham(ve, vinaiyadi, SimpleTense.PRESENT, true);
            PersistenceInterface.addKnown(v);
        }

    }

    @Override
    public SimpleTense getTense() {
        return tense;
    }
}
