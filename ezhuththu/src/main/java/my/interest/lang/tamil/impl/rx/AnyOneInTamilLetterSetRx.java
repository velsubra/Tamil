package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class AnyOneInTamilLetterSetRx implements PatternGenerator {
    public Set<TamilCharacter> getSet() {
        return set;
    }

    Set<TamilCharacter> set = null;
    String generated = null;

    public String getName() {
        return name;
    }

    private String name = null;
    private String description = null;
    public AnyOneInTamilLetterSetRx(String name, Set<TamilCharacter>... sets) {
        this(name, name, sets);

    }

    public AnyOneInTamilLetterSetRx(String name, String description, Set<TamilCharacter>... sets) {
        this.set = new LinkedHashSet<TamilCharacter>();
        for (Set<TamilCharacter> set : sets) {
            this.set.addAll(set);
        }
        this.name = name;
        this.description = description;

        //System.out.println(this.name + ":" + set.size() + ":" + set);
    }

    public String generate() {
        if (generated == null) {
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
                buffer.append(ch.toUnicodeStringRepresentation());
               // buffer.append(")");

                first = false;
            }

            buffer.append(")");


            generated = buffer.toString();
        }
        return generated;

    }

    public String getDescription() {
        return description;
    }

    public boolean isCharacterSet() {
        return true;
    }

    public Set<TamilCharacter> getCharacterSet() {
        return set;
    }
}
