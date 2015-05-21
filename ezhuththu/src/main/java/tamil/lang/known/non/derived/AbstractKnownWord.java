package tamil.lang.known.non.derived;

import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.*;

/**
 * <p>
 * அறியப்பட்ட சொல்லைக்குறிப்பது.
 * The base implementation that is shared by many of the concrete implementation of  concrete words.
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractKnownWord implements IKnownWord {
    private static final Set<Class> knownTypes = new HashSet<Class>();

    public Set<String> getPropertyNames() {
        if (properties == null) {
            return Collections.emptySet();
        }
        return properties.keySet();
    }

    Map<String, String> properties = null;

    public TamilWord getType() {
        return type;
    }

    protected TamilWord type = null;


    public AbstractKnownWord(TamilWord word) {
        this.word = word;
        type = EnglishToTamilCharacterLookUpContext.getBestMatch(getClass().getSimpleName().toLowerCase());
        int size = knownTypes.size();
        knownTypes.add(this.getClass());
        if (size != knownTypes.size()) {
            System.out.println(knownTypes);
        }
    }

    public TamilWord getWord() {
        return word;
    }

    public void setWord(TamilWord word) {
        this.word = word;
    }

    private TamilWord word;

    public void addProperty(String name, String val) {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        properties.put(name, val);
    }

    public String getProperty(String name) {
        if (properties == null) {
            return null;
        }
        return properties.get(name);
    }

    public String toString() {
        return word.toString();
    }

    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }


    @Override
    public int compareTo(Object o) {
        int ret = this.getWord().compareTo(((AbstractKnownWord) o).getWord());
        if (ret == 0) {
            if (ITheriyaachchol.class.isAssignableFrom(o.getClass())) {
                if (ITheriyaachchol.class.isAssignableFrom(getClass())) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (ITheriyaachchol.class.isAssignableFrom(getClass())) {
                    return -1;
                } else {
                    return getClass().getName().compareTo(o.getClass().getName());
                }
            }

        } else {
            return ret;
        }
    }
}
