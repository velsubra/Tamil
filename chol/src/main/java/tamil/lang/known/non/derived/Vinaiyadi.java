package tamil.lang.known.non.derived;

import my.interest.lang.tamil.TamilUtils;
import my.interest.lang.tamil.generated.types.PaalViguthi;
import my.interest.lang.tamil.generated.types.SimpleTense;
import my.interest.lang.tamil.punar.PropertyDescriptionContainer;
import tamil.lang.TamilWord;

import java.util.List;

/**
 * <p>
 * <p/>
 * வினையடியைக்குறிப்பது
 * எ.கா) செல், நட
 * </p>
 *
 * @author velsubra
 */
public final class Vinaiyadi extends AbstractKnownWord implements IKaddalhai, IBaseVinai {
    public Vinaiyadi(TamilWord word, PropertyDescriptionContainer propertyContainer, boolean transitive) {
        super(word);
        this.propertyContainer = propertyContainer;
        this.transitive = transitive;
    }

    PropertyDescriptionContainer propertyContainer;


    /**
     * Tells if the verb can act on an object.
     *
     * @return return tru if it has an object, false otherwise.
     */
    public boolean isTransitive() {
        return transitive;
    }

    private boolean transitive;


    /**
     * Returns விகாரம் if defined
     *
     * @param viguthi the விகுதி for which the   விகாரம் is sought. It could be null to  ask for விகாரம் commonly defined.
     * @return விகாரம் if defined (ie when it is different from root verb), else null. Some have multiple forms of விகாரம்.
     *
     */
    public TamilWord[] getFutureVigaaram(PaalViguthi viguthi) {
        if (propertyContainer == null) {
            return null;
        }
        String val = propertyContainer.getVigaaram(transitive, SimpleTense.FUTURE.toString(), viguthi == null ? null : viguthi.toString());
        if (val == null) {
            return null;
        } else {
            List<String> list = TamilUtils.parseString(val);
            if (list.isEmpty()) {
                return null;
            }
            TamilWord[] words = new TamilWord[list.size()];
            for (int i =0 ;i < list.size(); i ++) {
                words[i] = TamilWord.from(list.get(i));
            }
            return  words;
        }
    }

    @Override
    public Vinaiyadi getKaddalhaiVinaiyadi() {
        return this;
    }

    @Override
    public Vinaiyadi getVinaiyadi() {
        return this;
    }

    public int compareTo(Object o) {
        int ret = super.compareTo(o);
        if (ret == 0) {
            return new Boolean(transitive).compareTo(((Vinaiyadi) o).transitive);
        }
        return ret;
    }
}
