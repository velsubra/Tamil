package tamil.util.regx;

import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.impl.rx.RxRegistry;
import my.interest.lang.tamil.internal.api.IPropertyFinder;

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
             throw new TamilPatternSyntaxException("Errors in the generated rx: " + context.finalString + pe.getDescription() , given, range.from);

         }

    }
    public static  TamilPattern compile(String pattern,  IPropertyFinder aliasFinder) {
        return  compile(pattern, 0, aliasFinder);

    }
    public static  TamilPattern compile(String pattern, int flags, IPropertyFinder aliasFinder) {
        StringUtils.IndexContext context = StringUtils.replaceWithContext("${", "}", pattern, new RxRegistry(aliasFinder), true, true, true);
        System.out.println("pattern:" + pattern + " =>Real RX:" + context.finalString);
        return new TamilPattern(pattern, context, flags);


    }

    public Matcher matcher(CharSequence sequence) {
        return innerPattern.matcher(sequence);
    }
}
