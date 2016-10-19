package tamil.lang.api.number;

/**
 * <p>
 *     Ignores non-digits. Otherwise it might throw exception if it encounters a non-digit character.
 *     This features allows to ignore non-digits.
 * </p>
 *
 * @author velsubra
 */
public final class IgnoreNonDigitFeature implements NumberReaderFeature {

    public boolean considerNonDigitAs0 = false;

    private IgnoreNonDigitFeature() {
        this.considerNonDigitAs0 = false;
    }

    public boolean isConsiderNonDigitAs0() {
        return considerNonDigitAs0;
    }

    /**
     * Create new feature that tells how non digit character is to be handled.
     * @param nonDigitAs0  Pass true if non-digits such as space is to be  considered as 0. The default value is false.
     */
    private IgnoreNonDigitFeature(boolean nonDigitAs0) {
        this.considerNonDigitAs0 = nonDigitAs0;
    }


    public static final   IgnoreNonDigitFeature INSTANCE_IGNORE_NON_DIGIT = new IgnoreNonDigitFeature(false);

    public static final   IgnoreNonDigitFeature INSTANCE_TREAT_NON_DIGIT_AS_0 = new IgnoreNonDigitFeature(true);



}
