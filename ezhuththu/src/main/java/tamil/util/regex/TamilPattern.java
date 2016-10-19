package tamil.util.regex;

import my.interest.lang.tamil.StringUtils;
import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.RxRegistry;
import tamil.lang.api.feature.Feature;
import tamil.lang.api.regex.RXFeature;
import tamil.util.IPropertyFinder;
import tamil.lang.TamilWord;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    public Pattern getInnerPattern() {
        return innerPattern;
    }

    public RxRegistry getInnerRegistry() {
        return innerRegistry;
    }

    private RxRegistry innerRegistry = null;

    Pattern innerPattern = null;

    /**
     * Gets the tamil Pattern associated with this object
     * @return the tamil pattern
     */
    public String getTamilPattern() {
        return tamilPattern;
    }

    private String tamilPattern = null;

    private TamilPattern(String given, StringUtils.IndexContext context, int flags, RxRegistry registry) {
        try {
         // System.out.println("Compiling :" + context.finalString);
          innerPattern = Pattern.compile(context.finalString, flags | Pattern.UNICODE_CHARACTER_CLASS);
          this.tamilPattern = given;
          this.innerRegistry = registry;



        } catch (PatternSyntaxException pe) {
            //throw pe;
            pe.printStackTrace();
            StringUtils.IndexContext.Range range = context.getSourceIndex(pe.getIndex());
            throw new TamilPatternSyntaxException("Errors in the generated rx: " + context.finalString + pe.getDescription(), given, range.from);

        }

    }

    /**
     * Returns map of group name and group index
     * @return the named group map.
     */
    public Map<String, Integer> getNamedGroups() {
        try {
            Field field = Pattern.class.getDeclaredField("namedGroups");
            field.setAccessible(true);
            Map<String, Integer> map =  (Map<String, Integer>) field.get(this.innerPattern);
            if (map == null) {
                return Collections.emptyMap();
            } else {
                return map;
            }
        } catch (Exception ns) {
            throw new RuntimeException("Unable to get the named groups", ns);
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
        if (pattern.startsWith("TX{")) {
            pattern = "${"+pattern.substring(3);
        }
        RxRegistry  registry = new RxRegistry(aliasFinder, (features == null || features.length == 0) ? FeatureSet.EMPTY : new FeatureSet(features));
        StringUtils.IndexContext context = StringUtils.replaceWithContext("${", "}", pattern, registry, true, true, true);
        if (context.finalString.length() > 10*1024) {
            System.out.println("----"+pattern+"-----> Compiled Pattern size in KB:" + context.finalString.length() /1000);
        } else {
         // System.out.println("pattern:" + pattern + " =>Real RX:" + context.finalString+":");
        }
        TamilPattern pat =  new TamilPattern(pattern, context, flags, registry);
        context.release();
        return  pat;



    }


    /**
     * API for pre-processing Tamil Expression
     * @param pattern
     *
     * @param aliasFinder
     * @param features
     * @return  standard java expression
     */
    public static String preProcess(String pattern, IPropertyFinder aliasFinder, RXFeature ... features) {
        RxRegistry  registry = new RxRegistry(aliasFinder, (features == null || features.length == 0) ? FeatureSet.EMPTY : new FeatureSet(features));
        StringUtils.IndexContext context = StringUtils.replaceWithContext("${", "}", pattern, registry, true, true, true);
        return context.finalString;
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
        return new TamilMatcher(this,innerPattern.matcher(sequence), word);
    }

    public TamilMatcher matcher(TamilWord word) {
        return new TamilMatcher(this,innerPattern.matcher(word.toString()), word);
    }


    public static FeaturedPatternsList compileToPatternsList(String pattern,   RXFeature ... alternatives) {
        return compileToPatternsList(pattern, null, alternatives);
    }
    public static FeaturedPatternsList compileToPatternsList(String pattern,  List<RXFeature> base,  RXFeature ... alternatives) {
        return compileToPatternsList(pattern, 0, null, base, alternatives);
    }

    public static FeaturedPatternsList compileToPatternsList(String pattern, int flags, IPropertyFinder aliasFinder, List<RXFeature> base,  RXFeature ... alternatives) {
       if (base == null) {
           base = Collections.emptyList();
       }
        FeatureSet baseFeatureSet =  new FeatureSet(base.toArray(new RXFeature[0]));
        List<RXFeature> alter = new ArrayList<RXFeature>();
        if (alternatives != null) {
            for (RXFeature rx : alternatives) {
                if (!baseFeatureSet.isFeatureEnabled(rx.getClass())) {
                    alter.add(rx);
                }
            }
        }
        int loopLength = (int) Math.pow(2, alter.size());
        List<TamilPattern> patternlist = new ArrayList<TamilPattern>(loopLength);
        for (int i =0 ;i < loopLength; i++) {
           // System.out.println("starting collection :" + i);
            FeatureSet bcloned = new FeatureSet( baseFeatureSet.getFeatures(Feature.class).toArray(new Feature[0]));
            for (int j = 0; j < alter.size(); j++) {
                int bitposition = (int) Math.pow(  2, j);
               boolean toIncude = (bitposition & i) != 0;
                if (toIncude) {
                 //   System.out.println("------------------Including:" + alter.get(j));
                    bcloned.addFeature(alter.get(j));
                }
            }
          //  System.out.println(bcloned.getFeatures(RXFeature.class).size());

           patternlist.add(compile(pattern, flags,aliasFinder, bcloned.getFeatures(RXFeature.class).toArray(new RXFeature[0])));
        }

        return new FeaturedPatternsList( pattern,patternlist);

    }
}
