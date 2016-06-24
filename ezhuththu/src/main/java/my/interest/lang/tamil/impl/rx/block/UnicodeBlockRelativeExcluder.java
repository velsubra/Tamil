package my.interest.lang.tamil.impl.rx.block;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * Created by velsubra on 6/23/16.
 * <p/>
 * This is not directly exposed
 */
public class UnicodeBlockRelativeExcluder implements PatternGenerator {

    UnicodeBlockDescription description = null;
    int previousUpper, nextLower;

    public UnicodeBlockRelativeExcluder(UnicodeBlockDescription desc, int previousUpper, int nextLower) {
        this.description = desc;
        this.previousUpper = previousUpper;
        this.nextLower = nextLower;
    }

    public String generate(FeatureSet set) {
        if (description.getStartingCodePoint() <= 0) {
            return "[" + UnicodeBlockDescription.getUniCodeString(description.getEndingCodePoint() + 1) + "-" + UnicodeBlockDescription.getUniCodeString(nextLower-1) + "]";
        } else if (description.getEndingCodePoint() >= LAST_UNICODE_CODEPOINT) {
            return "["+ UnicodeBlockDescription.getUniCodeString(previousUpper + 1) + "-" + UnicodeBlockDescription.getUniCodeString(description.getStartingCodePoint() - 1) + "]";
        } else {
            return "["+ UnicodeBlockDescription.getUniCodeString(previousUpper + 1) + "-" + UnicodeBlockDescription.getUniCodeString(description.getStartingCodePoint() - 1) + UnicodeBlockDescription.getUniCodeString(description.getEndingCodePoint() + 1) + "-" + UnicodeBlockDescription.getUniCodeString(nextLower -1) + "]";
        }
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return "!" + description.getName();
    }

    public String getDescription() {
        return "Represents any character outside the unicode block " + description.getUnicodeBlock().toString() + "  that starts with " + description.getStartingUnicodeString();
    }
}
