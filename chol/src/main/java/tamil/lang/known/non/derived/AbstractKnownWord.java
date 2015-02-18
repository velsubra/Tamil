package tamil.lang.known.non.derived;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;
import my.interest.lang.tamil.translit.EnglishToTamilCharacterLookUpContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>
 *      அறியப்பட்ட சொல்லைக்குறிப்பது.
 *
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractKnownWord implements IKnownWord {
    private static final  Set<Class>  knownTypes = new HashSet<Class>();
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
            return getClass().getSimpleName().compareTo(o.getClass().getSimpleName());
        } else {
            return ret;
        }
    }
}
