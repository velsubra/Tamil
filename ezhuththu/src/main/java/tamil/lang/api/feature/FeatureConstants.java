package tamil.lang.api.feature;

import tamil.lang.api.dictionary.AutoSuggestFeature;
import tamil.lang.api.dictionary.DictionaryFeature;
import tamil.lang.api.dictionary.ExactMatchSearch;
import tamil.lang.api.number.IgnoreNonDigitFeature;
import tamil.lang.api.number.PunharchiFeature;
import tamil.lang.api.parser.ParseFailureFindIndexFeature;
import tamil.lang.api.parser.ParseWithUnknownFeature;
import tamil.lang.api.trans.JoinFeature;
import tamil.lang.api.trans.NounLookupFeature;

/**
 * <p>
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

    public static final ParseWithUnknownFeature PARSE_WITH_UNKNOWN_VAL_170 = new ParseWithUnknownFeature();

    public static final ParseFailureFindIndexFeature PARSE_FIND_FAILURE_INDEX_VAL_175 = new ParseFailureFindIndexFeature();







}
