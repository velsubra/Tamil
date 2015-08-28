package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 *     Regular expression for matching any Tamil word. ${மொழி} can be used in any Tamil regular expression.
 *
 *     ${மொழி} is defined to be (${ஓரெழுத்துமொழி})|(${மொழிமுதல்}${மொழியிடை}*${மொழிக்கடை}+)
 *
 * </p>
 *
 * @author velsubra
 */
public class MozhiRx implements PatternGenerator {
    public String generate() {
        return  "(${oarezhuththumozhi})|(${mozhimuthal}${mozhiyidai}*${mozhikkadai}+)";
    }

    public String getName() {
        return "மொழி";
    }

    public String getDescription() {
        return "Pattern for a Tamil word. A possible sequence that could be a Tamil Word.";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }
}