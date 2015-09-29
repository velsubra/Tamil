package tamil.lang.api.feature;

import tamil.lang.api.dictionary.*;
import tamil.lang.api.number.IgnoreNonDigitFeature;
import tamil.lang.api.number.PunharchiFeature;
import tamil.lang.api.parser.*;
import tamil.lang.api.regex.*;
import tamil.lang.api.trans.JoinFeature;
import tamil.lang.api.trans.NounLookupFeature;

/**
 * <p>
 * Most of the APIs takes for list of features
 * </p>
 *
 * @author velsubra
 */
public final class FeatureConstants {


    /**
     * Join feature supported by Transliteration
     *
     * @see tamil.lang.api.trans.JoinFeature
     */
    public static final JoinFeature TRANSLIT_JOIN_FEATURE_VAL_110 = JoinFeature.INSTANCE;

    /**
     * Noun lookup feature supported by  Transliteration
     *
     * @see tamil.lang.api.trans.NounLookupFeature
     */
    public static final NounLookupFeature TRANSLIT_NOUN_LOOKUP_FEATURE_VAL_115 = NounLookupFeature.INSTANCE;


    /**
     * Keeps punharchchi while reading a number
     *
     * @see tamil.lang.api.number.NumberReader
     */
    public static final PunharchiFeature READ_NUMBER_PUNHARCHCHI_FULL_VAL_130 = PunharchiFeature.INSTANCE_FULL;

    public static final PunharchiFeature READ_NUMBER_PUNHARCHCHI_KEEP_ONLY_POSITION_VAL_135 = PunharchiFeature.INSTANCE_KEEP_ONLY_POSITION;


    public static final IgnoreNonDigitFeature IGNORE_NON_DIGIT_VAL_150 = IgnoreNonDigitFeature.INSTANCE_IGNORE_NON_DIGIT;

    public static final IgnoreNonDigitFeature IGNORE_NON_DIGIT_TREAT_AS_0_VAL_155 = IgnoreNonDigitFeature.INSTANCE_TREAT_NON_DIGIT_AS_0;


    public static final DictionaryFeature DICTIONARY_EXACT_MATCH_VAL_160 = new ExactMatchSearch();
    public static final DictionaryFeature DICTIONARY_AUTO_SUGGEST_VAL_165 = new AutoSuggestFeature();
    public static final StartsWithHigherLengthSearch DICTIONARY_STARTS_WITH_HIGHER_LENGTH_VAL_166 = StartsWithHigherLengthSearch.FEATURE;
    public static final ReverseSearchFeature  DICTIONARY_REVERSED_SEARCH_VAL_167 =   ReverseSearchFeature.FEATURE;



    public static final ParseWithUnknownFeature PARSE_WITH_UNKNOWN_VAL_170 =  ParseWithUnknownFeature.FEATURE;
    public static final ParseAsNumberFeature PARSE_FOR_NUMBER_VAL_172 =  ParseAsNumberFeature.FEATURE;

    public static final ParseFailureFindIndexFeature PARSE_FIND_FAILURE_INDEX_VAL_175 = ParseFailureFindIndexFeature.FEATURE;
    public static final EagerlyParsingFeature PARSE_EAGER_FIND_LONG_WORDS_VAL_176 = EagerlyParsingFeature.FEATURE;
    public static final VallinavottuEndingOk PARSE_EAGER_FIND_LONG_WORDS_VAL_177 = VallinavottuEndingOk.FEATURE;


    public static final RXKuttuFeature RX_TEAT_KUTTU_AS_ONE_LETTER_VAL_185 = RXKuttuFeature.FEATURE;
    public static final RXKuttuAcrossCirFeature RX_TEAT_SPLIT_KUTTU_AS_ONE_MEI_VAL_182 = RXKuttuAcrossCirFeature.FEATURE;
    public static final RXAythamAsKurrilFeature RX_TEAT_AAYTHAM_AS_KURRIL_VAL_186 = RXAythamAsKurrilFeature.FEATURE;
    public static final RXOverrideSysDefnFeature RX_OVERRIDE_SYS_DEFN_VAL_188 = RXOverrideSysDefnFeature.FEATURE;
    public static final RXIncludeCanonicalEquivalenceFeature RX_INCLUDE_UNICODE_CANON_EQUIVALENCE_VAL_189 = RXIncludeCanonicalEquivalenceFeature.FEATURE;






}
