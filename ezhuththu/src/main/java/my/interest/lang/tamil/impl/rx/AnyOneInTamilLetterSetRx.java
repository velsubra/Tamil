package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;
import tamil.lang.api.regex.RXIncludeCanonicalEquivalenceFeature;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class AnyOneInTamilLetterSetRx implements PatternGenerator {
    public List<TamilCharacter> getSet() {
        return set;
    }

    List<TamilCharacter> set = null;


    public String getName() {
        return name;
    }

    private String name = null;
    private String description = null;

    public AnyOneInTamilLetterSetRx(String name, Set<TamilCharacter>... sets) {
        this(name, name, sets);

    }

    public AnyOneInTamilLetterSetRx(String name, String description, Set<TamilCharacter>... sets) {
        this.set = new ArrayList<TamilCharacter>();
        for (Set<TamilCharacter> set : sets) {
            this.set.addAll(set);
        }
        Collections.sort(this.set);
        this.name = name;
        this.description = description;

        //System.out.println(this.name + ":" + set.size() + ":" + set);
    }

    private String generateClassic(FeatureSet featureSet) {
        StringBuffer buffer = null;
        //Non capturing group
        buffer = new StringBuffer("(?:");

        Iterator<TamilCharacter> it = set.iterator();
        boolean first = true;
        while (it.hasNext()) {
            TamilCharacter ch = it.next();

            if (!first) {
                buffer.append("|");
            }
            // buffer.append("(?:");
            buffer.append(ch.toUnicodeRegEXRepresentation(featureSet.isFeatureEnabled(RXIncludeCanonicalEquivalenceFeature.class)));
            // buffer.append(")");

            first = false;
        }

        buffer.append(")");


        String generated = buffer.toString();

        return generated;
    }

    public String generate(FeatureSet featureSet) {
      //  return generateClassic(featureSet);
        return generateModern(featureSet);
    }

    public String generateModern(FeatureSet featureSet) {

        RXCalcForLetterSeries calc = new RXCalcForLetterSeries();
        for (TamilCharacter t : set) {
            calc.insertTamilCharacter(t, featureSet);
        }
        return calc.getRX();


    }

    public String getDescription() {
        return description;
    }

    public boolean isCharacterSet() {
        return true;
    }

    public Set<TamilCharacter> getCharacterSet() {
        return new LinkedHashSet<TamilCharacter>(set);
    }
}
