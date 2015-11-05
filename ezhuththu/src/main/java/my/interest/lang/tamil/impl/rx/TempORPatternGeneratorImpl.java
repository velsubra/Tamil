package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TempORPatternGeneratorImpl extends ORPatternGenerator {
    List<String> list = null;
    public TempORPatternGeneratorImpl(List<String> list) {
       this.list = list;
    }

    @Override
    public List<String> getList(FeatureSet set) {
        return list;
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    /**
     * The name of the set
     *
     * @return the name
     */
    public String getName() {
        return null;
    }


    public String getDescription() {
        return null;
    }
}
