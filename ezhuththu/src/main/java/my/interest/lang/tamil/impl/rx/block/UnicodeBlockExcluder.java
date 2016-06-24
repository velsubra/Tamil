package my.interest.lang.tamil.impl.rx.block;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * Created by velsubra on 6/21/16.
 */
public class UnicodeBlockExcluder implements PatternGenerator {
    UnicodeBlockDescription description = null;

    public UnicodeBlockExcluder(UnicodeBlockDescription desc) {
        this.description = desc;
    }

    public String generate(FeatureSet set) {
        return "[^"+ description.getStartingUnicodeString() + "-" + description.getEndingUnicodeString() + "]";
//        if (description.getStartingCodePoint() <= 0) {
//            return "[" + UnicodeBlockDescription.getUniCodeString(description.getEndingCodePoint() + 1) + "-" + UnicodeBlockDescription.getUniCodeString(LAST_UNICODE_CODEPOINT) + "]";
//        } else if (description.getEndingCodePoint() >= LAST_UNICODE_CODEPOINT) {
//            return "[\\u0000-" + UnicodeBlockDescription.getUniCodeString(description.getStartingCodePoint() - 1) + "]";
//        } else {
//            return "[\\u0000-" + UnicodeBlockDescription.getUniCodeString(description.getStartingCodePoint() - 1) + UnicodeBlockDescription.getUniCodeString(description.getEndingCodePoint() + 1) + "-" + UnicodeBlockDescription.getUniCodeString(LAST_UNICODE_CODEPOINT) + "]";
//        }

    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return "!" + description.getName();
    }

    public String getDescription() {
        return "Represents any character outside the unicode block " + description.getUnicodeBlock().toString() + "  that starts with " + description.getStartingUnicodeString() +". Hex Range:" + Integer.toHexString(description.getStartingCodePoint()) + " - " +Integer.toHexString(description.getEndingCodePoint());
    }
}
