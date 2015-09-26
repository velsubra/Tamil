package tamil.util.regex;

import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.RxRegistry;
import tamil.lang.api.regex.RXFeature;
import tamil.util.IPropertyFinder;
import tamil.lang.TamilWord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class TamilPattern {

    Pattern innerPattern = null;

    private TamilPattern(String given, StringUtils.IndexContext context, int flags) {
        try {
            innerPattern = Pattern.compile(context.finalString, flags | Pattern.CANON_EQ | Pattern.UNICODE_CHARACTER_CLASS);
        } catch (PatternSyntaxException pe) {
            // pe.printStackTrace();
            StringUtils.IndexContext.Range range = context.getSourceIndex(pe.getIndex());
            throw new TamilPatternSyntaxException("Errors in the generated rx: " + context.finalString + pe.getDescription(), given, range.from);

        }

    }

    /**
     * Compiles Tamil regex into TamilPattern.
     * @param pattern the tamil pattern to be compiled
     * @return    the compiled TamilPattern
     */
    public static TamilPattern compile(String pattern) {
        return compile(pattern, null);
    }


    /**
     * Compile a patter that may contain alias definitions.
     * @param pattern the pattern to be compiled.
     * @param aliasFinder interface to return alias definitions.
     * @return  the compiled TamilPattern.
     */
    public static TamilPattern compile(String pattern, IPropertyFinder aliasFinder, RXFeature ... features) {
        return compile(pattern, 0, aliasFinder, features);

    }

    /**
     * Compiles a regular expression into TamilPattern
     * @param pattern   the rx containing tamil patterns of the form ${.....}
     * @param flags  flags to pass into {@link java.util.regex.Pattern#compile(String, int)}
     * @param aliasFinder the interface to find alias definitions
     * @return  TamilPattern
     */
    public static TamilPattern compile(String pattern, int flags, IPropertyFinder aliasFinder, RXFeature ... features) {
        StringUtils.IndexContext context = StringUtils.replaceWithContext("${", "}", pattern, new RxRegistry(aliasFinder, (features == null || features.length == 0) ? FeatureSet.EMPTY : new FeatureSet(features)), true, true, true);
        if (context.finalString.length() > 10*1024) {
            System.out.println("----"+pattern+"-----> Generated Pattern size in KB:" + context.finalString.length() /1000);
        }
        // System.out.println("pattern:" + pattern + " =>Real RX:" + context.finalString);
        return new TamilPattern(pattern, context, flags);


    }

    /**
     * Returns standard Java Matcher for the given character sequence.  The matcher works off of the java CharSequence such that the indices it returns point the java character positions.
     * Please use {@link #tamilMatcher(String)} if you need the index pointing the Tamil characters.
     * @param sequence the character sequence.
     * @return the Matcher interface
     */
    public Matcher matcher(CharSequence sequence) {
        return innerPattern.matcher(sequence);
    }

    public TamilMatcher tamilMatcher(String sequence) {
        TamilWord word = TamilWord.from(sequence, true);
        return new TamilMatcher(innerPattern.matcher(sequence), word);
    }

    public TamilMatcher matcher(TamilWord word) {
        return new TamilMatcher(innerPattern.matcher(word.toString()), word);
    }
}
